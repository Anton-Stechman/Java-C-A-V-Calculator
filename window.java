package org.eclipse.wb.swt;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import javax.swing.*;

public class window 
{
	//Frame
	private JFrame frame;
	private Integer f_width;
	private Integer f_height;
	
	//GUI Elements
	private LinkedList<button> buttons;
	private LinkedList<JTextField>textboxes;
	
	//Other
	private String f_Title;
	public windowType w_type;
	
	public boolean isActiveWindow;
	
	public JTextField r_input; //Radius Input
	public JTextField w_input; //Width Input
	public JTextField l_input; //Length Input
	public JTextField h_input; //Height Input
	public JTextField r_output; //Result Output

	//New Window Constructor
	public window(String fTitle, Integer fWidth, Integer fHeight, boolean active, windowType type) 
	{
		//Setup Frame
		this.frame 			= new JFrame();
		this.f_Title 		= fTitle; //frame title
		this.f_width 		= fWidth; //frame width
		this.f_height 		= fHeight; //frame height
		this.isActiveWindow = active;
		this.w_type 		= type;
		
		this.buttons 		= new LinkedList<button>();
		
		//Add Buttons To List
		build_window();
	}
	

	private void build_window() 
	{
		if (w_type != windowType.menu) 
		{
			buildElements();
		}
		
		build_buttons();
		setVisibility();
		
		frame.setSize(f_width, f_height);
		frame.setTitle(f_Title);
		frame.setResizable(false);
		frame.setLocation(getCenter(0));
		frame.setLayout(new GridLayout(getElementAmount(), 1));
		
		for(button b : buttons) 
		{
			frame.add(b.thisButton);
		}
	
		
		if (w_type == windowType.menu) 
		{
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
	}
	
	public void setVisibility() 
	{
		frame.setVisible(isActiveWindow);
	}
	
	private Point getCenter(int padding) 
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 2;
		int y = ((dim.height - h) / 2) + padding;
		
		return new Point(x,y);
	}
	
	private Integer getElementAmount() 
	{
		switch(w_type) 
		{
			case menu:
			{
				return buttons.size();
			}
			default:
			{
				return buttons.size() + textboxes.size();
			}
		}
	}
	
	private void build_buttons() 
	{
		switch(w_type) 
		{
			case menu:
			{
				buttons.add(new button(buttonType.c_btn));
				buttons.add(new button(buttonType.a_btn));		
				buttons.add(new button(buttonType.v_btn));
				buttons.add(new button(buttonType.exit));
				//System.out.println("Working: " + w_type);
				break;
			}
			
			default:
			{
				buttons.add(new button(buttonType.calc));
				buttons.add(new button(buttonType.back));
				//System.out.println("Working: " + w_type);
				break;
			}
		}
	}	
	
	private void buildElements() 
	{
		//Create List for Text Boxes
		textboxes = new LinkedList<JTextField>();
		
		//Create Text Areas
		r_input 	= new JTextField("Input Radius...");
		w_input 	= new JTextField("Input Width...");
		l_input 	= new JTextField("Input Length...");
		h_input 	= new JTextField("Input Height...");
		r_output	= new JTextField("Result Shown Here...");
		
		//Configure JTextFields
		r_output.setEditable(false);
		r_output.setBackground(Color.black);
		r_output.setForeground(Color.gray);
		r_output.setHorizontalAlignment(JTextField.CENTER);
		r_output.setFont(new Font("Arial", Font.PLAIN, 25));
		
		setPlaceholders();
		
		switch (w_type) 
		{
			case circumference:
			{
				textboxes.add(r_output);
				textboxes.add(r_input);
				break;
			}
			case area:
			{
				textboxes.add(r_output);
				textboxes.add(w_input);
				textboxes.add(l_input);
				break;
			}
			case volume:
			{
				textboxes.add(r_output);
				textboxes.add(w_input);
				textboxes.add(l_input);
				textboxes.add(h_input);
				break;
			}
			default:
			{
				textboxes = null;
				break;
			}
		}
		
		if (textboxes != null) 
		{
			for (JTextField t : textboxes) 
			{
				frame.add(t);
			}
		}
		
	}
	
	private void setPlaceholders() 
	{
		JTextField[] fields = new JTextField[]
		{
			r_input,
			w_input,
			l_input,
			h_input
		};
		String[] placeholders = new String[] 
		{
				"Input Radius...",
				"Input Width...",
				"Input Length...",
				"Input Height..."
		};
		
		
		//Configure Text Areas	
		for (int i = 0; i < fields.length; i++) 
		{
			JTextField f 	= fields[i];
			String p 		= placeholders[i];
			
			FocusListener focus = new FocusListener() 
			{
				@Override
				public void focusGained(FocusEvent e) 
				{
					if (f.getText() != "") 
					{
						f.setForeground(Color.white);
						f.setText(null);
					}
					
				}

				@Override
				public void focusLost(FocusEvent e) {
			
					try 
					{
						Double.parseDouble(f.getText());
					}
					catch (Exception c) 
					{
						f.setForeground(Color.gray);
						f.setText(p);
					}
				}
				
			};
			f.setFont(new Font("Arial", Font.PLAIN, 25));
			f.setHorizontalAlignment(JTextField.CENTER);
			f.setForeground(Color.gray);
			f.setBackground(Color.black);
			f.addFocusListener(focus);
		}
	}

}
