import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GammonPanel extends JPanel{
	
	// creating variables for board image
	Image board;
	
	// creating board variables
	final int p1A = 4;
	final int p2A = 5;
	int selectorSize = 50;
	int tokenSize = 50;
	int gammonW = 800;
	int gammonH = 650;
	int offsetX = 300;
	int offsetY = 150;
	int centerX = (gammonW/2) + offsetX;
	int centerY = (gammonH/2) + offsetY;
	
	// creating array lists for tokens and selectors
	ArrayList<Token> tokenList = new ArrayList<Token>();
	ArrayList<Selector> selectorList = new ArrayList<Selector>();

	// size: 800 x 650
	// board size: 1400 x 1000
	// offset: 350 x 150
	// bar: 750 x 475
	
	public GammonPanel()
	{
		super();
		try
		{
			// reading in all images
			board = ImageIO.read(new File("gammon4.png"));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public Token getToken(int x, int y)
	{
		for (int i = 0; i < tokenList.size(); i++)
		{
			Token token = tokenList.get(i);
			// a squared + b squared = c squared
			double radius = Math.sqrt(Math.pow(x - (token.getX() + tokenSize/2), 2) + Math.pow(y - (token.getY() + tokenSize/2), 2));
			if (radius < tokenSize/2)
			{
				return tokenList.get(i);
			}
		}
		return null;
				
	}
	
	public Selector getSelector(int x, int y)
	{
		for (int i = 0; i < selectorList.size(); i++)
		{
			Selector selector = selectorList.get(i);
			// a squared + b squared = c squared
			double radius = Math.sqrt(Math.pow(x - (selector.getX() + selectorSize/2), 2) + Math.pow(y - (selector.getY() + selectorSize/2), 2));
			if (radius < selectorSize/2)
			{
				return selectorList.get(i);
			}
		}
		return null;
				
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		//g.drawLine(0, 0, 800, 600);
		// offset:
		g.drawImage(board, offsetX, offsetY, null);
		
		for (int i = 0; i < selectorList.size(); i++)
		{
			g.setColor(Color.CYAN);
			g.fillOval(selectorList.get(i).getX(), selectorList.get(i).getY(), selectorSize, selectorSize);
			g.setColor(Color.BLACK);
			g.drawOval(selectorList.get(i).getX(), selectorList.get(i).getY(), selectorSize, selectorSize);
			g.drawString(Integer.toString(selectorList.get(i).getName()), selectorList.get(i).getX() + selectorSize/2, selectorList.get(i).getY() + selectorSize/2);
		}
		
		for (int i = 0; i < tokenList.size(); i++)
		{
			if (tokenList.get(i).getAffiliation() == p1A)
			{
				g.setColor(Color.GRAY);
			}
			else
			{
				g.setColor(Color.RED);
			}
			g.fillOval(tokenList.get(i).getX(), tokenList.get(i).getY(), tokenSize, tokenSize);
			g.setColor(Color.BLACK);
			g.drawOval(tokenList.get(i).getX(), tokenList.get(i).getY(), tokenSize, tokenSize);
		}


	}
	
}
