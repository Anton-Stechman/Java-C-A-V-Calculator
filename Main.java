package org.eclipse.wb.swt;
import java.util.*;

import javax.swing.JTextField;

import com.ibm.icu.text.DecimalFormat;

public class Main
{

	public static window mainMenu;
	public static window c_window;
	public static window a_window;
	public static window v_window;
	
	static Integer default_width 	= 400;
	static Integer default_height 	= 600;
	
	static String mainTitle = "Circumference, Area & Volume Calculator";
	static String c_title 	= "Calculate Circumference";
	static String a_title 	= "Calculate Area";
	static String v_title 	= "Claculate Volume";
	
	static LinkedList<window> allWindows;
	
	public static void init() {
		// TODO Auto-generated method stub
		
		//Create Main Menu
		mainMenu = new window(mainTitle, default_width, default_height, true, windowType.menu);
		c_window = new window(c_title,	 default_width, default_height, false, windowType.circumference);
		a_window = new window(a_title,	 default_width, default_height, false, windowType.area);
		v_window = new window(v_title,	 default_width, default_height, false, windowType.volume);
		
		allWindows = new LinkedList<window>();
		allWindows.add(mainMenu);
		allWindows.add(c_window);
		allWindows.add(a_window);
		allWindows.add(v_window);
	}
	
	public void buttonClick(windowType t, boolean isExit) 
	{
		if (!isExit) 
		{
			for (window w : allWindows) 
			{
				if(w.w_type == t) 
				{
					w.isActiveWindow = true;
				}
				else 
				{
					w.isActiveWindow = false;
				}
				
				w.setVisibility();
			}
		}
		else 
		{
			System.exit(0);
		}
	}
	
	public void calcButtonClick() 
	{
		window curWindow = null;
		
		//Get the Active window
		for (window w : allWindows) 
		{
			if (w.isActiveWindow) 
			{
				curWindow = w;
			}
		}
		
		
		LinkedList<JTextField> tFields = new LinkedList<JTextField>(); 
		DecimalFormat df = new DecimalFormat("####0.00");
		
		//perform calculations based on active window
		switch(curWindow.w_type) 
		{

			case circumference:
			{
				tFields.add(curWindow.r_input);
				
				if(checkForInputError(tFields)) 
				{
					double r 	= calc_pi(Double.parseDouble(curWindow.r_input.getText()));
					String s 	= df.format(r);
					
					curWindow.r_output.setText("Circumference = " + s + " UoM");
				}
				else 
				{
					curWindow.r_output.setText("Input Error! \nPlease Try Again...");
				}
				break;
			}
			case area:
			{
				tFields.add(curWindow.w_input);
				tFields.add(curWindow.l_input);
				
				if(checkForInputError(tFields)) 
				{
					double w = Double.parseDouble(curWindow.w_input.getText());
					double l = Double.parseDouble(curWindow.l_input.getText());
					double r = calc_area(w,l);

					String s 	= df.format(r);
					
					curWindow.r_output.setText( "Area = " + s + " UoM\u00B2");
				}
				else 
				{
					curWindow.r_output.setText("Input Error! \nPlease Try Again...");
				}
				break;
			}
			case volume:
			{
				tFields.add(curWindow.w_input);
				tFields.add(curWindow.l_input);
				tFields.add(curWindow.h_input);
				
				if(checkForInputError(tFields)) 
				{
					double w = Double.parseDouble(curWindow.w_input.getText());
					double l = Double.parseDouble(curWindow.l_input.getText());
					double h = Double.parseDouble(curWindow.h_input.getText());
					double r = calc_vol(w,l,h);
					String s = df.format(r);
					
					curWindow.r_output.setText("Volume = " + s + " UoM\u00B3");
				}
				else 
				{
					curWindow.r_output.setText("Input Error! \nPlease Try Again...");
				}
				break;
			}
			default:
			{
				break;
			}
		}
	}

	private boolean checkForInputError(LinkedList<JTextField> tFields) {
		
		boolean result = false;
		
		for (int i = 0; i < tFields.size(); i++) 
		{
			
			try 
			{
				Double.parseDouble(tFields.get(i).getText());
				result = true;
			}
			catch (Exception e) 
			{
				return false;
				
			}
		}
		return result;
	}
	
	//Calculations Back End
	static final double pi = Math.PI;
	
	public static double calc_pi(double r) 
	{
		double d = r * 2;
		return  d * pi;
	}
	public static double calc_area(double l, double w) 
	{
		return l * w;
	}
	public static double calc_vol(double l, double w, double h) 
	{
		return l * w * h;
	}
	

}
