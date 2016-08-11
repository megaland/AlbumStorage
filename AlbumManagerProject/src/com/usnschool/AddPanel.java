package com.usnschool;


import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class AddPanel extends JPanel{
	private JLabel genre, singer, writer, writerrythm, relday, publisher, planner, albumname;
	private JTextField genretf, singertf, writertf, writerrythmtf, reldaytf, publishertf, plannertf, albumnametf;
	private JTextArea introduce;
	private BufferedImage img;
	private JPanel centerleftpn, centerrightpn, bottompn, bottomsouthpn, centerleftpicturepn;
	private JPanel centerdividepn;
	private JPanel picturefeaturepn;
	private JButton picturechangebtn, savebtn;
	private String dirname;
	private String filename;
	private JScrollPane scrollpane;
	private DBConnector connector;
	private String imgpath ="";
	
	public AddPanel() {
		setLayout(new BorderLayout());
		connector = DBConnector.getDBconnector();

		//센터패널
		centerdividepn = new JPanel();
		centerdividepn.setLayout(new GridLayout(1,2));
		//--좌
		centerleftpn = new JPanel();
		centerleftpn.setLayout(new BorderLayout());
		picturefeaturepn = new JPanel();
		centerleftpicturepn = new JPanel();
		centerleftpn.add(centerleftpicturepn, BorderLayout.CENTER);
		
		picturechangebtn = new JButton("사진바꾸기");
		picturechangebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jf = new JFrame();
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				FileDialog dialog = new FileDialog(jf,"Open", FileDialog.LOAD);
				dialog.setFile("*.*");
				dialog.setVisible(true);
				dirname = dialog.getDirectory();
				filename = dialog.getFile();
				imgpath = dirname + filename;
				System.out.println(dirname + filename);
				
				try {
					img = ImageIO.read(new File(dirname+filename));
					repaint();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		picturefeaturepn.add(picturechangebtn);
		centerleftpn.add(picturefeaturepn, BorderLayout.SOUTH);
		
		centerdividepn.add(centerleftpn);
		
		//--우
		centerrightpn = new JPanel();
		centerrightpn.setLayout(new GridLayout(8,2));
		
		//----앨범명
		albumname = new JLabel("앨범명");
		centerrightpn.add(albumname);
		albumnametf = new JTextField(2);
		centerrightpn.add(albumnametf);
		
		//----장르
		genre = new JLabel("장르");
		centerrightpn.add(genre);
		genretf = new JTextField(2);
		centerrightpn.add(genretf);
		//----가수
		
		singer = new JLabel("가수");
		centerrightpn.add(singer);
		singertf = new JTextField();
		centerrightpn.add(singertf);
		
		//----작사
		writer = new JLabel("작사");
		centerrightpn.add(writer);
		writertf = new JTextField();
		centerrightpn.add(writertf);
		
		//----작곡
		writerrythm = new JLabel("작곡");
		centerrightpn.add(writerrythm);
		writerrythmtf = new JTextField();
		centerrightpn.add(writerrythmtf);
		
		//----발매일
		relday = new JLabel("발매일");
		centerrightpn.add(relday);
		reldaytf = new JTextField();
		centerrightpn.add(reldaytf);
		
		//----발매사
		publisher = new JLabel("발매사");
		centerrightpn.add(publisher);
		publishertf = new JTextField();
		centerrightpn.add(publishertf);
		
		//----기획사
		planner = new JLabel("기획사");
		centerrightpn.add(planner);
		plannertf = new JTextField();
		centerrightpn.add(plannertf);
		
		
		centerdividepn.add(centerrightpn);

		add(centerdividepn, BorderLayout.CENTER);

		//바텀패널
		
		bottompn = new JPanel();
		bottompn.setLayout(new BorderLayout());
		introduce = new JTextArea(5,35);
		scrollpane = new JScrollPane(introduce);
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		bottomsouthpn = new JPanel();
		savebtn = new JButton("저장하기");
		savebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//저장
				AlbumData albumdata = new AlbumData();
				albumdata.setGenre(genretf.getText());
				albumdata.setSinger(singertf.getText());
				albumdata.setWriter(writertf.getText());
				albumdata.setWriterrythm(writerrythmtf.getText());
				albumdata.setPublisher(publishertf.getText());
				albumdata.setRelday(reldaytf.getText());
				albumdata.setIntroduce(introduce.getText());
				albumdata.setPlanner(plannertf.getText());
				albumdata.setAlbumname(albumnametf.getText());
				InputStream imgstream = null;
				try {
					
					if(!(imgpath.equals(""))){
						imgstream = new FileInputStream(new File(imgpath));
					}
				
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				albumdata.setImgstream(imgstream);
				connector.insertIntoDB(albumdata);
				imgpath = "";
				System.out.println("저장합니다");
				
			}
		});
		bottomsouthpn.add(savebtn);

		bottompn.add(scrollpane, BorderLayout.CENTER);
		bottompn.add(bottomsouthpn, BorderLayout.SOUTH);
		add(bottompn, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(img != null){
			g.drawImage(img, (int)(centerleftpicturepn.getWidth()*0.05), (int)(centerleftpicturepn.getHeight()*0.05), (int)(centerleftpicturepn.getWidth()*0.85), centerleftpicturepn.getHeight(), null);
		}
	}
}
