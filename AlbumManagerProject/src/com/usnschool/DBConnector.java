package com.usnschool;

import java.io.FileInputStream;
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
	
	private Connection con;
	
	public DBConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/melon", "root", "1234");
			System.out.println("db connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertIntoSongDB(ArrayList<SongAddPanel.EachSong> eachsonglist, int currentnum){
		String sql = null;
		for (int i = 0; i < eachsonglist.size(); i++) {
			PreparedStatement pstm = null;
			if(eachsonglist.get(i).getSongpath() != null){
				sql = "insert into songtbl (albumnum, songname, songcontent, songblob)"
						+ " values(?, ?, ?, ?)";
			}else {
				sql = "insert into songtbl (albumnum, songname, songcontent)"
						+ " values(?, ?, ?)";
			}

			try {
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, currentnum);
				pstm.setString(2, eachsonglist.get(i).getSongname());
				pstm.setString(3, eachsonglist.get(i).getSongcontent());
				if(eachsonglist.get(i).getSongpath() != null){
					pstm.setBinaryStream(4, new FileInputStream(eachsonglist.get(i).getSongpath()));
				}
				
				pstm.execute();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(pstm !=null){
					try {
						pstm.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void insertIntoDB(AlbumData albumdata){
		PreparedStatement pstm =null;
		try {
			String sql = "insert into albumtbl (genre, singer, writer, writerrythm, relday, publisher, planner, introduce, imgstream, albumname) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, albumdata.getGenre());
			pstm.setString(2, albumdata.getSinger());
			pstm.setString(3, albumdata.getWriter());
			pstm.setString(4, albumdata.getWriterrythm());
			pstm.setString(5, albumdata.getRelday());
			pstm.setString(6, albumdata.getPublisher());
			pstm.setString(7, albumdata.getPlanner());
			pstm.setString(8, albumdata.getIntroduce());
			pstm.setBinaryStream(9, albumdata.getImgstream());
			pstm.setString(10, albumdata.getAlbumname());
			pstm.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstm !=null){
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void updateAlbum(AlbumData albumdata){
		String sql = "update albumtbl set genre= ?, singer=?, writer=?,"
				+ " writerrythm=?, relday=?, publisher=?, planner=?,"
				+ " introduce=?, imgstream=?,albumname=? where num = ?";
		PreparedStatement pstm = null;
		try {
			
			pstm = con.prepareStatement(sql);
			pstm.setString(1, albumdata.getGenre());
			pstm.setString(2, albumdata.getSinger());
			pstm.setString(3, albumdata.getWriter());
			pstm.setString(4, albumdata.getWriterrythm());
			pstm.setString(5, albumdata.getRelday());
			pstm.setString(6, albumdata.getPublisher());
			pstm.setString(7, albumdata.getPlanner());
			pstm.setString(8, albumdata.getIntroduce());
			pstm.setBinaryStream(9, albumdata.getImgstream());
			pstm.setString(10, albumdata.getAlbumname());
			pstm.setInt(11, albumdata.getNum());
			pstm.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstm !=null){
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
		
	public void updateAlbumNo(AlbumData albumdata){
		String sql = "update albumtbl set genre= ?, singer=?, writer=?,"
				+ " writerrythm=?, relday=?, publisher=?, planner=?,"
				+ " introduce=?,albumname=? where num = ?";
		PreparedStatement pstm = null;
		try {
			
			pstm = con.prepareStatement(sql);
			pstm.setString(1, albumdata.getGenre());
			pstm.setString(2, albumdata.getSinger());
			pstm.setString(3, albumdata.getWriter());
			pstm.setString(4, albumdata.getWriterrythm());
			pstm.setString(5, albumdata.getRelday());
			pstm.setString(6, albumdata.getPublisher());
			pstm.setString(7, albumdata.getPlanner());
			pstm.setString(8, albumdata.getIntroduce());
			pstm.setString(9, albumdata.getAlbumname());
			pstm.setInt(10, albumdata.getNum());
			pstm.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstm !=null){
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
						e.printStackTrace();
					}
				}
				if(rs != null){
					try {
						rs.close();
					} catch (SQLException e) {
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
				albumdata.setAlbumname(rs.getString("albumname"));
				arrdata.add(albumdata);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return arrdata;
	}

	public ArrayList<SongData> getSongDataList(int albumnum){
		ArrayList<SongData> songdatalist = new ArrayList<>();
		String sql = "select * from songtbl where albumnum = "+ albumnum;
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				SongData songdata = new SongData();
				songdata.setNum(rs.getInt("num"));
				songdata.setAlbumnum(rs.getInt("albumnum"));
				songdata.setSongname(rs.getString("songname"));
				songdata.setSongcontent(rs.getString("songcontent"));
				songdatalist.add(songdata);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return songdatalist;
	}
	
	public ArrayList<SongData> getSongDataList (){
		Statement st = null;
		ResultSet rs = null;
		ArrayList<SongData> songdatalist = new ArrayList<>();
		String sql = "select * from songtbl";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				SongData songdata = new SongData();
				songdata.setNum(rs.getInt("num"));
				songdata.setSongname(rs.getString("songname"));
				songdata.setSongcontent(rs.getString("songcontent"));
				songdata.setAlbumnum(rs.getInt("albumnum"));
				songdata.setSongblob(rs.getBinaryStream("songblob"));
				songdatalist.add(songdata);
				System.out.println("songname " + rs.getString("songname"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return songdatalist;
	}
	
	public void DeleteAlbumAndSong(int albumnum){
		Statement st = null;
		String sql = "delete from albumtbl where num = "+ albumnum; 
		String sql2 = "select * from songtbl where albumnum = " + albumnum; 
		ResultSet rs = null;
		
		
		try {
			st = con.createStatement();
			st.execute(sql);
			
			rs = st.executeQuery(sql2);
			ArrayList<Integer> numlist = new ArrayList<>();
			while(rs.next()){
				numlist.add(rs.getInt("num"));
			}
			for (int i = 0; i < numlist.size(); i++) {
				String delsql = "delete from songtbl where num = " + numlist.get(i);
				st.execute(delsql);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		}
	}
	
	public void DeleteSong(int num){
		Statement st = null;
		String sql = "delete from songtbl where num = "+ num;
		
		try {
			st = con.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
