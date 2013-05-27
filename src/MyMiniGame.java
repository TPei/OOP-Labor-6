import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	// size of field (no of sprites)
    final int FIELD_SIZE = 13;
    
    // starting field for player and computer
    final int PLAYER_START_POS = (int)(FIELD_SIZE / 2);
    final int COMPUTER_START_POS = (int)(FIELD_SIZE / 2);
    
    // offset for sprites depending on field size, required so that field is centered
    final int POSITION_OFFSET = 60; 
    
    // time for laser animation
    final int LASER_ANIMATION_TIME = 100;
    
    // start post for player and computer
    final int PLAYER_X_POS = 100;
    final int COMPUTER_X_POS = 500;
    final int X_CENTER = 304; 
    
    // 0: not set, 1: computer, 2: player
    int[] spriteStatus = new int[FIELD_SIZE]; 
    
    Sprite[] gameSprites = getSprites(0);
    
    Sprite computerSprite = getSprite(1, 0);
    Sprite playerSprite = getSprite(2, 0);
    
    // sprite representation for sprites in the center that have been conquered
    // by a player or a computer
    Sprite[] computerConqueredSprites = getSprites(3);
    Sprite[] playerConqueredSprites = getSprites(4);
    
    // animation for player/computer GO action
    // represents a laser being fired towards the center sprites
    Sprite[] computerAnimationSprites = getSprites(5);
    Sprite[] playerAnimationSprites = getSprites(6);
    
    // initial player and computer position (y-center of all sprites)
    int playerPosition = PLAYER_START_POS;
    int computerPosition = COMPUTER_START_POS;
    
    /**
     * MyMiniGame constructor puts sprites and playerSprites onto the field
     */
    public MyMiniGame()
    {
    	// paint grey background
		getBackgroundPicture().paintRectangle(0, 0, 640, 640, -1, 100, 100, 100);
		
		// create neutral Sprites
		gameSprites[0].paintEllipse(5, 5, 22, 22, -1, 255, 255, 255);
		gameSprites[0].paintEllipse(10, 10, 12, 12, -1, 0, 50, 100);
	
		// create sprites that have been taken by player or computer
		computerConqueredSprites[0].paintEllipse(5, 5, 22, 22, -1, 255, 0, 0);
		computerConqueredSprites[0].paintEllipse(10, 10, 12, 12, -1, 0, 50, 100);
		playerConqueredSprites[0].paintEllipse(5, 5, 22, 22, -1, 0, 255, 0);
		playerConqueredSprites[0].paintEllipse(10, 10, 12, 12, -1, 0, 50, 100);
		
		// laser fire animation
		computerAnimationSprites[0].paintEllipse(10, 10, 12, 12, -1, 255, 0, 0);
		playerAnimationSprites[0].paintEllipse(10, 10, 12, 12, -1, 0, 50, 100);
	
		// create player and computer sprite
		playerSprite.paintImage("icons/leftShip.png");
		computerSprite.paintImage("icons/rightShip.png");
		
    }
    
    /**
     * initialize game
     */
    @Override
    protected void initGame()
    {
		for (int i = 0; i < FIELD_SIZE; i++)
		{
			// set position for player (on the left) and computer (on the right) sprites
		    playerConqueredSprites[i].setPosition(X_CENTER, i * 40 + POSITION_OFFSET);
		    computerConqueredSprites[i].setPosition(X_CENTER, i * 40 + POSITION_OFFSET);
		    
		    // set position for sprites in the center
		    gameSprites[i].setPosition(X_CENTER, i * 40 + POSITION_OFFSET);
		    
		    // make player and computer conquered sprites invisible
		    playerConqueredSprites[i].dontShow();
		    computerConqueredSprites[i].dontShow();
	
		    spriteStatus[i] = 0;
		}
	
		playerPosition = PLAYER_START_POS;
		computerPosition = COMPUTER_START_POS;
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
			// move computer sprite down
		    computerPosition = Math.min(FIELD_SIZE - 1, computerPosition + 1);
		    updatePositions();
		    break;
		case UP:
			// move computer sprite up
		    computerPosition = Math.max(0, computerPosition - 1);
		    updatePositions();
		    break;
		case GO:
			// move a sprite (representing a laser being fired) 
			// from the computer's position to the sprite in the middle
			computerAnimationSprites[computerPosition].setPosition(COMPUTER_X_POS, computerPosition * 40 + POSITION_OFFSET);
			computerAnimationSprites[computerPosition].animateTo(X_CENTER, computerPosition * 40 + POSITION_OFFSET, LASER_ANIMATION_TIME);
			
			// repaint inner blue circle
			computerConqueredSprites[computerPosition].paintEllipse(10, 10, 12, 12, -1, 0, 50, 100);
			//computerAnimationSprites[computerPosition].dontShow();
			
			// change status of sprite (the player conquered it)
		    spriteStatus[computerPosition] = 1;
		    // therefore we'll show the computerConqueredSprite  
		    // and hide the playerConqueredSprite
		    computerConqueredSprites[computerPosition].show();
		    playerConqueredSprites[computerPosition].dontShow();
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
			// move playerSprite down
		    playerPosition = Math.min(playerPosition + 1, FIELD_SIZE - 1);
		    updatePositions();
		    break;
		case UP:
			// moveplayerSprite up
		    playerPosition = Math.max(playerPosition - 1, 0);
		    updatePositions();
		    break;
		case GO:
			// move a sprite (representing a laser being fired) 
			// from the player's position to the sprite in the middle
			playerAnimationSprites[playerPosition].setPosition(PLAYER_X_POS, playerPosition * 40 + POSITION_OFFSET);
			playerAnimationSprites[playerPosition].animateTo(X_CENTER, playerPosition * 40 + POSITION_OFFSET, LASER_ANIMATION_TIME);
			// repaint inner blue circle
			playerConqueredSprites[playerPosition].paintEllipse(10, 10, 12, 12, -1, 0, 50, 100);
			//playerAnimationSprites[playerPosition].dontShow();
			
			// change status of sprite (the player conquered it)
		    spriteStatus[playerPosition] = 2;
		    // therefore we'll show the playerConqueredSprite  
		    // and hide the computerConqueredSprite
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
    		winner = "The Computer won!";
    	else if(getCurrentComputerScore() < getCurrentPlayerScore())
    		winner = "The Player won!";
    	else
    		winner = "It's a draw!";
    	
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
    	// position player and computer sprite
    	playerSprite.setPosition(PLAYER_X_POS, playerPosition * 40 + POSITION_OFFSET);
    	computerSprite.setPosition(COMPUTER_X_POS, computerPosition * 40 + POSITION_OFFSET);
    }

}
