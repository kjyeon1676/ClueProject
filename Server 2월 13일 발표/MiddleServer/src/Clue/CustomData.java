package Clue;

import java.util.ArrayList;
import java.util.Random;

public class CustomData {
	public ArrayList<ListCard>checkList;
	public ArrayList<String>modeSelect;
	public ListCard card_instance;
	public CustomData()
	{
		card_instance = new ListCard();
		checkList = new ArrayList<ListCard>();
		modeSelect = new ArrayList<String>();
	}
	public void setCheckList(ListCard card)
	{
		checkList.add(card);
	}
	public void setmodeSelect(String mode_name, int custom_index)
	{
		String modeConcat = mode_name+"$"+Integer.toString(custom_index);
		modeSelect.add(modeConcat);
	}
	public String getModeSelect(int index)
	{
		return modeSelect.get(index);
	}
	public void initModeSelect()
	{
		modeSelect.clear();
	}
	public void initCheckList()
	{
		checkList.clear();
	}
}

class ListCard{
	private static final int initNum = 16;
	public ArrayList<String>roomName;
	public ArrayList<String>weaponName;
	public ArrayList<String>peopleName;
	public ArrayList<String>mixCard;
	public ArrayList<Integer>sendCard;
	public String realSuspect = "";
	public String realWeapon = "";
	public String realRoom = "";
	public Random rand;
	public int randNum = 0 ;
	public ListCard()
	{
		rand = new Random();
		mixCard = new ArrayList<String>();
		roomName = new ArrayList<String>();
		weaponName = new ArrayList<String>();
		peopleName = new ArrayList<String>();
		sendCard = new ArrayList<Integer>();
	}
	public void setmixCard()
	{
		for(int i=0; i<roomName.size(); i++)
			mixCard.add(roomName.get(i));
		for(int i=0; i<weaponName.size(); i++)
			mixCard.add(weaponName.get(i));
		for(int i=0; i<peopleName.size(); i++)
			mixCard.add(peopleName.get(i));
	}
	public void printMixCard()
	{
		for(int i=0; i<mixCard.size(); i++)
			System.out.println(mixCard.get(i));
	}
	public String getMixCard(int randomMix)
	{
		return mixCard.get(sendCard.get(randomMix));
	}
	public void removeMixCard(int randomMix)
	{
		mixCard.remove(randomMix);
	}
	public void setRoomCard(String room)
	{
		roomName.add(room);
	}
	public void setWeaponCard(String weapon)
	{
		weaponName.add(weapon);
	}
	public void setPeopleCard(String people)
	{
		peopleName.add(people);
	}
	public void setSuspectSelect()
	{
    	int roomSelect = rand.nextInt(8);
    	int weaponSelect = rand.nextInt(6);
    	int peopleSelect = rand.nextInt(5);
    	realRoom = roomName.get(roomSelect);
    	realWeapon = weaponName.get(weaponSelect);
    	realSuspect = peopleName.get(peopleSelect); //용의자 카드 선정
    	System.out.println(realRoom +" "+realWeapon+ " "+realSuspect);
    	roomName.remove(realRoom);
    	weaponName.remove(realWeapon);
    	peopleName.remove(realSuspect); //뽑은 카드는 리스트에서 제거
	}
	public void printSendCard()
	{
		for(int i=0; i<sendCard.size(); i++)
			System.out.println(sendCard.get(i));
	}
	public void selectSendCard(int client_count)
	{
		Random rand = new Random();
		int flag = 1,select = 1;
		for(int c=0; c<client_count; c++)
		{
			for(int i=0; i<initNum/client_count; i++) //클라이언트 접속 수 만큼 카드를 나눠가진다.
			{
				randNum = rand.nextInt(initNum);
				if(flag==1)  //만약 처음 뽑는거라면.. 무조건 한번은 뽑는다.
				{
					sendCard.add(randNum); //선택한 카드번호를 임시 저장.
					flag=0;
				}
				else
				{
					for(int j=0; j<sendCard.size(); j++)
					{
						if(sendCard.get(j)==randNum)
						{
							select = 0;
							i--;
							break;
						}
					}
					if(select == 1)
					{
						sendCard.add(randNum);
					}
				}
				select=1;
			}
		}
	}
}
