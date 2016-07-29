package com.usnschool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SongListPanel extends JPanel{
	final int PANEL_SIZE_WIDTH = 491;
	final int PANEL_SIZE_HEIGHT = 372;
	int currenty = 0;
	final int EACH_PANEL_HEIGHT = 50;
	JPanel mainpn;
	JScrollPane scrollpane;
	public SongListPanel() {
		setLayout(null);
		setBackground(Color.RED);

		
		mainpn=  new JPanel();
		mainpn.setLayout(null);
		mainpn.setBackground(Color.cyan);
		
		scrollpane = new JScrollPane(mainpn);
		scrollpane.setBounds(0, 0, PANEL_SIZE_WIDTH, PANEL_SIZE_HEIGHT);

		DBConnector connector = DBConnector.getDBconnector();
		ArrayList<SongData> songdatalist = connector.getSongDataList();
		
		for (int i = 0; i < songdatalist.size(); i++) {
			SongListRowPanel songpanel = new SongListRowPanel(songdatalist.get(i), i);
			
			mainpn.add(songpanel);
			System.out.println(i);
			currenty += EACH_PANEL_HEIGHT;
		}

		add(scrollpane);
		
	}
	
	
	class SongListRowPanel extends JPanel{
		public SongListRowPanel(SongData songdata, int i) {
			setLayout(null);
			setBounds(0, currenty, PANEL_SIZE_WIDTH, EACH_PANEL_HEIGHT);
			
			JLabel songnum = new JLabel(""+(i+1));
			songnum.setBounds(20, 0, 50, EACH_PANEL_HEIGHT);
			add(songnum);
			
			
			JLabel songname = new JLabel(songdata.getSongname());
			songname.setBounds(50, 0, 100, EACH_PANEL_HEIGHT);
			add(songname);
			
			JLabel songcontent = new JLabel(songdata.getSongcontent());
			songcontent.setBounds(150, 0, 100, EACH_PANEL_HEIGHT);
			add(songcontent);
			
			JButton showbig = new JButton("크게보기");
			showbig.setBounds(300, 0, 150, EACH_PANEL_HEIGHT);
			showbig.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new SongContentDetail(songdata);
				}
			});
			add(showbig);
			
			mainpn.setPreferredSize(new Dimension(10, currenty+ 50));

		}
	}

}
