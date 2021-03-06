package com.usnschool;

import java.io.InputStream;

public class SongData {
	private int num;
	private int albumnum;
	private String songname;
	private String songcontent;
	private InputStream songblob;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getAlbumnum() {
		return albumnum;
	}
	public void setAlbumnum(int albumnum) {
		this.albumnum = albumnum;
	}
	public String getSongname() {
		return songname;
	}
	public void setSongname(String songname) {
		this.songname = songname;
	}
	public String getSongcontent() {
		return songcontent;
	}
	public void setSongcontent(String songcontent) {
		this.songcontent = songcontent;
	}
	public InputStream getSongblob() {
		return songblob;
	}
	public void setSongblob(InputStream songblob) {
		this.songblob = songblob;
	}
}
