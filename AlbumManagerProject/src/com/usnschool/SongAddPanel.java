package com.usnschool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class SongAddPanel extends JPanel{
	private JLabel albumname;
	private int currentnum;
	private String currentstring = "";
	private int mainpnwidth = 491;
	private int mainpnheight = 371;
	private int topwidth;
	private int topheight;
	private int currentplusbtny = 0;
	private int currentplusnum = 0;
	private ArrayList<EachSong> eachsonglist = new ArrayList<>();
	private JPanel center; 
	private JScrollPane centerscrollpane;
	private JPanel addpn;
	
	public SongAddPanel() {
		setLayout(new GridLayout(1,1));
		
		//메인패널
		JPanel mainpn = new JPanel();
		mainpn.setLayout(null);
		
		add(mainpn);
		
		//상단
		JPanel top = new JPanel();
		topwidth = mainpnwidth;
		topheight = mainpnheight/9;
		top.setBounds(0, 0, topwidth, topheight);
		
		top.setBackground(Color.green);
		mainpn.add(top);
		
		//--문자레이블
		albumname = new JLabel("선택된 앨범");
		top.add(albumname);
		
		
		//--앨범선택버튼
		JButton selectbtn = new JButton("앨범선택");
		selectbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SelectAlbumPanel(SongAddPanel.this);
				System.out.println(mainpn.getWidth());
				System.out.println(mainpn.getHeight());
				
			}
		});
		top.add(selectbtn);
		
		//중앙
		center = new JPanel();
		center.setLayout(null);
		center.setBounds(0, topheight, mainpnwidth, mainpnheight-topheight*2);
	
		
		centerscrollpane = new JScrollPane(center);
		centerscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		centerscrollpane.setBounds(0, topheight, mainpnwidth, mainpnheight-topheight*2);
		
		addpn = new JPanel();
		addpn.setBackground(Color.gray);
		addpn.setBounds(0, 0, mainpnwidth, topheight);
		JButton addpnbtn = new JButton("+");
		addpnbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//더해지는 노래추가 패널
				EachSong songaddpn = new EachSong(currentplusnum);
				songaddpn.setBackground(Color.CYAN);
				songaddpn.setBounds(0, currentplusbtny, mainpnwidth, topheight);
				
				eachsonglist.add(songaddpn);
				center.add(songaddpn);
				
				currentplusbtny += topheight;
				addpn.setBounds(0, currentplusbtny, mainpnwidth, topheight);
				
			
			
				center.setPreferredSize(new Dimension(mainpnwidth, mainpnheight-topheight*4+currentplusbtny));
				centerscrollpane.setViewportView(center);
				currentplusnum++;
				System.out.println(currentplusnum);
			}
		});
		addpn.add(addpnbtn);
		center.add(addpn);
		mainpn.add(centerscrollpane);
		
		
		//하단
		JPanel bottom = new JPanel();
		bottom.setBounds(0, mainpnheight-topheight, mainpnwidth, topheight);
		bottom.setBackground(Color.green);
		JButton savebtn = new JButton("저장하기");
		savebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentstring.equals("")){
					JOptionPane.showMessageDialog(SongAddPanel.this,  "앨범을 선택해주세요.");
				}else {
					DBConnector connector = DBConnector.getDBconnector();
					connector.insertIntoSongDB(eachsonglist, currentnum);
					JOptionPane.showMessageDialog(SongAddPanel.this,  "저장되었습니다.");
				}

			}
		});
		bottom.add(savebtn);
		mainpn.add(bottom);
		
		
	}
	
	class EachSong extends JPanel{
		private int panelnum;
		private JTextField songname;
		private JTextField songcontent;
		private String songpath;
		public EachSong(int panelnum) {
			this.panelnum = panelnum;
			setLayout(new FlowLayout(FlowLayout.LEFT));
			
			//-버튼
			JButton jbtn = new JButton("-");
			jbtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					//누른위치패널 삭제
					center.remove(eachsonglist.get(EachSong.this.panelnum));
					eachsonglist.remove(EachSong.this.panelnum);

					//누른위치 다음패널들 이동
					for (int i = EachSong.this.panelnum; i < eachsonglist.size(); i++) {
						eachsonglist.get(i).setBounds(0, (int)(eachsonglist.get(i).getBounds().getY()-topheight), mainpnwidth, topheight);
						eachsonglist.get(i).setPanelnum();
					}
					//+버튼이동
					currentplusbtny -= topheight;
					currentplusnum--;
					addpn.setBounds(0, currentplusbtny, mainpnwidth, topheight);
					
					//스크롤팬 변경
					center.setPreferredSize(new Dimension(mainpnwidth, mainpnheight-topheight*4+currentplusbtny));
					centerscrollpane.setViewportView(center);

				}
			});

			add(jbtn);
			
			//곡명
			JLabel songnamela = new JLabel("곡명");
			add(songnamela);
			songname = new JTextField(10);
			add(songname);
			
			//가사
			JLabel songcontentla = new JLabel("가사");
			add(songcontentla);
			songcontent = new JTextField(10);
			add(songcontent);
			JButton inputcontent = new JButton("크게");
			inputcontent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new SongContentFrame(songcontent);
				}
			});
			add(inputcontent);
			
			JButton songblob = new JButton("추가");
			songblob.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFrame jf = new JFrame();
					jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					FileDialog dialog = new FileDialog(jf,"Open", FileDialog.LOAD);
					dialog.setFile("*.*");
					dialog.setVisible(true);
					String dirname = dialog.getDirectory();
					String filename = dialog.getFile();
					songpath = dirname + filename;
				}
			});
			add(songblob);
		}
		
		public void setPanelnum(){
			panelnum--;
		}
		
		public String getSongname(){
			return songname.getText();
		}
		
		public String getSongcontent(){
			return songcontent.getText();
		}
		
		public String getSongpath(){
			return songpath;
		}
	}

	
	
	public void setCurrentAlbum(int currentnum, String currentstring){
		this.currentnum = currentnum;
		this.currentstring = currentstring;
		albumname.setText(currentstring);
		
	}
	

}
