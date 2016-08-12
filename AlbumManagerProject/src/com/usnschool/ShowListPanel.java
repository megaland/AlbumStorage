package com.usnschool;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ShowListPanel extends JPanel {
	private JPanel pn[];
	private JLabel[][] label;
	private DBConnector connector;
	private ImgPanel imgpanel;
	
	public ShowListPanel() {
		setLayout(new GridLayout(15,1));
		
		connector = DBConnector.getDBconnector();
		ArrayList<AlbumData> arrdata = connector.getAlbumList();
		pn = new JPanel[arrdata.size()];
		
		Dimension dimension = new Dimension();
		dimension.setSize(20, 50);
		
		JPanel titlepanel = new JPanel();
		JLabel titlelabel1 = new JLabel("번호" ,SwingConstants.CENTER);
		titlepanel.add(titlelabel1);
		JLabel titlelabel2 = new JLabel("그림" , SwingConstants.CENTER);
		titlepanel.add(titlelabel2);
		JLabel titlelabel3 = new JLabel("가수" , SwingConstants.CENTER);
		titlepanel.add(titlelabel3);
		JLabel titlelabel4 = new JLabel("장르" , SwingConstants.CENTER);
		titlepanel.add(titlelabel4);
		JLabel titlelabel5 = new JLabel("발매일" , SwingConstants.CENTER);
		titlepanel.add(titlelabel5);
		titlepanel.setLayout(new GridLayout(1,5));
		titlepanel.setPreferredSize(dimension);
		add(titlepanel);
		
		label = new JLabel[arrdata.size()][4];
		for (int i = 0; i < arrdata.size(); i++) {
			pn[i] = new JPanel();
			pn[i].setPreferredSize(dimension);
			pn[i].setLayout(new GridLayout(1,5));
			label[i][0] = new JLabel(""+(i+1), SwingConstants.CENTER);	
			label[i][1] = new JLabel(arrdata.get(i).getSinger(), SwingConstants.CENTER);
			label[i][2] = new JLabel(arrdata.get(i).getGenre(), SwingConstants.CENTER);
			label[i][3] = new JLabel(arrdata.get(i).getRelday(), SwingConstants.CENTER);
			
			try {
				if(arrdata.get(i).getImgstream()!=null){
					imgpanel = new ImgPanel(ImageIO.read(((arrdata.get(i)).getImgstream())));
				}else {
					imgpanel = new ImgPanel(ImageIO.read(new File("no.bmp")));
				}
	
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			pn[i].add(label[i][0]);
			pn[i].add(imgpanel);
			pn[i].add(label[i][1]);
			pn[i].add(label[i][2]);
			pn[i].add(label[i][3]);
			
			//여기서 사용되고 있는 객체를 JFrame으로 넘겨주면 안되는듯 같은 객체를 사용해서
			final int inumf = i;
			pn[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println(inumf);
							new DetailPanel(inumf);
				}
			});
			add(pn[i]);
		}
		setVisible(true);
	}
	
	class ImgPanel extends JPanel{
		BufferedImage is;
		public ImgPanel(BufferedImage is) {
			this.is = is;
		}

		@Override
		public void paint(Graphics g) {
			g.drawImage(is, 22, 0, 50, getParent().getHeight(), null);
			
		}
	}
}
