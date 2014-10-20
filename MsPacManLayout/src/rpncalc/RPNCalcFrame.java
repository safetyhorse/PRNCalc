package rpncalc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;

public class RPNCalcFrame extends JFrame
{
	public RPNCalcFrame()
	{
		add(new MarqueePanel());
		pack();
	}
}

class MarqueePanel extends JPanel
{
	public MarqueePanel()
	{
		this.setBackground(Color.white);
		// image from
		// http://www.safam.com/press.shtml
		this.add(new JLabel(new ImageIcon("mustache.jpg")));
		this.add(new RPNCalcPanel());

	}
	public Dimension getPreferredSize()
	{
		return new Dimension(800,680);
	}
}