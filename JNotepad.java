/*
 * 	Name:		Rabb, Christopher
 * 	Project:	4
 * 	Due:		12/4/2015
 * 	Course:		cs-245-01-f15
 * 	
 * 	Description:
 * 			This program is a clone of notepad and attempts to replicate some of the features of notepad.
 * 			
 */

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;


public class JNotepad extends MouseAdapter implements ActionListener
{
	JTextArea text;
	JFrame frame;
	JPopupMenu popMenu;
	JMenuItem cut;
	JMenuItem copy;
	JMenuItem paste;
	JMenuItem sBar;
	JTextField jtf;
	JPanel statusBar;
	JLabel stats;
	boolean visible = false;
	boolean wrap = false;
	boolean match = false;
	boolean direction = false;
	String fileName = "Untitled.txt";
	JFileChooser chooser;
	JFontChooser jfc;
	
	public JNotepad()
	{
		frame = new JFrame("Notepad");
		frame.setSize(512,256);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{
		ImageIcon img = new ImageIcon("JNotepad.png");
		frame.setIconImage(img.getImage());
		}catch(Exception e){
			
		}
		jfc = new JFontChooser(frame);
		
		chooser = new JFileChooser();
		String [] ft = {"txt", "java"};
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT & JAVA files", ft);
		chooser.setFileFilter(filter);
		
		text = new JTextArea("");
		jfc.setDefault(new Font("Courier", Font.PLAIN, 12));
		text.setFont(jfc.getFont());
		text.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if(e.isPopupTrigger())
				{
					popMenu = new JPopupMenu();
					popMenu.add(cut);
					popMenu.add(copy);
					popMenu.add(paste);
					popMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if(e.isPopupTrigger())
				{
					popMenu = new JPopupMenu();
					popMenu.add(cut);
					popMenu.add(copy);
					popMenu.add(paste);
					popMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		statusBar = new JPanel();
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		
		stats = new JLabel("Status");
		stats.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JMenuBar jmb = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic('E');
		JMenu format = new JMenu("Format");
		format.setMnemonic('O');
		JMenu view = new JMenu("View");
		view.setMnemonic('V');
		JMenu help = new JMenu("Help");
		help.setMnemonic('H');
		
		JMenuItem newFile = new JMenuItem("New", 'N');
		newFile.addActionListener(this);
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		
		JMenuItem open = new JMenuItem("Open...");
		open.addActionListener(this);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		
		JMenuItem saveAs = new JMenuItem("Save As...");
		saveAs.addActionListener(this);
		
		JMenuItem ps = new JMenuItem("Page Setup...", 'U'); //disable
		ps.setEnabled(false);
		
		JMenuItem pr = new JMenuItem("Print..."); //disable
		pr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		pr.setEnabled(false);
		
		JMenuItem ex = new JMenuItem("Exit...", 'X');
		ex.addActionListener(this);
		
		KeyStroke accUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
		JMenuItem undo = new JMenuItem("Undo"); //disable
		undo.setMnemonic('U');
		undo.setAccelerator(accUndo);
		undo.setEnabled(false);
		
		KeyStroke accCut = KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK);
		JMenuItem cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(accCut);
		
		this.cut = new JMenuItem("Cut");
		this.cut.addActionListener(this);
		this.cut.setAccelerator(accCut);
		
		JMenuItem copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		
		this.copy = new JMenuItem("Copy");
		this.copy.addActionListener(this);
		this.copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		
		JMenuItem paste = new JMenuItem("Paste");
		paste.addActionListener(this);
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		
		this.paste = new JMenuItem("Paste");
		this.paste.addActionListener(this);
		this.paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		
		JMenuItem del = new JMenuItem("Delete");
		del.addActionListener(this);
		del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
		
		JMenuItem find = new JMenuItem("Find...");
		find.addActionListener(this);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
		
		JMenuItem findN = new JMenuItem("Find Next");
		findN.addActionListener(this);
		
		JMenuItem rep = new JMenuItem("Replace..."); //disable
		rep.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));
		rep.setEnabled(false);
		
		JMenuItem go = new JMenuItem("Goto...", 'G'); //disable
		go.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_MASK));
		go.setEnabled(false);;
		
		JMenuItem selAll = new JMenuItem("Select All");
		selAll.addActionListener(this);
		selAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		
		JMenuItem time = new JMenuItem("Time/Date");
		time.addActionListener(this);
		time.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
		
		JMenuItem wrap = new JMenuItem("Word Wrap", 'W');
		wrap.addActionListener(this);
		
		JMenuItem font = new JMenuItem("Font...");
		font.addActionListener(this);
		
		sBar = new JMenuItem("Status Bar", 'S');
		sBar.addActionListener(this);
		sBar.setEnabled(false);
		
		JMenuItem vHelp = new JMenuItem("View Help", 'H');
		vHelp.addActionListener(this);
		vHelp.setEnabled(false);
		
		JMenuItem about = new JMenuItem("About JNotepad");
		about.addActionListener(this);
		
		file.add(newFile);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.addSeparator();
		file.add(ps);
		file.add(pr);
		file.addSeparator();
		file.add(ex);
		
		edit.add(undo);
		edit.addSeparator();
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(del);
		edit.addSeparator();
		edit.add(find);
		edit.add(findN);
		edit.add(rep);
		edit.add(go);
		edit.addSeparator();
		edit.add(selAll);
		edit.add(time);
		
		format.add(wrap);
		format.add(font);
		
		view.add(sBar);
		
		help.add(vHelp);
		help.addSeparator();
		help.add(about);
		
		jmb.add(file);
		jmb.add(edit);
		jmb.add(format);
		jmb.add(view);
		jmb.add(help);	
		
		statusBar.add(stats);
		statusBar.setVisible(visible);
		
		
		JScrollPane scrollPane = new JScrollPane(text);
		frame.setJMenuBar(jmb);
		frame.add(scrollPane);
		frame.add(statusBar, BorderLayout.SOUTH);
		
		frame.setLocationByPlatform(true);
		//frame.pack();
		frame.setVisible(true);
	}
	public void autoSave(File file)
	{
		try {
    		PrintWriter writer = new PrintWriter(file);
    		writer.write(text.getText());
    		writer.close();
		
    	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}
	public void save()
	{
		int retrival = chooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		PrintWriter writer = new PrintWriter(chooser.getSelectedFile());
	    		writer.write(text.getText());
	    		writer.close();
			
	    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("New"))
		{
			if(text.getText().length() > 0)
			{
				int choice = JOptionPane.showOptionDialog(frame, "Do you want to save?", "Save?",
						    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						    null, null, null);
				if(choice == JOptionPane.YES_OPTION)
					save();
				
				if(choice != JOptionPane.CANCEL_OPTION)
				{
					text.setText("");
					fileName = "Untitled.txt";
				}
			}
			
		}
		else if(ae.getActionCommand().equals("Open..."))
		{
			StringBuffer buffer;
		    buffer = new StringBuffer();
		    int result= chooser.showOpenDialog(frame);
		    if(result==JFileChooser.APPROVE_OPTION)
		    {
		        try {
		            FileReader reader;
		            reader = null;
		            File file = chooser.getSelectedFile();
		            fileName = file.getName();
		            reader = new FileReader(file);
		            int i=0;
		            while(i!=-1)
		            {
		                i=reader.read();
		                char ch=(char) i;
		                buffer.append(ch);

		            }

		            text.setText(buffer.toString());
		            reader.close();
		        } catch (Exception ex) {
		        	
		        }
		    }
		}
		else if(ae.getActionCommand().equals("Save"))
		{
			autoSave(new File(fileName));
		}
		else if(ae.getActionCommand().equals("Save As..."))
		{
			save();
		}
		else if(ae.getActionCommand().equals("Cut"))
		{
			text.cut();
			stats.setText("Selection cut to clipboard.");
		}
		else if(ae.getActionCommand().equals("Copy"))
		{
			text.copy();
			stats.setText("Selection copied to clipboard.");
		}
		else if(ae.getActionCommand().equals("Paste"))
		{
			text.paste();
			stats.setText("Pasted selection from clipboard.");
		}
		else if(ae.getActionCommand().equals("Delete"))
		{
			String sel = text.getSelectedText();
			
			text.setText(text.getText().replace(sel, ""));
		}
		else if(ae.getActionCommand().equals("Find..."))
		{
			JDialog findDialog = new JDialog(frame,"Find");
			findDialog.setSize(500,175);
			findDialog.setLayout(new FlowLayout());
			JPanel findPan = new JPanel();
			JPanel findB = new JPanel();
			findB.setLayout(new GridLayout(2,1));
			JPanel findOp = new JPanel();
			findPan.add(new JLabel("Find what: "));
			jtf = new JTextField("",25);
			jtf.setSize(400,70);
			findPan.add(jtf);
			JButton findNext = new JButton("Find Next ");
			findNext.addActionListener(this);
			JButton cancel = new JButton("Cancel");
			cancel.addActionListener(this);
			findB.add(findNext);
			findB.add(cancel);
			JCheckBox match = new JCheckBox("Match Case", false);
			match.addActionListener(this);
			JPanel radB = new JPanel();
			ButtonGroup bg = new ButtonGroup();
			JRadioButton up = new JRadioButton("up", false);
			up.addActionListener(this);
			JRadioButton down = new JRadioButton("down", true);
			down.addActionListener(this);
			bg.add(up);
			bg.add(down);
			radB.add(up);
			radB.add(down);
			radB.setBorder(BorderFactory.createTitledBorder("Direction"));
			findOp.setSize(400,70);
			findOp.add(match);
			findOp.add(radB);
			findDialog.add(findPan);
			findDialog.add(findB);
			findDialog.add(findOp);
			
			findDialog.setVisible(true);
		}
		else if(ae.getActionCommand().equals("Find Next"))
		{
			try{String str = text.getSelectedText();
				if(str.length() > 0)
				{	
					int index = text.getText().indexOf(str,text.getCaretPosition());
					text.select(index, index+str.length());
				}
			}catch(NullPointerException e){
				
			}
		}
		else if(ae.getActionCommand().equals("Select All"))
		{
			text.selectAll();
			stats.setText("Select all.");
		}
		else if(ae.getActionCommand().equals("Time/Date"))
		{
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("hh:mm aa MM/dd/yyyy");
			text.setText(text.getText()+dateFormat.format(date));
		}
		else if(ae.getActionCommand().equals("Word Wrap"))
		{
			wrap = !wrap;
			text.setLineWrap(wrap);
			
			if(wrap)
				sBar.setEnabled(true);
			else
			{
				sBar.setEnabled(false);
				statusBar.setVisible(false);
			}
		}
		else if(ae.getActionCommand().equals("Status Bar"))
		{
			if(wrap)
			{
				visible = !visible;
				statusBar.setVisible(visible);
			}
			
		}
		else if(ae.getActionCommand().equals("About JNotepad"))
		{
			JOptionPane.showMessageDialog(frame, "(c) Christopher Rabb");
		}
		else if(ae.getActionCommand().equals("up"))
		{
			direction = true;
		}
		else if(ae.getActionCommand().equals("down"))
		{
			direction = false;
		}
		else if(ae.getActionCommand().equals("Find Next "))
		{
			String str = jtf.getText();
			String copy = text.getText();
			if(!direction)
			{
				int index = text.getText().indexOf(str,text.getCaretPosition());
				if(!match)
					text.select(index, index+str.length());
				else
					try {
						if(text.getText(index,str.length()).equals(str))
							text.select(index, index+str.length());
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			else
			{
				int index = copy.lastIndexOf(str,text.getCaretPosition());
				if(!match)
					text.select(index, index+str.length());
			}
		}
		else if(ae.getActionCommand().equals("Match Case"))
		{
			match = !match;
		}
		else if(ae.getActionCommand().equals("Font..."))
		{
			
			text.setFont(jfc.showDialog(frame));
		}
	}
	
	public static void main(String [] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
			    new JNotepad();
			}
		});
	}

}
