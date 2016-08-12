package com.usnschool;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SongContentFrame extends JFrame{
	private JTextArea textarea;

	public SongContentFrame(JTextField songcontent) {

		setSize(300,200);
		setLayout(new BorderLayout());
		
		//텍스트
		textarea = new JTextArea(5, 50);
		textarea.setLineWrap(true);
		add(textarea, BorderLayout.CENTER);
		
		//버튼
		JButton button = new JButton("입력완료");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				songcontent.setText(textarea.getText());
				dispose();
			}
		});
		add(button, BorderLayout.SOUTH);
		setVisible(true);
	}
}
