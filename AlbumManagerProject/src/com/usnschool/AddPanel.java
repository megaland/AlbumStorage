package com.usnschool;


import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class AddPanel extends JPanel{
	JLabel genre, singer, writer, writerrythm, release, publisher, planner;
	JTextField genretf, singertf, writertf, writerrythmtf, releasetf, publishertf, plannertf;
	JTextArea textarea;
	BufferedImage img;
	JPanel centerleftpn, centerrightpn, bottompn, bottomsouthpn;
	JPanel centerdividepn;
	JPanel picturefeaturepn;
	JButton picturechangebtn, savebtn;
	String dirname;
	String filename;

	public AddPanel() {
		setLayout(new BorderLayout());
		

		//센터패널
		centerdividepn = new JPanel();
		centerdividepn.setLayout(new GridLayout(1,2));
		//--좌
		centerleftpn = new JPanel();
		centerleftpn.setLayout(new BorderLayout());
		picturefeaturepn = new JPanel();

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
				
				System.out.println(dirname + filename);
				
			}
		});
		picturefeaturepn.add(picturechangebtn);
		centerleftpn.add(picturefeaturepn, BorderLayout.SOUTH);
		
		centerdividepn.add(centerleftpn);
		
		//--우
		centerrightpn = new JPanel();
		centerrightpn.setLayout(new GridLayout(7,2));
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
		release = new JLabel("발매일");
		centerrightpn.add(release);
		releasetf = new JTextField();
		centerrightpn.add(releasetf);
		
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
		textarea = new JTextArea(5,35);
		bottomsouthpn = new JPanel();
		savebtn = new JButton("저장하기");
		savebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//저장
				System.out.println("저장합니다");
				
			}
		});
		bottomsouthpn.add(savebtn);

		bottompn.add(textarea, BorderLayout.CENTER);
		bottompn.add(bottomsouthpn, BorderLayout.SOUTH);
		add(bottompn, BorderLayout.SOUTH);
		
		setVisible(true);
		
		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(0, 0, 10, 10);
	
	}
}
