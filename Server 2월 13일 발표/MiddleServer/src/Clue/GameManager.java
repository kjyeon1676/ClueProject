package Clue;

import java.util.*;
import java.io.*;
//singleton
public class GameManager {
	InfoData idata;
	RankData rdata;
	CustomData cdata;
	PlayerData pdata;
	private static GameManager instance = new GameManager();
	private GameManager()
	{	
		idata = new InfoData();
		rdata = new RankData();
		cdata = new CustomData();
		pdata = new PlayerData();
	}
	public static GameManager getInstance(){
		return instance;
	}
	
}
