//	Name:		Rabb, Christopher
//	Homework:	1
//	Due:		October 11, 2015
//	Course:		cs-245-01-f15
//
//	Description:	This image viewer program allows a user to enter 
//					an image file path upon execution and the image
//					will be displayed in a JFrame.

import java.awt.BorderLayout;
import javax.swing.*;

public class ImageViewer {
	
	public ImageViewer(String str)
	{
		ImageIcon icon = new ImageIcon(str);
		JFrame frame = new JFrame("C. Rabb's Image Viewer");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		JLabel image = new JLabel(icon, JLabel.CENTER);
		JLabel caption = new JLabel(str, JLabel.CENTER);
		
		frame.add(caption, BorderLayout.SOUTH);
		frame.add(image, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
	
	public static void main(String [] args)
	{
		final String arg = args[0];
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				new ImageViewer(arg);
			}
		});
	}
	
}
