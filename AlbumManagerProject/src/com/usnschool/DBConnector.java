package com.usnschool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class DBConnector {
	private static DBConnector connector = new DBConnector();  
	
	static DBConnector getDBconnector(){
		return connector;
	}
	
	Connection con;
	
	public DBConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/melon", "root", "nfc123");
			System.out.println("db connected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertIntoDB(AlbumData albumdata){
		
		try {
			String sql = "insert into albumtbl (genre, singer, writer, writerrythm, relday, publisher, planner, introduce, imgstream) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, albumdata.getGenre());
			pstm.setString(2, albumdata.getSinger());
			pstm.setString(3, albumdata.getWriter());
			pstm.setString(4, albumdata.getWriterrythm());
			pstm.setString(5, albumdata.getRelday());
			pstm.setString(6, albumdata.getPublisher());
			pstm.setString(7, albumdata.getPlanner());
			pstm.setString(8, albumdata.getIntroduce());
			pstm.setBinaryStream(9, albumdata.getImgstream());
			pstm.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getAlbumCount(){
		Statement st = null;
		ResultSet rs = null;
		String sql = "Select count(*) from albumtbl";
		int count = 0;
			try {
				st = con.createStatement();
				rs = st.executeQuery(sql);
				rs.next();
				count = rs.getInt(0);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				if(st != null){
					try {
						st.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(rs != null){
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
	
		return count;
	}
	
	
	public ArrayList<AlbumData> getAlbumList(){
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from albumtbl";
		ArrayList<AlbumData> arrdata = new ArrayList<AlbumData>();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				AlbumData albumdata = new AlbumData();
				albumdata.setNum(rs.getInt("num"));
				albumdata.setImgstream(rs.getBinaryStream("imgstream"));
				albumdata.setIntroduce(rs.getString("introduce"));
				albumdata.setGenre(rs.getString("genre"));
				albumdata.setPlanner(rs.getString("planner"));
				albumdata.setPublisher(rs.getString("publisher"));
				albumdata.setRelday(rs.getString("relday"));
				albumdata.setSinger(rs.getString("singer"));
				albumdata.setWriter(rs.getString("writer"));
				albumdata.setWriterrythm(rs.getString("writerrythm"));
				arrdata.add(albumdata);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return arrdata;
	}

}
