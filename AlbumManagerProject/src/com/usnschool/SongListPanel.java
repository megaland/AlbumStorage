package com.usnschool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SongListPanel extends JPanel{
	private final int PANEL_SIZE_WIDTH = 491;
	private final int PANEL_SIZE_HEIGHT = 372;
	private int currenty = 0;
	private final int EACH_PANEL_HEIGHT = 50;
	private JPanel mainpn;
	private JScrollPane scrollpane;
	private DBConnector connector;
	private LinkedList<SongData> checkedlist = new LinkedList<>();
	
	public SongListPanel() {
		setLayout(null);
		setBackground(Color.RED);

		mainpn=  new JPanel();
		mainpn.setLayout(null);
		
		scrollpane = new JScrollPane(mainpn);
		scrollpane.setBounds(0, EACH_PANEL_HEIGHT-20, PANEL_SIZE_WIDTH, PANEL_SIZE_HEIGHT-20);

		JPanel menupn = new JPanel();
		menupn.setBounds(0,0, PANEL_SIZE_WIDTH, EACH_PANEL_HEIGHT-20);
		add(menupn);
		
		JButton playbtn = new JButton("선택곡재생하기");
		playbtn.setPreferredSize(new Dimension(130, EACH_PANEL_HEIGHT-30));
		playbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PlayerFrame(checkedlist);
			}
		});
		menupn.add(playbtn);
		
		connector = DBConnector.getDBconnector();
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
			
			JButton deletesong = new JButton("삭제하기");
			deletesong.setBounds(230, 0+10, 100, EACH_PANEL_HEIGHT-20);
			deletesong.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					connector.DeleteSong(songdata.getNum());
				}
			});
			add(deletesong);
			
			JButton showbig = new JButton("크게");
			showbig.setBounds(330, 0+10, 100, EACH_PANEL_HEIGHT-20);
			showbig.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new SongContentDetail(songdata);
				}
			});
			add(showbig);
			
			JCheckBox selectedsong = new JCheckBox();
			selectedsong.setBounds(450, 0, 50, EACH_PANEL_HEIGHT);
			selectedsong.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==e.SELECTED){
						checkedlist.add(songdata);
					}else if(e.getStateChange()==e.DESELECTED){
						checkedlist.remove(songdata);
					}
				}
			});
			add(selectedsong);
			
			mainpn.setPreferredSize(new Dimension(10, currenty+ 50));

		}
	}

}
