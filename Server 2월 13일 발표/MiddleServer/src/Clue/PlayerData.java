package Clue;

import java.util.ArrayList;
import java.util.Random;

public class PlayerData {
	public int [][]playerCoordinate;
	private ArrayList<String>startCoordinate;
	private ArrayList<String>startColor;
	private ArrayList<String>startPlayerName;
	public PlayerData()
	{
		startPlayerName = new ArrayList<String>();
		startColor = new ArrayList<String>();
		startCoordinate = new ArrayList<String>();
		playerCoordinate = new int [11][11];
	}
	public String getStartPlayerName(int index)
	{
		return startPlayerName.get(index);
	}
	public String getStartColor(int index)
	{
		return startColor.get(index);
	}
	public String getStartCoordinate(int index)
	{
		return startCoordinate.get(index);
	}
	public int dicePlay()
    {
		Random rand = new Random();
    	int diceNum = rand.nextInt(6)+1;
    	//System.out.println("Make Dice number"+diceNum);
    	return diceNum;
    }
	public void initPlayerCoordinate()
	{
		//*******************기본 1로 setting*********************//
		for(int i=0; i<11; i++)
		{
			for(int j=0; j<11; j++)
			{
				playerCoordinate[i][j]=0;
			}
		}
		//*******************1번 Room Setting*********************//
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
				playerCoordinate[i][j]=1;
		}
		//*******************2번 Room Setting*********************//
		for(int i=0; i<3; i++)
		{
			for(int j=4; j<7; j++)
				playerCoordinate[i][j]=2;
		}
		//*******************3번 Room Setting*********************//
		for(int i=0; i<3; i++)
		{
			for(int j=8; j<11; j++)
				playerCoordinate[i][j]=3;
		}
		//*******************4번 Room Setting*********************//
		for(int i=4; i<6; i++)
		{
			for(int j=0; j<3; j++)
				playerCoordinate[i][j]=4;
		}
		//*******************Clue Room Setting*********************//
		for(int i=4; i<6; i++)
		{
			for(int j=4; j<7; j++)
				playerCoordinate[i][j]=5;
		}
		//*******************6번 Room Setting*********************//
		for(int i=4; i<6; i++)
		{
			for(int j=8; j<11; j++)
				playerCoordinate[i][j]=6;
		}
		//*******************7번 Room Setting*********************//
		for(int i=8; i<11; i++)
		{
			for(int j=0; j<3; j++)
				playerCoordinate[i][j]=7;
		}
		//*******************8번 Room Setting*********************//
		for(int i=8; i<11; i++)
		{
			for(int j=4; j<7; j++)
				playerCoordinate[i][j]=8;
		}
		//*******************9번 Room Setting*********************//
		for(int i=8; i<11; i++)
		{
			for(int j=8; j<11; j++)
				playerCoordinate[i][j]=9;
		}
		//********************방문 setting************************//
		playerCoordinate[2][1]=0;
		playerCoordinate[2][5]=0;
		playerCoordinate[2][9]=0;
		playerCoordinate[5][2]=0;
		playerCoordinate[4][5]=0; //clue 방문
		playerCoordinate[5][8]=0;
		playerCoordinate[8][1]=0;
		
	}
	public void initPlayerName()
	{
		startPlayerName.add("player1");
		startPlayerName.add("player2");
		startPlayerName.add("player3");
		startPlayerName.add("player4");
	}
	
	public void initColor()
	{
		startColor.add("Red");
		startColor.add("Green");
		startColor.add("Blue");
		startColor.add("Yellow");
	}
	public void initCoordinate()
	{
		startCoordinate.add("3$0");
		startCoordinate.add("7$10");
		startCoordinate.add("3$10");
		startCoordinate.add("7$0");
	}
	public void clearStartColor()
	{
		startColor.clear();
	}
	
	public void setPlayerCoordinate(int x, int y)
	{
		playerCoordinate[x][y]=1;
	}

}
