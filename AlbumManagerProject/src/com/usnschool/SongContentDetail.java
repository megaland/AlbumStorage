package com.usnschool;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class SongContentDetail extends JFrame {
	private JTextArea textarea;
	
	public SongContentDetail(SongData songdata) {
		setSize(300,200);
		setLayout(new BorderLayout());
	
		
		//텍스트
		textarea = new JTextArea(5, 50);
		textarea.setLineWrap(true);
		textarea.setText(songdata.getSongcontent());
		textarea.setEditable(false);
		add(textarea, BorderLayout.CENTER);
		
		//버튼
		JButton button = new JButton("종료");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(button, BorderLayout.SOUTH);
		setVisible(true);
	}
}
