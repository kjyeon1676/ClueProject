package Clue;
import java.util.*;
import java.io.*;
import java.net.*;
public class ClueServer {
   private final int max_card = 4;
   private final int port = 7788;
   private boolean game_flag;
   private boolean end_flag;
   private int client_count;
   private ArrayList<ServerReceiver>clients_list;
    private ArrayList<String>messageToclient;
    private ArrayList<String>gameProcess;
    private ServerSocket serverSocket;
    private Socket socket;
    public DataInputStream input;
    public DataOutputStream output;
    public ClueServer() throws IOException {
       socket = null;
       clients_list = new ArrayList<ServerReceiver>();
       Collections.synchronizedList(clients_list);
       gameProcess = new ArrayList<String>();
        messageToclient = new ArrayList<String>();
        serverSocket = new ServerSocket(port);
        System.out.println("서버가 시작되었습니다.");
        while (true) {
            socket = serverSocket.accept();
              System.out.println(socket.getInetAddress());
              clients_list.add(new ServerReceiver(socket,client_count));
              clients_list.get(client_count).start();
              client_count++;
              System.out.println("Client Count : "+client_count);
        }
    }
    public static void main(String[] args) throws IOException {
        new ClueServer();
    }
    private class ServerReceiver extends Thread {
       private boolean myTurn;  //턴 확인
        private boolean bExit;
        private boolean master_flag;  //방장권한 확인
        private boolean cardContent;
        private String message = "";
        private String client_name = "";
        private String bonus="";
        private String receiveInfer="";
        private String gameProcessMessage = "";
        private DbManager dbHandler;
        private InetAddress ipAddress;
        private int moveDataX;
      private int moveDataY;
      private int turn;
      private int order;
        public Socket socket;
        public DataInputStream t_input;
        public DataOutputStream t_output;
        public ServerReceiver(Socket socket,int order) 
        {
            this.socket = socket;
            this.order = order;
            try {
            t_input = new DataInputStream(socket.getInputStream());
            t_output = new DataOutputStream(socket.getOutputStream());
         } catch (IOException e) {
            e.printStackTrace();
         }
        }
      @Override
        public void run() {
           //최초 접속시 DbManager, gameManager 객체 생성
           dbHandler = new DbManager();
         dbHandler.DbConnector();
         System.out.println("[" + socket.getInetAddress() + ":"+ socket.getPort() + "]" + "client Access.");
         System.out.println("now, " + clients_list.size()+ "client connected.");
         while (!Thread.currentThread().isInterrupted()) {
            try {
               Thread.sleep(500);
               message = t_input.readUTF();
               System.out.println("receve : "+message);
               splitClientMessage(message);
               if(messageToclient.get(0).equals("0"))
               {
                  Thread.interrupted();
               }
               switch(messageToclient.get(0))
               {
               case "1": //1. 회원가입
                  if (dbHandler.signUp(messageToclient.get(1), messageToclient.get(2), messageToclient.get(3),messageToclient.get(4))) 
                  {
                     writeClient("1$1");
                     System.out.println("SignUp Complete!");
                     messageToclient.clear();
                  } 
                  else
                  {
                     writeClient("1$0");
                     System.out.println("SignUp fail");
                     messageToclient.clear();
                  }
                  break;
               case "2": //2. 로그인
                  if (dbHandler.LoginCheck(messageToclient.get(1),messageToclient.get(2))) 
                  {
                     
                     writeClient("2$1");
                     System.out.println(messageToclient.get(1)+ " hello login complete");
                     System.out.println("password is : "+ messageToclient.get(2));
                     messageToclient.clear();
                  }
                  else 
                  {
                     writeClient("2$0");
                     System.out.println("Login fail");
                     messageToclient.clear();
                  }
                  break;
               case "3": //3.내정보보기
                  System.out.println(messageToclient.get(1));
                  if (dbHandler.MyInfoCheck(messageToclient.get(1))) 
                  {
                     writeClient((GameManager.getInstance().idata.getMyInfo()));
                     GameManager.getInstance().idata.initmyInfo();
                     messageToclient.clear();
                  } 
                  else 
                  {
                     System.out.println("Myinfo fail");
                     messageToclient.clear();
                  }
                  break;
               case "4": //랭킹보기
                  dbHandler.rankingReceive();
                  for(int i=0; i<GameManager.getInstance().rdata.rankInfo.size(); i++)
                  {
                     writeClient(GameManager.getInstance().rdata.getRankInfo(i));
                  }
                  GameManager.getInstance().rdata.initRankData();
                  GameManager.getInstance().rdata.rankCount=1;
                  messageToclient.clear();
                  break;
               case "5":  //게임 시작 버튼 누른 후 방장여부 확인 후 방장권한 전송
                  if(game_flag==false)
                  {
                     game_flag=true ;
                     writeClient("5$1");
                     master_flag = true;
                     myTurn = true; //방장부터 게임시작.
                     System.out.println("my turn is  : "+myTurn+", Thread Name:"+Thread.currentThread().getName());
                     messageToclient.clear();
                  }
                  else
                  {
                     writeClient("5$0"); 
                     //myTurn = false;
                     System.out.println("my turn is  : "+myTurn+", Thread Name:"+Thread.currentThread().getName());
                     messageToclient.clear();
                  }
                  break;
               case "6": //방장확인하면 전체메세지 전송
                  if(master_flag)
                  {
                     sendToAll("6$4");
                     messageToclient.clear();
                  }
                  break;
               case "7":  //custom mode일때  7$id값 받아오고 .. db찾아서 title/custom_index만 다시 전송
                  dbHandler.receiveCustom(messageToclient.get(1));
                  for(int i=0; i<GameManager.getInstance().cdata.modeSelect.size(); i++)
                     writeClient("7$"+GameManager.getInstance().cdata.getModeSelect(i));
                  messageToclient.clear();
                  break;
               case "8":  //custom 선택 후에 8$Custom_index -> db찾아서 SendToall전송
                  dbHandler.CustomSendData(Integer.parseInt(messageToclient.get(1)));
                  sendToAll("6$");
                  messageToclient.clear();
                  break;
               case "9":  //기존 mode sendToall 기존모드  9$7 받아서 처리.
                  dbHandler.CustomSendData(Integer.parseInt(messageToclient.get(1)));
                  sendToAll("6$");//1번 기존모드
                  System.out.println("기존모드전송");
                  messageToclient.clear();
                  break;
               case "11":
                  if(!dbHandler.idDuplicationCheck(messageToclient.get(1)))
                  {
                     writeClient("11$1");
                     System.out.println("중복아이디없음");
                     messageToclient.clear();
                  }
                  else
                  {
                     writeClient("11$0");
                     System.out.println("중복아이디존재");
                     messageToclient.clear();
                  }
                  break;
               case "A":  //게임시작이후 진행될 모든 부분 ( AI Part + Logic 파트) 4인기준 접속햇을때!
                  if(master_flag)
                  {
                     writeClient("40$");
                     initPrevGame();
                  }
                  for(; ;)
                  {
                     if(end_flag==true)  //게임의 끝을 알리면 탈출한다.
                        break;
                     if(myTurn==true)
                     {
                        while(true) //게임이 진행된다.
                        {   
                           System.out.println(Thread.currentThread().getName()+"is Turn state : "+myTurn);
                           Thread.sleep(500); //잠시 대기.
                           gameProcessMessage = t_input.readUTF();
                           System.out.println("receve : "+gameProcessMessage);
                           splitGameMessage(gameProcessMessage);
                           if(gameProcess.get(0).equals("49")) //turn 종료라는 버튼이 눌러지면. while문 탈출 
                           {
                              System.out.println("턴을 넘깁니다..!");
                              changeTurn(order+1);
                              gameProcess.clear();
                              break;
                           }
                           if(gameProcess.get(0).equals("0"))
                           {
                              end_flag=true;
                              myTurn=false;
                              gameProcess.clear();
                              break;
                           }//바텀 큰지팡이 블크
                           switch(gameProcess.get(0))
                           {
                           case "41":
                              //*******************dice start (주사위를 굴린다)*********************//
                              writeClient("41$"+GameManager.getInstance().pdata.dicePlay());
                              gameProcess.clear();
                              break;
                           //*******************말을 움직인다.플레이어가 움직인 좌표를 최종 반영한다. *********************//
                           case "42":
                              moveDataX = Integer.parseInt(gameProcess.get(1));
                              moveDataY = Integer.parseInt(gameProcess.get(2));
                              GameManager.getInstance().pdata.setPlayerCoordinate(moveDataX, moveDataY);
                              System.out.println("x좌표 : "+moveDataX+", y좌표 :"+moveDataY);
                              //**********************만약 움직인 좌표가 방으로 들어왔다면 추리를 시작한다!**************************//
                              if(moveDataX==2 && moveDataY==1)
                              {
                                 System.out.println("1번방입장");
                                 writeClient("31$"+GameManager.getInstance().cdata.card_instance.roomName.get(0));
                              }
                              else if(moveDataX==2 && moveDataY==5)
                              {
                                 System.out.println("2번방 입장");
                                 writeClient("32$1"+GameManager.getInstance().cdata.card_instance.roomName.get(1));
                              }
                              else if(moveDataX==2 && moveDataY==9)
                              {
                                 System.out.println("3번방 입장");
                                 writeClient("33$1"+GameManager.getInstance().cdata.card_instance.roomName.get(2));
                              }
                              else if(moveDataX==5 && moveDataY==2)
                              {
                                 System.out.println("4번방 입장");
                                 writeClient("33$1"+GameManager.getInstance().cdata.card_instance.roomName.get(3));
                              }
                              else if(moveDataX==5 && moveDataY==4)
                              {
                                 System.out.println("클루방 입장");
                                 writeClient("34$1"+GameManager.getInstance().cdata.card_instance.roomName.get(4));
                              }
                              else if(moveDataX==5 && moveDataY==8)
                              {
                                 System.out.println("5번방 입장");
                                 writeClient("35$1"+GameManager.getInstance().cdata.card_instance.roomName.get(5));
                              }
                              else if(moveDataX==8 && moveDataY==1)
                              {
                                 System.out.println("6번방 입장");
                                 writeClient("36$1"+GameManager.getInstance().cdata.card_instance.roomName.get(6));
                              }
                              else if(moveDataX==8 && moveDataY==5)
                              {
                                 System.out.println("7번방 입장");
                                 writeClient("37$1"+GameManager.getInstance().cdata.card_instance.roomName.get(7));
                              }
                              else if(moveDataX==8 && moveDataY==9)
                              {
                                 System.out.println("8번방 입장");
                                 writeClient("38$1"+GameManager.getInstance().cdata.card_instance.roomName.get(8));
                              }
                              else
                              {
                                 System.out.println("방 도착아님!");
                              }
                              gameProcess.clear();
                              break;
                           case "44": //추리에 대한 결과값을 모든 클라이언트들에게 알림  //50$playerName$roomName$weapon$suspect
                              //messageToclient.clear();
                              receiveInfer="50$"+gameProcess.get(1)+"$"+gameProcess.get(2)+"$"+gameProcess.get(3)+"$"+gameProcess.get(4);
                              sendToAll(receiveInfer);
                              for(int i=0; i<clients_list.size(); i++)
                                 clients_list.get(i).cardContent=true;
                              gameProcess.clear();
                              System.out.println("여기는..?");
                              Thread.sleep(3000);
                              System.out.println(gameProcess.size());
                              while(gameProcess.size()!=4);

                              System.out.println("여기까지는 오겟지1");
                              String tempconcat="42$";
                              for(int i=0; i<2; i++)
                              {
                                 tempconcat+=gameProcess.get(i)+"$";
                                 System.out.println(tempconcat);
                              }
                              
                              writeClient(tempconcat);
                              if(gameProcess.get(1).equals("없음"))
                                 sendToAll("43$0");
                              else
                                 sendToAll("43$1");//
                              //이쪽부분 4명이었을시 싹 다 바뀌어야함.
                              gameProcess.clear();
                              //messageToclient.clear();
                              break;
                           case "45": //clue방에서 정답을 외친 후 정답 카드와 일치하는지 확인
                              if(gameProcess.get(1).equals(GameManager.getInstance().cdata.card_instance.realRoom) &&
                                    gameProcess.get(2).equals(GameManager.getInstance().cdata.card_instance.realSuspect) &&
                                    gameProcess.get(3).equals(GameManager.getInstance().cdata.card_instance.realWeapon))
                              {
                                 System.out.println("카드일치");
                                 writeClient("44$");//카드 일치
                                 sendToAll("46$");
                              }
                              else
                                 writeClient("45$");//카드 불일치
                     
                              break;
                           case "46":
                              //플래그 초기화 등등
                              break;
                           }//case A의 switch문
                        }//while문 
                        myTurn=false;
                        gameProcess.clear();
                     }//if문(for문안)
                     else if(myTurn==false)
                     {
                        if(cardContent)
                        {
                           Thread.sleep(1000);
                           cardMerge();
                           cardContent=false;
                        }
                        else
                        {
                           Thread.sleep(5000);
                        }
                     }
                  }
                  
               }//바깥 switch
            }//try
            catch (IOException e)
            {
               System.out.println("소켓 비정상 종료!!");
               bExit = true; //나갔다는 흔적을 남김.
                  ipAddress = socket.getInetAddress(); //IP정보 남기고 종료.
                  break;
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            } 
            }//전체 run method while
            System.out.println("client is dead..");
            System.out.println("[" + socket.getInetAddress() + ":"+ socket.getPort() + "]" + "release connected.");
              clients_list.remove(order);
         System.out.println("now.." + clients_list.size()+ "client connected.");
            try {
            t_output.close();
            t_input.close();
            dbHandler.DisConnector();
            socket.close();
            messageToclient.clear();
            //client.yield();
            client_count--;
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
            
        }
      public synchronized void cardMerge()
      {
         try {
            message = t_input.readUTF();
         } catch (IOException e) {
            e.printStackTrace();
         }
         splitGameMessage(message);
         for(int i=0; i<gameProcess.size(); i++)
            System.out.println(gameProcess.get(i));
      }
      public void initPrevGame()
      {
         System.out.println("카드 리스트를 전송합니다..");
         sendStartNameCard();
         System.out.println("카드섞기를 진행합니다..");
         GameManager.getInstance().cdata.card_instance.setSuspectSelect(); //용의자,무기,방 카드 한장씩 무작위로 뽑는다.
         GameManager.getInstance().cdata.card_instance.setmixCard();  //나머지 17장을 차례대로 카드에 넣는다.
         GameManager.getInstance().cdata.card_instance.selectSendCard(client_count); //그 중 무작위로 인원수에 맞게 배분한다.
         System.out.println("카드를 전송합니다..");
         //GameManager.getInstance().cdata.card_instance.printMixCard();
         //GameManager.getInstance().cdata.card_instance.printSendCard();
         sendSelectNameCard(client_count);
         sendStartCoordinate();
      }
      public void splitGameMessage(String gameMessage)
      {
         StringTokenizer tokenizedProcess = new StringTokenizer(gameMessage, "$"); //parsing 해서 정보를 나눔
         while (tokenizedProcess.hasMoreTokens())
         {
            gameProcess.add(tokenizedProcess.nextToken());
         }
      }
      public void splitClientMessage(String ClientMessage)
      {
         StringTokenizer tokenizedProcess = new StringTokenizer(ClientMessage, "$"); //parsing 해서 정보를 나눔
         while (tokenizedProcess.hasMoreTokens())
         {
            messageToclient.add(tokenizedProcess.nextToken());
         }
      }
      public void changeTurn(int i)
      {
         System.out.println("turn Change : "+i);
         System.out.println("before : "+clients_list.get(i%client_count).myTurn);
            clients_list.get(i%client_count).myTurn=true;
            sendToOne(i%client_count,"39$");
            System.out.println("after : "+clients_list.get(i%client_count).myTurn);
      }
      public void writeClient(String message)
        {
           try {
            t_output.writeUTF(message);
         } catch (IOException e) {
            e.printStackTrace();
         }
        }
      public void sendSelectNameCard(int c_count)
      {
         int temp = 0;
         String sum ="23$";
         for(int i=0; i<c_count; i++)
         {
            for(int j=0; j<max_card; j++)
            {
               sum+=GameManager.getInstance().cdata.card_instance.getMixCard(temp++)+"$";
            }
            sendToOne(i,sum);
            sum = "23$";
         } 
      }
      public void sendStartNameCard()
      {
         String sum ="20$";
         for(int i=0; i<GameManager.getInstance().cdata.card_instance.weaponName.size(); i++)
         {
            System.out.println(GameManager.getInstance().cdata.card_instance.weaponName.get(i));
            sum+=GameManager.getInstance().cdata.card_instance.weaponName.get(i)+"$";
         }
         sendToAll(sum);
         sum = "21$";
         for(int i=0; i<GameManager.getInstance().cdata.card_instance.peopleName.size(); i++)
         {
            System.out.println(GameManager.getInstance().cdata.card_instance.peopleName.get(i));
            sum+=GameManager.getInstance().cdata.card_instance.peopleName.get(i)+"$";
         }
         sendToAll(sum);
         sum = "22$";
         for(int i=0; i<GameManager.getInstance().cdata.card_instance.roomName.size(); i++)
         {
            System.out.println(GameManager.getInstance().cdata.card_instance.roomName.get(i));
            sum+=GameManager.getInstance().cdata.card_instance.roomName.get(i)+"$";
         }
         sendToAll(sum);
      }
        public boolean getExit()
        {
           return bExit;
        }   
        public void RejoinGame(Socket socket, DataInputStream input,DataOutputStream output)
        {
           //리조인시 초기화할 목록들..
           bExit = false;
           this.socket = socket;
           this.t_input=input;
           this.t_output=output;
           try {
            t_input = new DataInputStream(socket.getInputStream());
            t_output = new DataOutputStream(socket.getOutputStream());
         } catch (IOException e) {
            e.printStackTrace();
         }
         
        }
    }
    public void sendStartCoordinate()
    {
       String sum ="24$";
       GameManager.getInstance().pdata.initCoordinate();
       GameManager.getInstance().pdata.initPlayerCoordinate();
       GameManager.getInstance().pdata.initPlayerName();
       GameManager.getInstance().pdata.initColor();
      //*******************시작 좌표를 전송*********************//
      System.out.println("시작좌표를 전송합니다....");
      for(int i=0;i <client_count; i++)
      {
         sum+=GameManager.getInstance().pdata.getStartCoordinate(i)+"$"+GameManager.getInstance().pdata.getStartColor(i)+"$"+GameManager.getInstance().pdata.getStartPlayerName(i);
         sendToOne(i,sum);
         sum = "24$";
      }
    }
    public void sendToOne(int index, String message)
    {
      clients_list.get(index).writeClient(message);
    }
    public void sendToAll(String message) 
    {
       Iterator<ServerReceiver>valuekey = clients_list.iterator();
       while(valuekey.hasNext())
       {
          valuekey.next().writeClient(message);
       }
    }
   
}

/*Iterator<String>itkey = clients_list.keySet().iterator();
Iterator<ServerReceiver>valuekey = clients_list.values().iterator();
while(itkey.hasNext()){
   System.out.println(itkey.next());
}
while(valuekey.hasNext()){
   System.out.println(valuekey.next());
}*/