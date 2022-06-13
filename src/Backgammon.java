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
import javax.swing.SwingConstants;

public class Backgammon implements ActionListener, MouseListener {
	
	JFrame frame = new JFrame("Backgammon");
	GammonPanel gammonPanel = new GammonPanel();
	DicePanel dicePanel = new DicePanel();
	JTextField p1nameTF = new JTextField("Player 1");
	JTextField p2nameTF = new JTextField("Player 2");
	JTextField p1scoreTF = new JTextField("Score: 0");
	JTextField p2scoreTF = new JTextField("Score: 0");
	JTextField playerMoveTF = new JTextField("Gray Move");
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
	int MOVE_COUNT = 0;
	boolean P1TURN = true;
	boolean P2TURN = false;
	final int ROLL_STATE = 2;
	final int MOVE_STATE = 3;
	final int p1A = 4;
	final int p2A = 5;
	final int TOKEN_STATE = 6;
	final int SELECT_STATE = 7;
	int moveState = TOKEN_STATE;
	int tokenIndex = 0;
	int singleMoveSelectorOne;
	int singleMoveSelectorTwo;
	int doubleMoveSelector;
	
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
		interactiveArea.add(playerMoveTF, c);
		
		playerMoveTF.setHorizontalAlignment(SwingConstants.CENTER);

		playerMoveTF.setEditable(false);
		p1scoreTF.setEditable(false);
		p2scoreTF.setEditable(false);

		c.fill = GridBagConstraints.BOTH; //natural height, maximum width	
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		westGrid.add(interactiveArea, c);
		frame.add(westGrid, BorderLayout.WEST);
		
		gammonPanel.addMouseListener(this);
	
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
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p1A, false, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 145, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 195, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 245, p1A, false, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 142, p1A, false, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 142, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 192, p1A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 242, p1A, false, false));

				// Player 2:
				
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p2A, false, false));

				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 92, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 142, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 192, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 5*60), gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 242, p2A, false, false));
			
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) - (50 + 1*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 145, p2A, false, false));
			
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 95, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 145, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 195, p2A, false, false));
				gammonPanel.tokenList.add(new Token(gammonPanel.centerX - (gammonPanel.tokenSize/2) + (50 + 0*60), gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 245, p2A, false, false));
				
				


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
		diceRand1 = (int)(Math.random() * DicePanel.diceArray.size());
		diceRand2 = (int)(Math.random() * DicePanel.diceArray.size());
		
		DicePanel.randDiceImage1 = DicePanel.diceArray.get(diceRand1);
		DicePanel.randDiceImage2 = DicePanel.diceArray.get(diceRand2);
		frame.repaint();		
	}
	
	public void tokenMoves(MouseEvent e) {
		Token tii = gammonPanel.tokenList.get(tokenIndex);		
		Selector s = gammonPanel.getSelector(e.getX(), e.getY());
		
		if (s != null && MOVE_COUNT < 2)
		{
			// putting the token in the correct place
			// ADD CHECKS FOR MAKING SURE THE MOVE IS LEGAL
			// MAY TURN THIS PART OF THE CODE INTO A METHOD
			
			// stores the original x and y values in case it needs to go back
			int ogx = tii.getX();
			int ogy = tii.getY();
			
			// puts the token to the selected selector
			tii.setX(s.getX());
			tii.setY(s.getY());
			MOVE_COUNT++;
			System.err.println(MOVE_COUNT);
			
			if (s.getName() == singleMoveSelectorOne) {
				MOVE_COUNT++;
			}
			else if (s.getName() == singleMoveSelectorTwo) {
				MOVE_COUNT++;
			}
			else if (s.getName() == doubleMoveSelector) {
				MOVE_COUNT = 2;
			}
			
			if (s.getName() > 12)
			{
				// if the selector is on the top portion, the token adjusts accordingly
				tii.setY(tii.getY() + 35 + 42);
			}
			else
			{
				// if the selector is on the bottom portion, the token adjusts accordingly
				tii.setY(tii.getY() - 35 - 45);
			}
			
			// puts the token in the appropiate place on the triangle
			// repeats 6 times to make sure there's no overlap
			for (int a = 0; a < 6; a++)
			{
				// for every existing token...
				for (int i = 0; i < gammonPanel.tokenList.size(); i++)
				{
					// if the selected token has the same x as the current token...
					// and the selected token has the same y as the current token...
					// and the selected token isn't the same as the current token...
					if (tii.getX() == gammonPanel.tokenList.get(i).getX() && 
						tii.getY() == gammonPanel.tokenList.get(i).getY() && 
						tii != gammonPanel.tokenList.get(i))
					{
						// and its on the top part of the board...
						if (s.getName() > 12)
						{
							// then move the token down
							tii.setY(tii.getY() + 50);
						}
						// and its on the bottom part of the board...
						else
						{
							// then more the token up
							tii.setY(tii.getY() - 50);
						}
					}
				}
			}
			
			// if the token's at the top part of the board and it's y value is too high (as in the token is too low beyond the limit of 6)...
			if (s.getName() > 12 && tii.getY() > gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 292)
			{
				// put the token back to it's original position
				tii.setY(ogy);
				tii.setX(ogx);
			}
			// if the token's at the bottom part of the board and it's y value is too low (as in the token is too high beyond the limit of 6)...
			else if (s.getName() < 13 && tii.getY() < gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 295)
			{
				// put the token back to it's original position
				tii.setY(ogy);
				tii.setX(ogx);
			}
			
			// this code makes sure that there are no gaps between tokens in one column
			// repeats six times to make sure everything is caught
			for (int m = 0; m < 6; m++)
			{
				// for all the tokens...
				for (int i = 0; i < gammonPanel.tokenList.size(); i++)
				{
					Token token = gammonPanel.tokenList.get(i);
					// if the token is on the top part of the board...
					if (token.getY() > gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 42 && token.getY() < gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 295)
					{
						// move it up one space
						token.setY(token.getY() - 50);
						for (int a = 0; a < gammonPanel.tokenList.size(); a++)
						{
							Token token2 = gammonPanel.tokenList.get(a);
							// if it occupies the same space as any other token...
							if (token.getX() == token2.getX() && 
								token.getY() == token2.getY() && 
								token != token2)
							{
								// go back to original position
								token.setY(token.getY() + 50);
							}
						}
					}
					// if the token is on the bottom part of the board...
					else if (token.getY() < gammonPanel.gammonH + gammonPanel.offsetY - (gammonPanel.tokenSize/2) - 45 && token.getY() > gammonPanel.offsetY - (gammonPanel.tokenSize/2) + 292)
					{
						// move it up down space
						token.setY(token.getY() + 50);
						for (int a = 0; a < gammonPanel.tokenList.size(); a++)
						{
							Token token2 = gammonPanel.tokenList.get(a);
							// if it occupies the same space as any other token...
							if (token.getX() == token2.getX() && 
								token.getY() == token2.getY() && 
								token != token2)
							{
								// go back to original position
								token.setY(token.getY() - 50);
							}
						}
					}
				}
			}
			
			// sets all the selectors back to being unselctable
			for (int i = 0; i < gammonPanel.selectorList.size(); i++)
			{
				gammonPanel.selectorList.get(i).setSelectable(false);
			}
			
			// unhighlights the token
			tii.setSelected(false);
			
			frame.repaint();
			moveState = TOKEN_STATE;
			
		}
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
				// this loop stores the index of the selected token for future use
				for (int i = 0; i < gammonPanel.tokenList.size(); i++)
				{
					if (gammonPanel.tokenList.get(i).equals(t))
					{
						tokenIndex = i;
					}
				}
				
				int startingSelector = 0;
				// code for making sure only valid selectors are selectable
				// for all the selectors...
				for (int i = 0; i < gammonPanel.selectorList.size(); i++)
				{
					// if the x value of the selected token and this selector is the same...
					if (t.getX() == gammonPanel.selectorList.get(i).getX())
					{
						// and the y values aren't further than 6 tokens apart...
						if (Math.abs(t.getY() - gammonPanel.selectorList.get(i).getY()) < 335)
						{
							// that is the selector associated with this token
							startingSelector = i;
						}
					}
				}
				
				// code that makes only the appropriate selectors are selectable
				// based on the dice roll and player affiliation (clockwise/gray moves)
				if (t.getAffiliation() == p1A)
				{
					if (startingSelector + diceRand1 + 1 < 24)
					{
						// makes the first dice roll value selector selectable
						gammonPanel.selectorList.get(startingSelector + diceRand1 + 1).setSelectable(true);
						singleMoveSelectorOne = gammonPanel.selectorList.get(startingSelector + diceRand1 + 1).getName();
					}
					if (startingSelector + diceRand2 + 1 < 24)
					{
						// makes the second dice roll value selector selectable
						gammonPanel.selectorList.get(startingSelector + diceRand2 + 1).setSelectable(true);
						singleMoveSelectorTwo = gammonPanel.selectorList.get(startingSelector + diceRand2 + 1).getName();
					}
					if (startingSelector + diceRand1 + 1 + diceRand2 + 1 < 24)
					{
						// makes both dice roll values combined selector selectable
						gammonPanel.selectorList.get(startingSelector + diceRand1 + 1 + diceRand2 + 1).setSelectable(true);
						doubleMoveSelector = gammonPanel.selectorList.get(startingSelector + diceRand1 + 1 + diceRand2 + 1).getName();
					}
				}
				// (counterclockwise/ red moves)
				else if (t.getAffiliation() == p2A)
				{
					if (startingSelector - (diceRand1 + 1) > -1)
					{
						// makes the first dice roll value selector selectable
						gammonPanel.selectorList.get(startingSelector - (diceRand1 + 1)).setSelectable(true);
						singleMoveSelectorOne = gammonPanel.selectorList.get(startingSelector + diceRand1 + 1).getName();
					}
					if (startingSelector - (diceRand2 + 1) > -1)
					{
						// makes the second dice roll value selector selectable
						gammonPanel.selectorList.get(startingSelector - (diceRand2 + 1)).setSelectable(true);
						singleMoveSelectorTwo = gammonPanel.selectorList.get(startingSelector + diceRand2 + 1).getName();

					}
					if (startingSelector - (diceRand1 + 1 + diceRand2 + 1) > -1)
					{
						// makes both dice roll values combined selector selectable
						gammonPanel.selectorList.get(startingSelector - (diceRand1 + 1 + diceRand2 + 1)).setSelectable(true);
						doubleMoveSelector = gammonPanel.selectorList.get(startingSelector + diceRand1 + 1 + diceRand2 + 1).getName();
					}
				}
					
				frame.repaint();
				moveState = SELECT_STATE;
			}
		}
		else if (moveState == SELECT_STATE)
		{
			if (P1TURN == true) {
				System.out.println("p1 turn");
				tokenMoves(e);
				if (MOVE_COUNT == 2) {
					P1TURN = false;
					P2TURN = true;
				}
			}
			else if (P2TURN == true) {
				System.out.println("p2 turn");
				tokenMoves(e);
				if (MOVE_COUNT == 2) {
					P1TURN = true;
					P2TURN = false;
				}
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
