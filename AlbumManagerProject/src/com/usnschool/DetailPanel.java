package com.usnschool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DetailPanel extends JFrame{
	private JLabel genre, singer, writer, writerrythm, relday, publisher, planner;
	private JLabel genretf, singertf, writertf, writerrythmtf, reldaytf, publishertf, plannertf;
	private JTextArea introduce;
	
	public DetailPanel(int inumf) {
		setTitle("상세보기");
		setSize(500,500);
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
		AlbumData albumdata = ((connector.getAlbumList()).get(inumf));
		DetailImgPanel topleftpn = null;
		try {
			topleftpn = new DetailImgPanel(ImageIO.read(albumdata.getImgstream()));
			System.out.println("상단의 좌 : ");
			System.out.println(albumdata.getImgstream().hashCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		topleftpn.setBackground(Color.RED);
		toppn.add(topleftpn);
		
		//-상단의 우
		JPanel toprightpn = new JPanel();
		
		toprightpn.setLayout(new GridLayout(7,2));
		//----장르
		genre = new JLabel("장르");
		toprightpn.add(genre);
		genretf = new JLabel(albumdata.getGenre());
		toprightpn.add(genretf);
		//----가수
		
		singer = new JLabel("가수");
		toprightpn.add(singer);
		singertf = new JLabel(albumdata.getSinger());
		toprightpn.add(singertf);
		
		//----작사
		writer = new JLabel("작사");
		toprightpn.add(writer);
		writertf = new JLabel(albumdata.getWriter());
		toprightpn.add(writertf);
		
		//----작곡
		
		writerrythm = new JLabel("작곡");
		toprightpn.add(writerrythm);
		writerrythmtf = new JLabel(albumdata.getWriterrythm());
		toprightpn.add(writerrythmtf);
		//----발매일
		relday = new JLabel("발매일");
		toprightpn.add(relday);
		reldaytf = new JLabel(albumdata.getRelday());
		toprightpn.add(reldaytf);
		
		//----발매사
		publisher = new JLabel("발매사");
		toprightpn.add(publisher);
		publishertf = new JLabel(albumdata.getPublisher());
		toprightpn.add(publishertf);
		
		//----기획사
		planner = new JLabel("기획사");
		toprightpn.add(planner);
		plannertf = new JLabel(albumdata.getPlanner());
		toprightpn.add(plannertf);
		
		
		toppn.add(toprightpn);
	
		mainpn.add(toppn);
		
		//중단패널
		JPanel centerpn = new JPanel();
		introduce = new JTextArea(6, 40);
		introduce.setEditable(false);
		JScrollPane introscrollpane = new JScrollPane(introduce);
		centerpn.add(introscrollpane);
		centerpn.setBackground(Color.blue);
		centerpn.setPreferredSize(new Dimension(30, 110));
		mainpn.add(centerpn);
		
		
		//하단패널
		JPanel bottompn = new JPanel();
		bottompn.setBackground(Color.gray);
		bottompn.setPreferredSize(new Dimension(30, 300));
		mainpn.add(bottompn);
		
		setVisible(true);
		
	}
	
	class DetailImgPanel extends JPanel{
		BufferedImage is ;
		
		DetailImgPanel(BufferedImage is) {
			this.is = is;

		}

	
		
		@Override
		public void paint(Graphics g) {
			g.drawImage(is, (int)(getWidth()*0.05), (int)(getHeight()*0.05), (int)(getWidth()*0.9), (int)(getHeight()*0.9), null);
			
			
		}
		
		
	}
}
