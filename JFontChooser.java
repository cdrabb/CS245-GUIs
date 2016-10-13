import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class JFontChooser {
	
	Font font;
	Color color;
	JDialog frame;
	JList<String> fl;
	JList<String> stl;
	JList<Integer> sil;
	public JFontChooser(JFrame parent)
	{
		frame = new JDialog(parent, "Font", true);
		frame.setSize(350,300);
		frame.setLayout(new FlowLayout());//GridLayout(1,3));
		
		DefaultListModel <String> fonts = new DefaultListModel <String>();
		DefaultListModel <String> styles = new DefaultListModel<String>();
		DefaultListModel <Integer> sizes = new DefaultListModel<Integer>();
		
		String [] array = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(String f : array)
			fonts.addElement(f);
		
		styles.addElement("Regular");
		styles.addElement("Italic");
		styles.addElement("Bold");
		
		for(int i = 2; i < 76; i++)
			sizes.addElement(i++);
		
		fl = new JList<String>(fonts);
		stl = new JList<String>(styles);
		sil = new JList<Integer>(sizes);
		JScrollPane fp = new JScrollPane(fl);
		JScrollPane stp = new JScrollPane(stl);
		JScrollPane sip = new JScrollPane(sil);
		
		fp.setSize(155,300);
		stl.setSize(155,300);
		sil.setSize(155,300);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				int style = 0;
				if(stl.getSelectedValue().equals("Regular"))
					style = Font.PLAIN;
				else if(stl.getSelectedValue().equals("Bold"))
					style = Font.BOLD;
				else if(stl.getSelectedValue().equals("Italic"))
					style = Font.ITALIC;
				
				int size = sil.getSelectedValue();
				
				font = new Font(fl.getSelectedValue(),style,size);
				frame.dispose();
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				frame.dispose();
			}
		});
		
		JPanel buttons = new JPanel();
		buttons.add(ok);
		buttons.add(cancel);
		
		frame.add(fp);
		frame.add(stp);
		frame.add(sip);
		frame.add(buttons);
	}
	public void setDefault(Font font)
	{
		this.font = font;
	}
	public void setDefault(Color color)
	{
		this.color = color;
	}
	public Font getFont()
	{
		return font;
	}
	public Color getColor()
	{
		return color;
	}
	public Font showDialog(JFrame parent)
	{
		frame.setVisible(true);
		return getFont();
	}
}
