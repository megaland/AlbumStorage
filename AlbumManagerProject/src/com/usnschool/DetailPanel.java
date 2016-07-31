package com.usnschool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Currency;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DetailPanel extends JFrame{
	private JLabel genre, singer, writer, writerrythm, relday, publisher, planner, albumname;
	private JTextField genretf, singertf, writertf, writerrythmtf, reldaytf, publishertf, plannertf, albumnametf;
	private JTextArea introduce;
	private int albumnum;
	private final int FRAME_WIDTH = 500;
	private final int FRAME_HEIGHT = 500;
	private final int SONG_PANEL_HEIGHT = 50;
	private int curreny;
	private boolean btncheck = true;
	private AlbumData albumdata;
	private BufferedImage img;
	private DetailImgPanel topleftpn;
	private InputStream tempinputstream;
	private ArrayList<SongData> songdata;
	
	public DetailPanel(int inumf) {
		setTitle("상세보기");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		
		//전체패널
		JPanel mainpn = new JPanel();
		mainpn.setLayout(new BoxLayout(mainpn, BoxLayout.Y_AXIS));
		JScrollPane scrollpane = new JScrollPane(mainpn);
		add(scrollpane);
		
		//상단패널
		JPanel toppn = new JPanel();
		toppn.setLayout(new GridLayout(1,2));
		toppn.setBackground(Color.GREEN);
		toppn.setPreferredSize(new Dimension(150, 300));

		
		//-상단의 좌
		DBConnector connector = DBConnector.getDBconnector();
		albumdata = ((connector.getAlbumList()).get(inumf));
		topleftpn = null;
		try {
			tempinputstream = albumdata.getImgstream();
			topleftpn = new DetailImgPanel(ImageIO.read(tempinputstream));
		} catch (Exception e) {
			e.printStackTrace();
		}
		topleftpn.setBackground(Color.RED);
		toppn.add(topleftpn);
		
		//-상단의 우
		JPanel toprightpn = new JPanel();
		
		toprightpn.setLayout(new GridLayout(8,2));
		
		//----앨범번호넣기
		albumnum = albumdata.getNum();
		
		//----앨범명
		albumname = new JLabel("앨범명");
		toprightpn.add(albumname);
		albumnametf = new JTextField(albumdata.getAlbumname());
		albumnametf.setEditable(false);
		toprightpn.add(albumnametf);
		
		//----장르
		genre = new JLabel("장르");
		toprightpn.add(genre);
		genretf = new JTextField(albumdata.getGenre());
		genretf.setEditable(false);
		toprightpn.add(genretf);
		//----가수
		
		singer = new JLabel("가수");
		toprightpn.add(singer);
		singertf = new JTextField(albumdata.getSinger());
		singertf.setEditable(false);
		toprightpn.add(singertf);
		
		//----작사
		writer = new JLabel("작사");
		toprightpn.add(writer);
		writertf = new JTextField(albumdata.getWriter());
		writertf.setEditable(false);
		toprightpn.add(writertf);
		
		//----작곡
		
		writerrythm = new JLabel("작곡");
		toprightpn.add(writerrythm);
		writerrythmtf = new JTextField(albumdata.getWriterrythm());
		writerrythmtf.setEditable(false);
		toprightpn.add(writerrythmtf);
		//----발매일
		relday = new JLabel("발매일");
		toprightpn.add(relday);
		reldaytf = new JTextField(albumdata.getRelday());
		reldaytf.setEditable(false);
		toprightpn.add(reldaytf);
		
		//----발매사
		publisher = new JLabel("발매사");
		toprightpn.add(publisher);
		publishertf = new JTextField(albumdata.getPublisher());
		publishertf.setEditable(false);
		toprightpn.add(publishertf);
		
		//----기획사
		planner = new JLabel("기획사");
		toprightpn.add(planner);
		plannertf = new JTextField(albumdata.getPlanner());
		plannertf.setEditable(false);
		toprightpn.add(plannertf);
		
		
		toppn.add(toprightpn);
		mainpn.add(toppn);
		
		//버튼 패널
		JPanel buttonpn = new JPanel();
		buttonpn.setBackground(Color.darkGray);
		JButton modifybtn = new JButton("수정하기");
		modifybtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btncheck){
					genretf.setEditable(true);
					singertf.setEditable(true);
					writertf.setEditable(true);
					writerrythmtf.setEditable(true);
					reldaytf.setEditable(true);
					publishertf.setEditable(true);
					plannertf.setEditable(true);
					albumnametf.setEditable(true);
					introduce.setEditable(true);
					modifybtn.setText("수정완료");
					btncheck = false;
				}else {
					albumdata.setGenre(genretf.getText());
					albumdata.setSinger(singertf.getText());
					albumdata.setWriter(writertf.getText());
					albumdata.setWriterrythm(writerrythmtf.getText());
					albumdata.setRelday(reldaytf.getText());
					albumdata.setPublisher(publishertf.getText());
					albumdata.setPlanner(plannertf.getText());
					albumdata.setAlbumname(albumnametf.getText());
					albumdata.setIntroduce(introduce.getText());
					//albumdata.setImgstream(tempinputstream);
					System.out.println(albumdata.getImgstream()==tempinputstream);
					//inputstream을 그대로 사용해서 업데이트를 하면 그림이 제대로 저장이 안된다.
					if(albumdata.getImgstream()==tempinputstream){
						connector.updateAlbumNo(albumdata);
					}else{
						connector.updateAlbum(albumdata);
					}
					genretf.setEditable(false);
					singertf.setEditable(false);
					writertf.setEditable(false);
					writerrythmtf.setEditable(false);
					reldaytf.setEditable(false);
					publishertf.setEditable(false);
					plannertf.setEditable(false);
					albumnametf.setEditable(false);
					introduce.setEditable(false);
					modifybtn.setText("수정하기");
					btncheck = true;
					System.out.println("수정되었습니다.");
				}
				
				
			}
		});
		buttonpn.add(modifybtn);
		
		JButton picturechangebtn = new JButton("사진바꾸기");
		picturechangebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jf = new JFrame();
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				FileDialog dialog = new FileDialog(jf,"Open", FileDialog.LOAD);
				dialog.setFile("*.*");
				dialog.setVisible(true);
				String dirname = dialog.getDirectory();
				String filename = dialog.getFile();
				String imgpath = dirname + filename;
				System.out.println(dirname + filename);
				
				try {
					File file = new File(dirname+filename);
					albumdata.setImgstream(new FileInputStream(file));
					img = ImageIO.read(file);
					topleftpn.setIs(img);
					repaint();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		buttonpn.add(picturechangebtn);
		JButton deletebtn = new JButton("삭제하기");
		deletebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connector.DeleteAlbumAndSong(albumnum);
				DetailPanel.this.dispose();
				System.out.println("삭제되었습니다");
				
			}
		});
		buttonpn.add(deletebtn);
		
		
		mainpn.add(buttonpn);
		
		//중간패널
		JPanel centerpn = new JPanel();
		introduce = new JTextArea(6, 40);
		introduce.setText(albumdata.getIntroduce());
		introduce.setEditable(false);
		JScrollPane introscrollpane = new JScrollPane(introduce);
		centerpn.add(introscrollpane);
		centerpn.setBackground(Color.blue);
		centerpn.setPreferredSize(new Dimension(30, 110));
		mainpn.add(centerpn);
		
		
		//하단패널
		JPanel bottompn = new JPanel();
		bottompn.setLayout(null);
		bottompn.setBackground(Color.gray);
		bottompn.setPreferredSize(new Dimension(30, 100));
		mainpn.add(bottompn);
		songdata = connector.getSongDataList(albumnum);
		for (int i = 0; i < songdata.size(); i++) {
			
			DetailSongListPanel songpn = new DetailSongListPanel(songdata.get(i), i);
			songpn.setBounds(0, curreny,  FRAME_WIDTH, SONG_PANEL_HEIGHT);
			curreny += SONG_PANEL_HEIGHT;
			bottompn.setPreferredSize(new Dimension(30, 100+curreny));
			bottompn.add(songpn);
		}
		
		setVisible(true);
		
	}
	
	class DetailImgPanel extends JPanel{
		BufferedImage is;
		DetailImgPanel(BufferedImage is) {
			this.is = is;
		}

		@Override
		public void paint(Graphics g) {
			
			g.drawImage(is, (int)(getWidth()*0.05), (int)(getHeight()*0.05), (int)(getWidth()*0.9), (int)(getHeight()*0.9), null);
		}
		
		public void setIs(BufferedImage img){
			is = img;
		}
	}

	class DetailSongListPanel extends JPanel{
		
		public DetailSongListPanel(SongData songdata, int songnum) {
			setLayout(null);
			JLabel songnumla = new JLabel(""+(songnum+1) , SwingConstants.CENTER);
			songnumla.setBounds(0, 0, 150, SONG_PANEL_HEIGHT);
			add(songnumla);
			
			JLabel songname = new JLabel(songdata.getSongname(), SwingConstants.LEFT);
			songname.setBounds(150, 0, 250, SONG_PANEL_HEIGHT);
			add(songname);
			
			JButton songcontentshow = new JButton("가사보기");
			songcontentshow.setBounds(350, 0, 100, SONG_PANEL_HEIGHT);
			songcontentshow.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new SongContentDetail(songdata);
				}
			});

			add(songcontentshow);
		}
	}
	
}
