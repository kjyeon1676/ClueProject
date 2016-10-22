package Clue;

import java.util.ArrayList;
import java.util.Collections;
public class RankData {
	public ArrayList<String>rankInfo;
	public int rankCount = 1;
	public RankData()
	{
		rankInfo = new ArrayList<String>();
		Collections.synchronizedList(rankInfo);
	}
	public void setRankInfo(String id, int score)
	{
		String concatInfo = null;
		concatInfo = "4$"+id+"$"+score+"$"+rankCount++;
		rankInfo.add(concatInfo);
	}
	public synchronized String getRankInfo(int index)
	{
		return rankInfo.get(index);
	}
	public void initRankData()
	{
		rankInfo.clear();
	}
}
