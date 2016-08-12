package com.usnschool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.mpatric.mp3agic.Mp3File;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class PlayerFrame extends JFrame{
	private boolean flag = true;
	private AdvancedPlayer adplayer;
	private InputStream currentstream;
	private Thread thread;
	private Mp3File currentmp3file;
	private JSlider jslider;
	private boolean timestopper = true;
	private LinkedList<SongData> checkedlist ;
	private JList<String> jlist;
	
	public PlayerFrame(LinkedList<SongData> checkedlist) {
		//DB에 binarystream으로 넣은 스트림이 아닌 fileinputstream으로 받아온스트림은 reset이 제대로 먹히지 않는다.
		//그리고 플레이어를 close시키면 스트림이 종료된다.
		this.checkedlist = checkedlist;
		setSize(500,300);
		setTitle("MP3Player");
		setLayout(new BorderLayout());
		
		JsliderThread jsthread = new JsliderThread();
		jsthread.start();
		
		currentstream = checkedlist.get(0).getSongblob();
		changePlayer();

		//탑
		JPanel toppn = new JPanel();
		toppn.setBackground(Color.red);
		add(toppn, BorderLayout.NORTH);
		
		JButton startbtn = new JButton("스타트");
		startbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread = new Thread(){
					public  void run() {
						try {
							jslider.setValue(0);
							timestopper = false;
							adplayer.play();
						} catch (JavaLayerException e) {
							e.printStackTrace();
						}
					};
				};
				thread.start();
			}
		});
		toppn.add(startbtn);
		
		JButton stopbtn = new JButton("정지");
		stopbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timestopper = true;
				changePlayer();
			}
		});
		toppn.add(stopbtn);
		
/*		JButton pausebtn = new JButton("일시정지");
		pausebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(){
					@Override
					public void run() {
						try {
							
							System.out.println("clcick");
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}

				}.start();

			}
		});
		toppn.add(pausebtn);*/
		
		jslider = new JSlider(0, 100);
		jslider.setValue(0);
		jslider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(adplayer != null){
					jslider.setMinimum(0);
					jslider.setMaximum(jslider.getWidth());
					jslider.setValue(e.getX());
					
					final int getx = e.getX();
				
					changePlayer();
					try {
						new Thread(){
							public void run() {
								try {
									System.out.println((int)((double)getx/(double)(jslider.getWidth())
											*currentmp3file.getFrameCount())
											+" : "+ currentmp3file.getFrameCount());
									timestopper = false;
									adplayer.play((int)((double)getx/(double)(jslider.getWidth())
											*currentmp3file.getFrameCount()),
											currentmp3file.getFrameCount());
								} catch (JavaLayerException e) {
									e.printStackTrace();
								}
							};
						}.start();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		toppn.add(jslider);
		
		//센터
		JPanel centerpn = new JPanel();
		centerpn.setBackground(Color.YELLOW);
		centerpn.setLayout(new BorderLayout());
		add(centerpn, BorderLayout.CENTER);
		
		centerpn.add(new JLabel("목록"), BorderLayout.NORTH);
		
		jlist = new JList<>();
		jlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
			
				int selectindex = ((JList)e.getSource()).getSelectedIndex();
				if(flag){
					timestopper = true;
					currentstream = checkedlist.get(selectindex).getSongblob();
					try {
						currentstream.reset();
						File file = new File("mp3file");
						FileOutputStream fos = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						int length = 0;
						while((length = currentstream.read(buffer))!= -1){
							fos.write(buffer);
						}
						currentmp3file = new Mp3File(file);
						fos.close();
						file.delete();
						currentstream.reset();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					changePlayer();
				}
				flag = !flag;
			}
		});
	
		Vector<String> vector = new Vector<>();
		for (int i = 0; i < checkedlist.size(); i++) {
			vector.add(checkedlist.get(i).getSongname());
		}
		jlist.setSelectedIndex(0);
		jlist.setListData(vector);
		JScrollPane scrollpane = new JScrollPane(jlist);
		centerpn.add(scrollpane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void changePlayer(){
		try {
			if(adplayer !=null){
				System.out.println("adplayer close");
				adplayer.close();
			}
			currentstream.reset();
			adplayer = new AdvancedPlayer(currentstream);
			adplayer.setPlayBackListener(new PlaybackListener() {
				@Override
				public void playbackFinished(PlaybackEvent arg0) {
					super.playbackFinished(arg0);
				}
				@Override
				public void playbackStarted(PlaybackEvent arg0) {
					super.playbackStarted(arg0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class JsliderThread extends Thread{
		private double remainder = 0;
		@Override
		public void run() {
			while(true){
				try {
					if(!timestopper){
						Thread.sleep(1000);
						int integerpart = (int)((double)(jslider.getMaximum())
								/(double)(currentmp3file.getLengthInSeconds()));
						remainder += ((double)(jslider.getMaximum())
								/(double)(currentmp3file.getLengthInSeconds())) - integerpart;
						if(remainder > 1){
							remainder--;
							jslider.setValue(jslider.getValue()+integerpart+1);
						}else{
							jslider.setValue(jslider.getValue()+integerpart);
						}
						if(jslider.getValue() >= jslider.getMaximum()){
							if(adplayer != null){
								System.out.println("adplayer close");
								adplayer.close();
								
							}
							
							int randomnum = (int)(Math.random()*checkedlist.size());
							currentstream = checkedlist.get(randomnum).getSongblob();
							currentstream.reset();
							jslider.setValue(0);
							jlist.setSelectedIndex(randomnum);
							changePlayer();
							adplayer.play();
							
						}
					}else {
						Thread.sleep(100);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
	
			}
	
		}
	}
}
