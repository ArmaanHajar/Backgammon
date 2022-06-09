import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Backgammon implements ActionListener, MouseListener {
	
	JFrame frame = new JFrame("Backgammon");
	GammonPanel gammonPanel = new GammonPanel();
	DicePanel dicePanel = new DicePanel();
	JTextField p1nameTF = new JTextField("Player 1");
	JTextField p2nameTF = new JTextField("Player 2");
	JTextField p1scoreTF = new JTextField("Score: 0");
	JTextField p2scoreTF = new JTextField("Score: 0");
	JTextField playerMove = new JTextField("Gray Move");
	JButton roll = new JButton("Roll");
	JPanel interactiveArea = new JPanel();
	JPanel westGrid = new JPanel();
	Container north = new Container();
	Container south = new Container();
	
	String p1 = "Player 1";
	String p2 = "Player 2";
	
	int p1score = 0;
	int p2score = 0;
	int diceRand1 = 0;
	int diceRand2 = 0;
	final int P1TURN = 0;
	final int P2TURN = 1;
	final int ROLL_STATE = 2;
	final int MOVE_STATE = 3;
	final int p1A = 4;
	final int p2A = 5;
	final int TOKEN_STATE = 6;
	final int SELECT_STATE = 7;
	int moveState = TOKEN_STATE;
	int tokenIndex = 0;
	
	public Backgammon()
	{
		frame.setSize(1400,1000);
		frame.setLayout(new BorderLayout());
		frame.add(gammonPanel, BorderLayout.CENTER);
		
		westGrid.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.ipadx = 2;
		c.ipady = 2;
		
		c.fill = GridBagConstraints.BOTH; //natural height, maximum width	
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		westGrid.add(dicePanel, c);
		dicePanel.setPreferredSize(new Dimension(300,300));
		interactiveArea.setPreferredSize(new Dimension(300,300));
		roll.addActionListener(this);
		
		dicePanel.setBackground(Color.PINK);
		interactiveArea.setBackground(Color.BLUE);
		frame.setBackground(Color.CYAN);
		
		interactiveArea.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		interactiveArea.add(roll, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		interactiveArea.add(p1nameTF, c);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		interactiveArea.add(p1scoreTF, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		interactiveArea.add(p2nameTF, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		interactiveArea.add(p2scoreTF, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 1;
		interactiveArea.add(playerMove, c);
		
		playerMove.setEditable(false);
		p1scoreTF.setEditable(false);
		p2scoreTF.setEditable(false);

		c.fill = GridBagConstraints.BOTH; //natural height, maximum width	
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		westGrid.add(interactiveArea, c);
		frame.add(westGrid, BorderLayout.WEST);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// Adding all the selectors
				// for x axis: +50 for the initial setting of the tokens/selectors, +60 for any additional move
				// for y axis bottom: +45 for initial setting of the tokens, +50 for any addition changes
				// for y axis top: -42 for initial setting of the tokens, +50 for any addition changes
				for (int i = 5; i > -1; i--)
				{
					// bottom right
					gammonPanel.selectorList.add(new Selector (gammonPanel.centerX - (gammonPanel.selectorSize/2) + ((i * 60) + 50), gammonPanel.offsetY + gammonPanel.gammonH - (gammonPanel.selectorSize/2) + 35, false, 6 - i));
				}
				for (int i = 0; i < 6; i++)
				{
					// bottom left
					gammonPanel.selectorList.add(new Selector (gammonPanel.centerX - (gammonPanel.selectorSize/2) - ((i * 60) + 50), gammonPanel.offsetY + gammonPanel.gammonH - (gammonPanel.selectorSize/2) + 35, false, i + 7));
				}
				for (int i = 5; i > -1; i--)
				{
					// top left
					gammonPanel.selectorList.add(new Selector (gammonPanel.centerX - (gammonPanel.selectorSize/2) - ((i * 60) + 50), gammonPanel.offsetY - (gammonPanel.selectorSize/2) - 35, false, 6 - i + 12));
				}
				for (int i = 0; i < 6; i++)
				{
					// top right
					gammonPanel.selectorList.add(new Selector (gammonPanel.centerX - (gammonPanel.selectorSize/2) + ((i * 60) + 50), gammonPanel.offsetY - (gammonPanel.selectorSize/2) - 35, false, i + 19));
				}
				
				
				
				// Adding all the tokens
				// Player 1:
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p1A, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 145, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 195, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 245, p1A, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 142, p1A, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 142, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 192, p1A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 242, p1A, false));

				// Player 2:
				
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p2A, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 142, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 192, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 242, p2A, false));
			
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 145, p2A, false));
			
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 145, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 195, p2A, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 245, p2A, false));
				
				


				// For debugging:
				/*
				System.out.println("Selectors:");
				for (int i = 0; i < panel.selectorList.size(); i++)
				{
					System.out.print("Name: " + Integer.toString(panel.selectorList.get(i).getName()));
					System.out.print(" X: " + Integer.toString(panel.selectorList.get(i).getX()));
					System.out.print(" Y: " + Integer.toString(panel.selectorList.get(i).getY()));
					System.out.println("");
				}
				System.out.println("");

				System.out.println("Tokens:");
				for (int i = 0; i < panel.tokenList.size(); i++)
				{
					System.out.print("Affiliation: " + Integer.toString(panel.tokenList.get(i).getAffiliation()));
					System.out.print(" X: " + Integer.toString(panel.tokenList.get(i).getX()));
					System.out.print(" Y: " + Integer.toString(panel.tokenList.get(i).getY()));
					System.out.println("");

				}
				*/
	}

	public static void main(String[] args) {
		new Backgammon();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// when roll is clicked, randomly select a dice image from the dice array list
		diceRand1 = (int)(Math.random() * dicePanel.diceArray.size());
		diceRand2 = (int)(Math.random() * dicePanel.diceArray.size());
		
		DicePanel.randDiceImage1 = dicePanel.diceArray.get(diceRand1);
		DicePanel.randDiceImage2 = dicePanel.diceArray.get(diceRand2);
		frame.repaint();		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (moveState == TOKEN_STATE)
		{
			Token t = gammonPanel.getToken(e.getX(), e.getY());
			if (t != null)
			{
				for (int i = 0; i < gammonPanel.tokenList.size(); i++)
				{
					if (gammonPanel.tokenList.get(i).equals(t))
					{
						tokenIndex = i;
					}
				}
				moveState = SELECT_STATE;
			}
		}
		else if (moveState == SELECT_STATE)
		{
			Selector s = gammonPanel.getSelector(e.getX(), e.getY());
			Token tii = gammonPanel.tokenList.get(tokenIndex);
			if (s != null)
			{
				// putting the token in the correct place
				// ADD CHECKS FOR MAKING SURE THE MOVE IS LEGAL
				// MAY TURN THIS PART OF THE CODE INTO A METHOD
				int ogx = tii.getX();
				int ogy = tii.getY();
				tii.setX(s.getX());
				tii.setY(s.getY());
				if (s.getName() > 12)
				{
					tii.setY(tii.getY() + 35 + 42);
				}
				else
				{
					tii.setY(tii.getY() - 35 - 45);
				}
				for (int a = 0; a < 6; a++)
				{
					for (int i = 0; i < gammonPanel.tokenList.size(); i++)
					{
						if (tii.getX() == gammonPanel.tokenList.get(i).getX())
						{
							if (tii.getY() == gammonPanel.tokenList.get(i).getY())
							{
								if (tii != gammonPanel.tokenList.get(i))
								{
									if (s.getName() > 12)
									{
										tii.setY(tii.getY() + 50);
									}
									else
									{
										tii.setY(tii.getY() - 50);
									}
								}
							}
						}
					}
				}
				if (s.getName() > 12 && tii.getY() > gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 292)
				{
					tii.setY(ogy);
					tii.setX(ogx);
				}
				else if (s.getName() < 13 && tii.getY() < gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 295)
				{
					tii.setY(ogy);
					tii.setX(ogx);
				}
				moveState = TOKEN_STATE;
				frame.repaint();
			}
		}
		// check that its the time to move the tokens
		// check that the token clicked was the player's token (depending on who's turn is active)
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
