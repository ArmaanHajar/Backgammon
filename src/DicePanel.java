import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DicePanel extends JPanel{
	
	// creating variables for dice images
	Image diceImage1;
	Image diceImage2;
	Image diceImage3;
	Image diceImage4;
	Image diceImage5;
	Image diceImage6;
	static Image randDiceImage1;
	static Image randDiceImage2;

	// creating array lists for tokens and selectors
	static ArrayList<Image> diceArray = new ArrayList<Image>();

	// size: 800 x 650
	// board size: 1400 x 1000
	// offset: 350 x 150
	// bar: 750 x 475
	
	public DicePanel()
	{
		super();
		try
		{
			// reading in all images
			diceImage1 = ImageIO.read(new File("one.png"));
			diceImage2 = ImageIO.read(new File("two.png"));
			diceImage3 = ImageIO.read(new File("three.png"));
			diceImage4 = ImageIO.read(new File("four.png"));
			diceImage5 = ImageIO.read(new File("five.png"));
			diceImage6 = ImageIO.read(new File("six.png"));
			randDiceImage1 = ImageIO.read(new File("one.png"));
			randDiceImage2 = ImageIO.read(new File("one.png"));
			// adding dice images to dice array list
			diceArray.add(diceImage1);
			diceArray.add(diceImage2);
			diceArray.add(diceImage3);
			diceArray.add(diceImage4);
			diceArray.add(diceImage5);
			diceArray.add(diceImage6);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(randDiceImage1, 065, 100, null);
		g.drawImage(randDiceImage2, 165, 100, null);
	}
}
