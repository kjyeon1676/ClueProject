package Clue;

import java.util.ArrayList;
import java.util.Collections;

public class InfoData {
	public ArrayList<String>myInfo;
	public InfoData()
	{
		myInfo = new ArrayList<String>();
		Collections.synchronizedList(myInfo);
	}
	public synchronized String getMyInfo()
	{
		String concat="";
		concat = "3$"+myInfo.get(0)+"$"+myInfo.get(1)+"$"+myInfo.get(2)+"$"+myInfo.get(3);
		System.out.println("3$"+myInfo.get(0)+"$"+myInfo.get(1)+"$"+myInfo.get(2)+"$"+myInfo.get(3));
		return concat;
	}
	public void setMyinfo(String id, String gender, int score, String email)
	{
		myInfo.add(id);
		myInfo.add(gender);
		myInfo.add(Integer.toString(score));
		myInfo.add(email);
	}
	public void initmyInfo()
	{
		myInfo.clear();
	}
}	
