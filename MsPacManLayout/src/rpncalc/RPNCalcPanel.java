/*
 * 
 * to do:
 * add on/off buttons
 * try gridbag for layout
 * 
 */
package rpncalc;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

import javax.swing.*;

public class RPNCalcPanel extends JPanel
{
	private JLabel display;
	private JPanel buttonPanel;
	boolean start = true;
	Stack<Double> stack = new Stack<Double>();
	
	public RPNCalcPanel()
	{
		setLayout(new BorderLayout());
		display = new JLabel("0");

		try
		{
			File f = new File("ARCADE_R.ttf");
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
		
		buttonPanel = new JPanel(new GridLayout(5,4));

		Color pacManYellow = new Color(255,246,17);
		Color pacManPink = new Color(255,72,190);

		display.setBackground(Color.black);
		display.setOpaque(true);
		display.setForeground(pacManPink);
		display.setBorder(BorderFactory.createLineBorder(new
				Color(16,67,234), 3, true));

		//addButton("ENT", command,pacManYellow);
		addButton("7", insert,pacManPink);
		addButton("8", insert,pacManPink);
		addButton("9", insert,pacManPink);
		addButton("/", command,pacManYellow);

		addButton("4", insert,pacManPink);
		addButton("5", insert,pacManPink);
		addButton("6", insert,pacManPink);
		addButton("*", command,pacManYellow);

		addButton("1", insert,pacManPink);
		addButton("2", insert,pacManPink);
		addButton("3", insert,pacManPink);
		addButton("-", command,pacManYellow);

		/*addButton("CLR", insert,pacManYellow);
		addButton("0", insert,pacManPink);
		addButton("ENT", command,pacManYellow);
		addButton("+", command,pacManYellow);*/
		addButton("0", insert,pacManPink);
		addButton("ENT", command,pacManYellow);
		addButton("CLR", insert,pacManYellow);
		addButton("+", command,pacManYellow);
		
		
		addButton(".", insert,pacManPink);
		addButton("ON", command,pacManYellow);
		addButton("RST", insert,pacManYellow);
		addButton("", insert,pacManPink);
		

		buttonPanel.setBackground(Color.black);
		add(buttonPanel, BorderLayout.CENTER);

	}



	private void addButton(String label, ActionListener listener,
			Color fg)
	{
		JButton button = new JButton(label);
		button.addActionListener(listener);
		try
		{
			File f = new File("ARCADE_R.ttf");
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
		button.setBackground(Color.BLACK);
		button.setForeground(fg);
		button.setBorder(BorderFactory.createLineBorder(new
				Color(16,67,234), 3, true));
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
			if(start)
			{
				display.setText("");
				start = false;
			}
			//System.out.println(e.getActionCommand());
			if(e.getActionCommand() == "CLR")
			{
				display.setText("0");
				start = true;
			}
			else if(e.getActionCommand() == "RST")
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
			else
			{
				display.setText(display.getText() + e.getActionCommand());
			}
			
		}
	}

	private class CommandAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
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
}


