package com.usnschool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

public class StartMain {

	public static void main(String[] args) {
		new AlbumUI();
	}

}

class AlbumUI extends JFrame implements ActionListener{
	JPanel toppn, middlepn, bottompn, leftpn, leftpn2;
	JButton showlistbtn, managebtn;
	JButton albumlistbtn, songlistbtn;
	JButton addbtn, songaddbtn;
	DBConnector connector = null;
	JScrollPane scrollpane;
	public AlbumUI() {

		setTitle("Album Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 450);
		//Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		//setLocation((int)(screensize.getWidth()/2-getWidth()/2), (int)(screensize.getHeight()/2-getHeight()/2));
		setLayout(new BorderLayout());
		setResizable(false);
		connector = DBConnector.getDBconnector();
		
		//탑부분
		toppn = new JPanel();
		toppn.setBackground(Color.green);
		toppn.setAlignmentX(CENTER_ALIGNMENT);
		
		showlistbtn = new JButton("리스트보기");
		showlistbtn.addActionListener(this);
		toppn.add(showlistbtn);
		
		managebtn = new JButton("관리하기");
		managebtn.addActionListener(this);
		toppn.add(managebtn);
		
		add(toppn, BorderLayout.NORTH);

		
		//왼쪽
		//----리스트보기
		leftpn = new JPanel();
		leftpn.setLayout(new GridLayout(15,1));

		leftpn.setBackground(Color.yellow);
		
		Dimension leftbtndimension = new Dimension();
		leftbtndimension.setSize(100, 50);
		albumlistbtn = new JButton("앨범리스트");
		albumlistbtn.setPreferredSize(leftbtndimension);
		albumlistbtn.addActionListener(this);
		leftpn.add(albumlistbtn);
		
		songlistbtn = new JButton("곡리스트");
		songlistbtn.addActionListener(this);
		leftpn.add(songlistbtn);
		leftpn.setVisible(false);
		
		
	
		
		//----매니저보기
		leftpn2 = new JPanel();
		leftpn2.setLayout(new GridLayout(15, 1));
		leftpn2.setBackground(Color.yellow);
		
		addbtn = new JButton("앨범추가");
		addbtn.setPreferredSize(leftbtndimension);
		addbtn.addActionListener(this);	
		leftpn2.add(addbtn);
		
		
		songaddbtn = new JButton("곡추가");
		songaddbtn.setPreferredSize(leftbtndimension);
		songaddbtn.addActionListener(this);
		leftpn2.add(songaddbtn);
		
		leftpn2.setVisible(false);
		
		
		
		//미들
		middlepn = new JPanel();
		middlepn.setBackground(Color.black);
		middlepn.setLayout(new BoxLayout(middlepn, BoxLayout.Y_AXIS));
	
		scrollpane = new JScrollPane(middlepn);
		add(scrollpane, BorderLayout.CENTER);
		
		//바텀
		bottompn = new JPanel();
		bottompn.setBackground(Color.cyan);
		add(bottompn, BorderLayout.SOUTH);
		
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

				Object eventobj = e.getSource();
				if(eventobj == showlistbtn ){ //목록보기
					leftpn2.setVisible(false);
					
					add(leftpn, BorderLayout.WEST);
					leftpn.setVisible(true);
					System.out.println("목록버튼");
				}else if(eventobj == managebtn){//관리
					leftpn.setVisible(false);
					add(leftpn2, BorderLayout.WEST);
					leftpn2.setVisible(true);
					System.out.println("관리버튼"); 
				
				}else if(eventobj == albumlistbtn){
					middlepn.setVisible(false);
					middlepn.removeAll();
				
					ShowListPanel showpanel = new ShowListPanel();
					middlepn.add(showpanel);
					middlepn.setVisible(true);
					System.out.println("앨범버튼");
				}else if(eventobj == songlistbtn){
					middlepn.setVisible(false);
					middlepn.removeAll();
					SongListPanel songlistpanel = new SongListPanel();
					middlepn.add(songlistpanel);
					middlepn.setVisible(true);
					System.out.println("곡버튼");
				}else if(eventobj == addbtn){
					
					middlepn.setVisible(false);
					middlepn.removeAll();
					AddPanel addpanel = new AddPanel();
					middlepn.add(addpanel);
					middlepn.setVisible(true);
					System.out.println("추가버튼");
				
					
				}else if(eventobj == songaddbtn){
					middlepn.setVisible(false);
					middlepn.removeAll();
					SongAddPanel songaddpanel = new SongAddPanel();
					middlepn.add(songaddpanel);
					middlepn.setVisible(true);
					System.out.println("곡추가버튼");
				}
				repaint();
	}
	
}
