/*package Clue;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
public class ThreadEx1_2{
	public ArrayList<Integer>sendCard = new ArrayList<Integer>();
	int randNum = 0 ;
	public void print()
	{
		for(int i=0; i<sendCard.size(); i++)
			System.out.println(sendCard.get(i));
	}
	
	public void selectSendCard(int client_count)
	{
		Random rand = new Random();
		int initNum = 17;
		int flag = 1,select = 1;
		for(int c=0; c<client_count; c++)
		{
			for(int i=0; i<initNum/client_count; i++) //Ŭ���̾�Ʈ ���� �� ��ŭ ī�带 ����������.
			{
				randNum = rand.nextInt(initNum);
				if(flag==1)  //���� ó�� �̴°Ŷ��.. ������ �ѹ��� �̴´�.
				{
					sendCard.add(randNum); //������ ī���ȣ�� �ӽ� ����.
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
	public static void main(String[] args) {
		ThreadEx1_2 test = new ThreadEx1_2();
		test.selectSendCard(3);
		test.print();
	}
}
*/