/*
 * 
 * to do:
 * modify layout
 * 
 */
package rpncalc;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.CompoundBorder;

public class RPNCalcPanel extends JPanel
{
	private JLabel display;
	private JPanel buttonPanel;
	boolean start = true;
	Stack<Double> stack = new Stack<Double>();
	boolean displayOn = false;
	Color pacManYellow = Color.DARK_GRAY;
	Color pacManPink = Color.black;
	
	public RPNCalcPanel()
	{
		setLayout(new BorderLayout());
		display = new JLabel("0");

		try
		{
			File f = new File("big_noodle_titling_oblique.ttf");
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,
					in);
			Font dynamicFont32Pt = dynamicFont.deriveFont(56f);
			display.setFont(dynamicFont32Pt);

		}
		catch(Exception e)
		{
			System.out.println("Loading Font Didn't Work");
		}
		//puts displayed zero to right side
		display.setHorizontalAlignment(SwingConstants.RIGHT);

		add(display, BorderLayout.NORTH);



		ActionListener insert = new InsertAction();
		ActionListener command = new CommandAction();
		
		buttonPanel = new JPanel(new GridLayout(4,5));

		//Color pacManYellow = new Color(255,246,17);
		//Color pacManPink = new Color(255,72,190);

		display.setBackground(Color.white);
		display.setOpaque(true);
		display.setForeground(Color.white);
		//display.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		display.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(Color.white, 2, true),
				BorderFactory.createLineBorder(Color.black, 2, true)));

		//addButton("ENT", command,pacManYellow);
		addButton("ON", insert,pacManYellow);
		addButton("7", insert,pacManPink);
		addButton("8", insert,pacManPink);
		addButton("9", insert,pacManPink);
		addButton("/", command,pacManYellow);

		addButton("OFF", insert,pacManYellow);
		addButton("4", insert,pacManPink);
		addButton("5", insert,pacManPink);
		addButton("6", insert,pacManPink);
		addButton("*", command,pacManYellow);

		addButton("RST", insert,pacManYellow);
		addButton("1", insert,pacManPink);
		addButton("2", insert,pacManPink);
		addButton("3", insert,pacManPink);
		addButton("-", command,pacManYellow);

		/*addButton("CLR", insert,pacManYellow);
		addButton("0", insert,pacManPink);
		addButton("ENT", command,pacManYellow);
		addButton("+", command,pacManYellow);*/
		
		addButton("ENT", command,pacManYellow);
		addButton("CLR", insert,pacManYellow);
		addButton("0", insert,pacManPink);
		addButton(".", insert,pacManPink);
		addButton("+", command,pacManYellow);
		
		
		
		
		
		
		

		buttonPanel.setBackground(Color.white);
		add(buttonPanel, BorderLayout.CENTER);

	}



	private void addButton(String label, ActionListener listener,
			Color fg)
	{
		JButton button = new JButton(label);
		button.addActionListener(listener);
		try
		{
			File f = new File("big_noodle_titling_oblique.ttf");
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,
					in);
			Font dynamicFont44Pt = dynamicFont.deriveFont(44f);
			button.setFont(dynamicFont44Pt);
		}
		catch(Exception e)
		{
			System.out.println("Loading Font Didn't Work");
		}
		button.setBackground(Color.white);
		button.setForeground(fg);
		//button.setBorder(BorderFactory.createLineBorder(Color.red, 1, true));
		button.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(Color.white, 2, true),
				BorderFactory.createLineBorder(Color.black, 2, true)));
		buttonPanel.add(button);
	}


	public Dimension getPreferredSize()
	{
		return new Dimension(700,350);

	}

	private class InsertAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//playClip();
			
			if(start)
			{
				display.setText("");
				start = false;
			}
			//System.out.println(e.getActionCommand());
			if(e.getActionCommand() == "ON")
			{
				//System.out.println(displayOn);
				display.setForeground(pacManPink);
				display.setText("0");
				stackReset();
				start = true;
				displayOn = true;
				playClip();
			}
			else if(e.getActionCommand() == "OFF")
			{
				display.setForeground(Color.white);
				stackReset();
				start = true;
				displayOn = false;
			}
			else if(e.getActionCommand() == "CLR")
			{
				display.setText("0");
				start = true;
			}
			else if(e.getActionCommand() == "RST")
			{
				/*if(stack.size() == 1)
				{
					stack.pop();
				}
				else if(stack.size() > 1)
				{
					for(int i=0; i <= stack.size()+1; i++)
					{
						stack.pop();
					}
				}
				
				display.setText("0");
				printStack();
				start = true;*/
				stackReset();
			}
			else
			{
				if(displayOn)
				{
					playClip();
				}
				display.setText(display.getText() + e.getActionCommand());
			}
			
		}
	}

	private class CommandAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(displayOn)
			{
				playClip();
			}
			
			// if number input not entered, 
			// but command selected
			// add display number to the stack
			// must be in command state (not input state)
			if(e.getActionCommand() != "ENT" && 
					start == false &&
					(display.getText() != "0" || 
					display.getText() != "Invalid"))
			{
				stack.push(Double.parseDouble(display.getText()));
			}/**/
			
			if(e.getActionCommand() == "ENT")
			{
				stack.push(Double.parseDouble(display.getText()));
			}
			/*else if(e.getActionCommand() == "CLR")
			{
				display.setText("");
			}*/
			else if(stack.size()>=2)
			{
				switch(e.getActionCommand())
				{
				case "+":
					double p2 = stack.pop();
					double p1 = stack.pop();
					double p = p1 + p2;
					stack.push(p);
					//System.out.println(removeTrailingZeros(p));
					display.setText("" + removeTrailingZeros(p));
				break;
				case "-":
					double m2 = stack.pop();
					double m1 = stack.pop();
					double m = m1 - m2;
					stack.push(m);
					display.setText("" + removeTrailingZeros(m));
				break;
				case "*":
					double t2 = stack.pop();
					double t1 = stack.pop();
					double t = t1 * t2;
					stack.push(t);
					display.setText("" + removeTrailingZeros(t));
				break;
				case "/":
					double d2 = stack.pop();
					//handle division by zero
					//replace number in stack
					if(d2 != 0)
					{
						double d1 = stack.pop();
						double d = d1 / d2;
						stack.push(d);
						display.setText("" + removeTrailingZeros(d));
					}
					else
					{
						stack.push(d2);
						display.setText("Invalid");
					}
					
				break;
				}
			}
			else
			{
				display.setText("Invalid");
			}
			
			
			/*else if(e.getActionCommand()=="+")
			{
				if(stack.size()>=2)
				{
					//get 2nd operand
					int op2 = stack.pop();
					//get 1st operand
					int op1 = stack.pop();
					//apply the operator
					int op = op1 + op2;
					//push the result
					stack.push(op);
					//display the result
					display.setText("" + op);
				}
				else
				{
					display.setText("Invalid");
				}
				
			}*/
			start = true;
			//System.out.println("Command");
			printStack();
		}
	}
	
	public void printStack()
	{
		System.out.println("Stack:");
		//System.out.println(stack.elementAt(stack.size()-1) + " ");
		//System.out.println(stack.size());
		for(int i=stack.size()-1; i >= 0; i--)
		{
			System.out.println(stack.elementAt(i) + " ");
		}
		System.out.println("");
	}
	
	//clean display - don't show unnecessary decimals
	// stackoverflow.com/questions/703396/how-to-nicely-format-floating-numbers-to-string-without-unnecessary-decimal-0
	public static String removeTrailingZeros(double f)
	{
	    if(f == (int)f) {
	        return String.format("%d", (int)f);
	    }
	    return String.format("%f", f).replaceAll("0*$", "");
	}
	
	// reset stack
	public void stackReset()
	{
		if(stack.size() == 1)
		{
			stack.pop();
		}
		else if(stack.size() > 1)
		{
			for(int i=0; i <= stack.size()+1; i++)
			{
				stack.pop();
			}
		}
		
		display.setText("0");
		printStack();
		start = true;
	}	
	
	// play an audio clip
	// adapted from http://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
	// audio from http://vozme.com/index.php?lang=en
	// converted to wav files using audacity
	public void playClip()
	{
		int random = randInt(1,7);
		try 
		{
			String soundName = random + ".wav";    
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch (Exception e) 
		{
			System.err.println(e.getMessage());
		}
	}
	
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

}


