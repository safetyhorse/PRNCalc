/*
 * 
 * to do:
 * add on/off buttons
 * automatically use 2nd operand even if not entered
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
	Stack<Float> stack = new Stack<Float>();
	
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
			if(e.getActionCommand() == "ENT")
			{
				stack.push(Float.parseFloat(display.getText()));
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
					float p2 = stack.pop();
					float p1 = stack.pop();
					float p = p1 + p2;
					stack.push(p);
					display.setText("" + p);
				break;
				case "-":
					float m2 = stack.pop();
					float m1 = stack.pop();
					float m = m1 - m2;
					stack.push(m);
					display.setText("" + m);
				break;
				case "*":
					float t2 = stack.pop();
					float t1 = stack.pop();
					float t = t1 * t2;
					stack.push(t);
					display.setText("" + t);
				break;
				case "/":
					float d2 = stack.pop();
					//handle division by zero
					//replace number in stack
					if(d2 != 0)
					{
						float d1 = stack.pop();
						float d = d1 / d2;
						stack.push(d);
						display.setText("" + d);
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
}


