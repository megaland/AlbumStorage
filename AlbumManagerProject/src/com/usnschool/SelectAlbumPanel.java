package com.usnschool;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SelectAlbumPanel extends JFrame{
	private SongAddPanel songaddpanel;
	private final int FRAME_WIDTH = 300;
	private final int FRAME_HEIGHT = 400;
	private int currentpny = 0;
	private int yincresementvalue = 30; 
	public SelectAlbumPanel(SongAddPanel songaddpanel) {
		this.songaddpanel = songaddpanel;
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		//메인 패널
		JPanel mainpn = new JPanel();
		mainpn.setLayout(null);
		mainpn.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		mainpn.setBackground(Color.RED);
		JScrollPane scrollpane = new JScrollPane(mainpn);
		add(scrollpane);
		
		DBConnector connector = new DBConnector();
		ArrayList<AlbumData> albumlist = connector.getAlbumList();
		ListPanel[] listpanel = new ListPanel[albumlist.size()];
	
		for (int i = 0; i < albumlist.size(); i++) {
			listpanel[i] = new ListPanel(albumlist.get(i));
			final int inumf = i;
			listpanel[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					songaddpanel.setCurrentAlbum((albumlist.get(inumf).getNum()), albumlist.get(inumf).getAlbumname());
					SelectAlbumPanel.this.dispose();
				}
			});
			mainpn.add(listpanel[i]);
			currentpny += yincresementvalue;
		}
		
		
		setVisible(true);
	}
	
	class ListPanel extends JPanel{
		
		public ListPanel(AlbumData albumdata) {
			setLayout(null);
			setBackground(Color.green);
			setBounds(0, currentpny, FRAME_WIDTH, yincresementvalue);
			
			int labelx = 0;
			System.out.println(albumdata.getGenre());
			//앨범이름
			JLabel alumname = new JLabel(albumdata.getAlbumname());
			alumname.setBounds(10, 0, (labelx += (int)(FRAME_WIDTH*6/10)), yincresementvalue);
			add(alumname);
			//장르
			JLabel genre = new JLabel(albumdata.getGenre());
			genre.setBounds(labelx , 0, (labelx += (int)(FRAME_WIDTH*2/10)), yincresementvalue);
			add(genre);
			//가수이름
			JLabel singer = new JLabel(albumdata.getSinger());
			singer.setBounds(labelx, 0, (labelx += (int)(FRAME_WIDTH*2/10)), yincresementvalue);
			add(singer);
			
			
			setVisible(true);
		}
		
	}
	
}
