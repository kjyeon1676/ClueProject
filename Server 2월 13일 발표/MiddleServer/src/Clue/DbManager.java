package Clue;
import java.sql.*;
import java.io.*;

public class DbManager {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String url = "jdbc:mysql://210.118.75.151:3306/clue?useUnicod=true&characterEncoding=utf8";
	private String id = "root";
	private String password = "eornapaqjtlq1!";
	public void DbConnector()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이버 로딩 성공");
			con = DriverManager.getConnection(url,id,password);
			System.out.println("연결성공!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void DisConnector()
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void receiveCustom(String user_id)
	{
		boolean customCheck = false;
		String sql = "select mode_name,custom_index from story_mode where user_id=?";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				GameManager.getInstance().cdata.setmodeSelect(rs.getString(1),rs.getInt(2));
				System.out.println(rs.getString(1)+rs.getInt(2));
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean LoginCheck(String user_id,String user_pw)
	{
		boolean loginCheck = false;
		String sql = "select id,pw from user where id=? and pw=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pw);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(user_id.equals(rs.getString(1)))			
				{
					if(user_pw.equals(rs.getString(2)))
						loginCheck = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginCheck;
	}
	public boolean customConstruct(String user_id,String title, String w1,String w2,String w3,String w4,String w5,String w6,String s1,String s2,String s3,String s4,String s5,String s6,String r1,String r2,String r3,String r4,String r5,String r6,String r7,String r8)
	{
		boolean customCheck=false;
		int rs;
		String sql = "insert into story_mode values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, title);
			pstmt.setString(3, w1);
			pstmt.setString(4, w2);
			pstmt.setString(5, w3);
			pstmt.setString(6, w4);
			pstmt.setString(7, w5);
			pstmt.setString(8, w6);
			pstmt.setString(9, s1);
			pstmt.setString(10, s2);
			pstmt.setString(11, s3);
			pstmt.setString(12, s4);
			pstmt.setString(13, s5);
			pstmt.setString(14, s6);
			pstmt.setString(15, r1);
			pstmt.setString(16, r2);
			pstmt.setString(17, r3);
			pstmt.setString(18, r4);
			pstmt.setString(19, r5);
			pstmt.setString(20, r6);
			pstmt.setString(21, r7);
			pstmt.setString(22, r8);
			rs = pstmt.executeUpdate();
			if(rs==1)
			{
				System.out.println("custom Success!");
				System.out.println("id:"+user_id);
				customCheck = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customCheck;
	}
	public boolean MyInfoCheck(String user_id)
	{
		boolean InfoCheck = false;
		String sql = "select id,gender,score,email from user where id=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(user_id.equals((rs.getString(1))))		
				{
					GameManager.getInstance().idata.setMyinfo(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4));
					InfoCheck = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return InfoCheck;
	}
	public boolean signUp(String user_id,String user_pw,String user_gender,String user_mail)
	{
		boolean signCheck=false;
		int rs;
		String sql = "insert into user values (?,?,?,?,?)";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pw);
			pstmt.setString(3, user_gender);
			pstmt.setInt(4, 0);
			pstmt.setString(5, user_mail);
			rs = pstmt.executeUpdate();
			if(rs==1)
			{
				System.out.println("Sign up Success!");
				System.out.println("id:"+user_id);
				signCheck = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return signCheck;
	}
	public void rankingReceive()
	{
		String sql = "select id,score from user order by score desc";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				GameManager.getInstance().rdata.setRankInfo(rs.getString(1),rs.getInt(2));
				System.out.println("id:"+rs.getString(1)+", score:"+rs.getInt(2));
			}
			//System.out.println(GameManager.getInstance().rdata.rankInfo.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void CustomSendData(int custom_index)
	{
		String sql = "select weapon1,weapon2,weapon3,weapon4,weapon5,weapon6,suspect1,suspect2,suspect3,"
				+ "suspect4,suspect5,suspect6,room1,room2,room3,room4,room5,room6,room7,room8"
				+ " from story_mode where custom_index=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, custom_index);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				GameManager.getInstance().cdata.card_instance.setWeaponCard(rs.getString(1));
				GameManager.getInstance().cdata.card_instance.setWeaponCard(rs.getString(2));
				GameManager.getInstance().cdata.card_instance.setWeaponCard(rs.getString(3));
				GameManager.getInstance().cdata.card_instance.setWeaponCard(rs.getString(4));
				GameManager.getInstance().cdata.card_instance.setWeaponCard(rs.getString(5));
				GameManager.getInstance().cdata.card_instance.setWeaponCard(rs.getString(6));
				GameManager.getInstance().cdata.card_instance.setPeopleCard(rs.getString(7));
				GameManager.getInstance().cdata.card_instance.setPeopleCard(rs.getString(8));
				GameManager.getInstance().cdata.card_instance.setPeopleCard(rs.getString(9));
				GameManager.getInstance().cdata.card_instance.setPeopleCard(rs.getString(10));
				GameManager.getInstance().cdata.card_instance.setPeopleCard(rs.getString(11));
				GameManager.getInstance().cdata.card_instance.setPeopleCard(rs.getString(12));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(13));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(14));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(15));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(16));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(17));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(18));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(19));
				GameManager.getInstance().cdata.card_instance.setRoomCard(rs.getString(20));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean idDuplicationCheck(String user_id)
	{
		boolean dCheck = false;
		System.out.println("중복확인 이에용 "+user_id);
		String sql = "select id from user where id=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, user_id); 
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(user_id.compareTo(rs.getString(1))==0)			
				{
					dCheck = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dCheck;
	}
	/*public static void main(String[] args) {
		DbManager test = new DbManager();
		boolean testflag=false;
		test.DbConnector();
		//test.rankingReceive();
		//test.receiveCustom("송이안녕");
		//test.CustomSendData("송이안녕");
		//test.LoginCheck("송이안녕", "1234");
		test.CustomSendData(7);
	}*/
}
