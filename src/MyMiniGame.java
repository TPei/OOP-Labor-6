import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import MiniGamePackage.MiniGame;
import MiniGamePackage.Sprite;

/**
 * game logic
 * @author Thomas
 */
public class MyMiniGame extends MiniGame
{
	/**
	 * size of field (no of sprites etc.)
	 */
    final int FIELD_SIZE = 12;
    
    // 0: not set, 1: computer, 2: player
    int[] spriteStatus = new int[FIELD_SIZE]; 
    
    Sprite[] gameSprites = getSprites(0);
    
    Sprite computerSprite = getSprite(1, 0);
    Sprite playerSprite = getSprite(2, 0);
    
    Sprite[] computerConqueredSprites = getSprites(3);
    Sprite[] playerConqueredSprites = getSprites(4);
    
    int playerPosition = 0;
    int computerPosition = 0;
    
    /**
     * MyMiniGame constructor puts sprites and playerSprites onto the field
     */
    public MyMiniGame()
    {
    	// paint grey background
		getBackgroundPicture().paintRectangle(0, 0, 640, 640, -1, 100, 100, 100);
		
		// create neutral Sprites
		gameSprites[0].paintEllipse(5, 5, 22, 22, -1, 255, 255, 255);
	
		// create sprites that have been taken by player or computer
		computerConqueredSprites[0].paintEllipse(5, 5, 22, 22, -1, 255, 0, 0);
		playerConqueredSprites[0].paintEllipse(5, 5, 22, 22, -1, 0, 255, 0);
	
		// create player and computer sprite
		computerSprite.paintImage("icons/ship.png");
		playerSprite.paintImage("icons/ship.png");

    }
    
    /**
     * initialize game
     */
    @Override
    protected void initGame()
    {
		for (int i = 0; i < FIELD_SIZE; i++)
		{
		    playerConqueredSprites[i].setPosition(304, i * 40 + 80);
		    computerConqueredSprites[i].setPosition(304, i * 40 + 80);
		    
		    gameSprites[i].setPosition(304, i * 40 + 80);
		    
		    // make player and computer conquered sprites invisible
		    playerConqueredSprites[i].dontShow();
		    computerConqueredSprites[i].dontShow();
	
		    spriteStatus[i] = 0;
		}
	
		playerPosition = 0;
		computerPosition = 0;
		updatePositions();
    }
    
    @Override
    protected void gameHasStarted()
    {
    	
    }
    
    @Override
    protected void computerMove(Action action)
    {
		switch (action)
		{
		case DOWN:
		    computerPosition = Math.min(FIELD_SIZE - 1, computerPosition + 1);
		    updatePositions();
		    break;
		case UP:
		    computerPosition = Math.max(0, computerPosition - 1);
		    updatePositions();
		    break;
		case GO:
		    spriteStatus[computerPosition] = 1;
		    playerConqueredSprites[computerPosition].dontShow();
		    computerConqueredSprites[computerPosition].show();
		    break;
		default:
		    break;
		}
    }
    
    @Override
    protected void playerMove(Action action)
    {
		switch (action)
		{
		case DOWN:
		    playerPosition = Math.min(playerPosition + 1, FIELD_SIZE - 1);
		    updatePositions();
		    break;
		case UP:
		    playerPosition = Math.max(playerPosition - 1, 0);
		    updatePositions();
		    break;
		case GO:
		    spriteStatus[playerPosition] = 2;
		    playerConqueredSprites[playerPosition].show();
		    computerConqueredSprites[playerPosition].dontShow();
		    break;
		default:
		    break;
		}
    }
    
    /**
     * display info window after game has finished
     */
    @Override
    protected void gameHasFinished()
    {
    	String winner = "";
    	if(getCurrentComputerScore() > getCurrentPlayerScore())
    	{
    		winner = "The Computer won!";
    	}
    	else if(getCurrentComputerScore() < getCurrentPlayerScore())
    	{
    		winner = "The Player won!";
    	}
    	else
    	{
    		winner = "It's a draw!";
    	}
    	
    	//System.out.println(winner);
    	
    	// display info window after game has finished
        JOptionPane.showMessageDialog(new JFrame(), winner); // + "Player: " + playerScore + ",  Computer: " + computerScore);
      
    }
    
    @Override
    public int getCurrentComputerScore()
    {
		int computerScore = 0;
		for (int i = 0; i < spriteStatus.length; i++)
		{
		    if (spriteStatus[i] == 1)
		    {
		    	++computerScore;
		    }
		}
		return computerScore;
    }
    
    @Override
    public int getCurrentPlayerScore()
    {
		int playerScore = 0;
		for (int i = 0; i < spriteStatus.length; i++)
		{
		    if (spriteStatus[i] == 2)
		    {
		    	++playerScore;
		    }
		}
		return playerScore;
    }
    
    @Override
    public String getName()
    {
    	return "My great game!";
    }
    
    /**
     * determines max no of shots for player dependent on difficulty
     */
    @Override
    public int getNrOfPlayerGoActionsMax(int difficulty)
    {
    	return 50/difficulty;
    }
    
    /**
     * determines max no of shots for computer dependent on difficulty
     */
    @Override
    public int getNrofComputerGoActions(int difficulty)
    {
    	return 5*difficulty;
    }
    
    /**
     * updates positions of player and computer sprites
     */
    private void updatePositions()
    {
		computerSprite.setPosition(200, computerPosition * 40 + 80);
		playerSprite.setPosition(400, playerPosition * 40 + 80);
    }

}
