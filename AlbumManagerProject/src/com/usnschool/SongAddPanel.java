package com.usnschool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SongAddPanel extends JPanel{
	public SongAddPanel() {
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(1,1));
		
		JPanel mainpn = new JPanel();
		mainpn.setBackground(Color.black);
		mainpn.setLayout(new BoxLayout(mainpn, BoxLayout.Y_AXIS));
		JScrollPane scrollpane = new JScrollPane(mainpn);
		add(scrollpane);
		
		//상단
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(43, 50));
		top.setBackground(Color.green);
		mainpn.add(top);
		
		//하단
		JPanel bottom = new JPanel();
		//bottom.setPreferredSize(new Dimension(getHeight(), getHeight()));
		bottom.setBackground(Color.blue);
		mainpn.add(bottom);
		
		
	}
}
