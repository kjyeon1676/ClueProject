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
        System.out.println("������ ���۵Ǿ����ϴ�.");
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
       private boolean myTurn;  //�� Ȯ��
        private boolean bExit;
        private boolean master_flag;  //������� Ȯ��
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
           //���� ���ӽ� DbManager, gameManager ��ü ����
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
               case "1": //1. ȸ������
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
               case "2": //2. �α���
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
               case "3": //3.����������
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
               case "4": //��ŷ����
                  dbHandler.rankingReceive();
                  for(int i=0; i<GameManager.getInstance().rdata.rankInfo.size(); i++)
                  {
                     writeClient(GameManager.getInstance().rdata.getRankInfo(i));
                  }
                  GameManager.getInstance().rdata.initRankData();
                  GameManager.getInstance().rdata.rankCount=1;
                  messageToclient.clear();
                  break;
               case "5":  //���� ���� ��ư ���� �� ���忩�� Ȯ�� �� ������� ����
                  if(game_flag==false)
                  {
                     game_flag=true ;
                     writeClient("5$1");
                     master_flag = true;
                     myTurn = true; //������� ���ӽ���.
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
               case "6": //����Ȯ���ϸ� ��ü�޼��� ����
                  if(master_flag)
                  {
                     sendToAll("6$4");
                     messageToclient.clear();
                  }
                  break;
               case "7":  //custom mode�϶�  7$id�� �޾ƿ��� .. dbã�Ƽ� title/custom_index�� �ٽ� ����
                  dbHandler.receiveCustom(messageToclient.get(1));
                  for(int i=0; i<GameManager.getInstance().cdata.modeSelect.size(); i++)
                     writeClient("7$"+GameManager.getInstance().cdata.getModeSelect(i));
                  messageToclient.clear();
                  break;
               case "8":  //custom ���� �Ŀ� 8$Custom_index -> dbã�Ƽ� SendToall����
                  dbHandler.CustomSendData(Integer.parseInt(messageToclient.get(1)));
                  sendToAll("6$");
                  messageToclient.clear();
                  break;
               case "9":  //���� mode sendToall �������  9$7 �޾Ƽ� ó��.
                  dbHandler.CustomSendData(Integer.parseInt(messageToclient.get(1)));
                  sendToAll("6$");//1�� �������
                  System.out.println("�����������");
                  messageToclient.clear();
                  break;
               case "11":
                  if(!dbHandler.idDuplicationCheck(messageToclient.get(1)))
                  {
                     writeClient("11$1");
                     System.out.println("�ߺ����̵����");
                     messageToclient.clear();
                  }
                  else
                  {
                     writeClient("11$0");
                     System.out.println("�ߺ����̵�����");
                     messageToclient.clear();
                  }
                  break;
               case "A":  //���ӽ������� ����� ��� �κ� ( AI Part + Logic ��Ʈ) 4�α��� ����������!
                  if(master_flag)
                  {
                     writeClient("40$");
                     initPrevGame();
                  }
                  for(; ;)
                  {
                     if(end_flag==true)  //������ ���� �˸��� Ż���Ѵ�.
                        break;
                     if(myTurn==true)
                     {
                        while(true) //������ ����ȴ�.
                        {   
                           System.out.println(Thread.currentThread().getName()+"is Turn state : "+myTurn);
                           Thread.sleep(500); //��� ���.
                           gameProcessMessage = t_input.readUTF();
                           System.out.println("receve : "+gameProcessMessage);
                           splitGameMessage(gameProcessMessage);
                           if(gameProcess.get(0).equals("49")) //turn ������ ��ư�� ��������. while�� Ż�� 
                           {
                              System.out.println("���� �ѱ�ϴ�..!");
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
                           }//���� ū������ ��ũ
                           switch(gameProcess.get(0))
                           {
                           case "41":
                              //*******************dice start (�ֻ����� ������)*********************//
                              writeClient("41$"+GameManager.getInstance().pdata.dicePlay());
                              gameProcess.clear();
                              break;
                           //*******************���� �����δ�.�÷��̾ ������ ��ǥ�� ���� �ݿ��Ѵ�. *********************//
                           case "42":
                              moveDataX = Integer.parseInt(gameProcess.get(1));
                              moveDataY = Integer.parseInt(gameProcess.get(2));
                              GameManager.getInstance().pdata.setPlayerCoordinate(moveDataX, moveDataY);
                              System.out.println("x��ǥ : "+moveDataX+", y��ǥ :"+moveDataY);
                              //**********************���� ������ ��ǥ�� ������ ���Դٸ� �߸��� �����Ѵ�!**************************//
                              if(moveDataX==2 && moveDataY==1)
                              {
                                 System.out.println("1��������");
                                 writeClient("31$"+GameManager.getInstance().cdata.card_instance.roomName.get(0));
                              }
                              else if(moveDataX==2 && moveDataY==5)
                              {
                                 System.out.println("2���� ����");
                                 writeClient("32$1"+GameManager.getInstance().cdata.card_instance.roomName.get(1));
                              }
                              else if(moveDataX==2 && moveDataY==9)
                              {
                                 System.out.println("3���� ����");
                                 writeClient("33$1"+GameManager.getInstance().cdata.card_instance.roomName.get(2));
                              }
                              else if(moveDataX==5 && moveDataY==2)
                              {
                                 System.out.println("4���� ����");
                                 writeClient("33$1"+GameManager.getInstance().cdata.card_instance.roomName.get(3));
                              }
                              else if(moveDataX==5 && moveDataY==4)
                              {
                                 System.out.println("Ŭ��� ����");
                                 writeClient("34$1"+GameManager.getInstance().cdata.card_instance.roomName.get(4));
                              }
                              else if(moveDataX==5 && moveDataY==8)
                              {
                                 System.out.println("5���� ����");
                                 writeClient("35$1"+GameManager.getInstance().cdata.card_instance.roomName.get(5));
                              }
                              else if(moveDataX==8 && moveDataY==1)
                              {
                                 System.out.println("6���� ����");
                                 writeClient("36$1"+GameManager.getInstance().cdata.card_instance.roomName.get(6));
                              }
                              else if(moveDataX==8 && moveDataY==5)
                              {
                                 System.out.println("7���� ����");
                                 writeClient("37$1"+GameManager.getInstance().cdata.card_instance.roomName.get(7));
                              }
                              else if(moveDataX==8 && moveDataY==9)
                              {
                                 System.out.println("8���� ����");
                                 writeClient("38$1"+GameManager.getInstance().cdata.card_instance.roomName.get(8));
                              }
                              else
                              {
                                 System.out.println("�� �����ƴ�!");
                              }
                              gameProcess.clear();
                              break;
                           case "44": //�߸��� ���� ������� ��� Ŭ���̾�Ʈ�鿡�� �˸�  //50$playerName$roomName$weapon$suspect
                              //messageToclient.clear();
                              receiveInfer="50$"+gameProcess.get(1)+"$"+gameProcess.get(2)+"$"+gameProcess.get(3)+"$"+gameProcess.get(4);
                              sendToAll(receiveInfer);
                              for(int i=0; i<clients_list.size(); i++)
                                 clients_list.get(i).cardContent=true;
                              gameProcess.clear();
                              System.out.println("�����..?");
                              Thread.sleep(3000);
                              System.out.println(gameProcess.size());
                              while(gameProcess.size()!=4);

                              System.out.println("��������� ������1");
                              String tempconcat="42$";
                              for(int i=0; i<2; i++)
                              {
                                 tempconcat+=gameProcess.get(i)+"$";
                                 System.out.println(tempconcat);
                              }
                              
                              writeClient(tempconcat);
                              if(gameProcess.get(1).equals("����"))
                                 sendToAll("43$0");
                              else
                                 sendToAll("43$1");//
                              //���ʺκ� 4���̾����� �� �� �ٲ�����.
                              gameProcess.clear();
                              //messageToclient.clear();
                              break;
                           case "45": //clue�濡�� ������ ��ģ �� ���� ī��� ��ġ�ϴ��� Ȯ��
                              if(gameProcess.get(1).equals(GameManager.getInstance().cdata.card_instance.realRoom) &&
                                    gameProcess.get(2).equals(GameManager.getInstance().cdata.card_instance.realSuspect) &&
                                    gameProcess.get(3).equals(GameManager.getInstance().cdata.card_instance.realWeapon))
                              {
                                 System.out.println("ī����ġ");
                                 writeClient("44$");//ī�� ��ġ
                                 sendToAll("46$");
                              }
                              else
                                 writeClient("45$");//ī�� ����ġ
                     
                              break;
                           case "46":
                              //�÷��� �ʱ�ȭ ���
                              break;
                           }//case A�� switch��
                        }//while�� 
                        myTurn=false;
                        gameProcess.clear();
                     }//if��(for����)
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
                  
               }//�ٱ� switch
            }//try
            catch (IOException e)
            {
               System.out.println("���� ������ ����!!");
               bExit = true; //�����ٴ� ������ ����.
                  ipAddress = socket.getInetAddress(); //IP���� ����� ����.
                  break;
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            } 
            }//��ü run method while
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
         System.out.println("ī�� ����Ʈ�� �����մϴ�..");
         sendStartNameCard();
         System.out.println("ī�弯�⸦ �����մϴ�..");
         GameManager.getInstance().cdata.card_instance.setSuspectSelect(); //������,����,�� ī�� ���徿 �������� �̴´�.
         GameManager.getInstance().cdata.card_instance.setmixCard();  //������ 17���� ���ʴ�� ī�忡 �ִ´�.
         GameManager.getInstance().cdata.card_instance.selectSendCard(client_count); //�� �� �������� �ο����� �°� ����Ѵ�.
         System.out.println("ī�带 �����մϴ�..");
         //GameManager.getInstance().cdata.card_instance.printMixCard();
         //GameManager.getInstance().cdata.card_instance.printSendCard();
         sendSelectNameCard(client_count);
         sendStartCoordinate();
      }
      public void splitGameMessage(String gameMessage)
      {
         StringTokenizer tokenizedProcess = new StringTokenizer(gameMessage, "$"); //parsing �ؼ� ������ ����
         while (tokenizedProcess.hasMoreTokens())
         {
            gameProcess.add(tokenizedProcess.nextToken());
         }
      }
      public void splitClientMessage(String ClientMessage)
      {
         StringTokenizer tokenizedProcess = new StringTokenizer(ClientMessage, "$"); //parsing �ؼ� ������ ����
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
           //�����ν� �ʱ�ȭ�� ��ϵ�..
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
      //*******************���� ��ǥ�� ����*********************//
      System.out.println("������ǥ�� �����մϴ�....");
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