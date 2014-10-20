package rpncalc;

import java.awt.*;

import javax.swing.JFrame;


public class RPNCalc
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				RPNCalcFrame frame = new RPNCalcFrame();
				frame.setTitle("Motivational RPN Calculator");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
	}
}