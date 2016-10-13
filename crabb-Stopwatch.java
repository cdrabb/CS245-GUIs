//
//	Name:		Rabb, Christopher
//	Project:	1
//	Due:		Wednesday, October 14th
//	Course:		cs-245-01-f15
//
//	Description:
//				A stopwatch program that begins counting upon pressing the start button.
//				Pressing stop will stop the counter and display the time elapsed.

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Stopwatch implements ActionListener{

	long startTime;
	JLabel label;
	
	public Stopwatch()
	{	
        JFrame frame = new JFrame("C. Rabb's Stopwatch");
		frame.setSize(200, 100);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnStart = new JButton("Start");
		JButton btnStop = new JButton("Stop");
		
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		
		label = new JLabel("Press Start to begin timing.");
		
		frame.add(btnStart);
		frame.add(btnStop);
		frame.add(label);
		
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		
		if(ae.getActionCommand().equals("Start"))
		{
			startTime = ae.getWhen();
			label.setText("The clock is running...");
		}
		else
		{
			startTime = (ae.getWhen()-startTime)/1000;
			label.setText("Time elapsed: " + (double)startTime + " seconds!");		
		}
	}
	
	public static void main(String [] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new Stopwatch();
			}
		});
	}
}
