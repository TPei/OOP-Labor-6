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
    final int NEXT_FIELD_MULTIPLIER = 40;
    final int POSITION_OFFSET = 60; 
    
    // time for laser animation
    final int LASER_ANIMATION_TIME = 500;
    
    // start post for player and computer
    final int PLAYER_Y_POS = 500;
    final int COMPUTER_Y_POS = 100;
    final int Y_CENTER = 304;
    final int COMPUTER_BASE_Y = 50;
    final int PLAYER_BASE_Y = 550;
    
    // additional score for hitting enemies directly
    int computerExtraScore = 0;
    int playerExtraScore = 0;
    
    // 1: intact, 0: destroyed
    int[] computerBaseStatus = new int[FIELD_SIZE];
    int[] playerBaseStatus = new int[FIELD_SIZE];
    
    // sprite for palyers
    Sprite computerSprite = getSprite(1, 0);
    Sprite playerSprite = getSprite(2, 0);
    
    // animation for player/computer GO action
    // represents a laser being fired towards the center sprites
    Sprite[] computerAttackSprites = getSprites(3);
    Sprite[] playerAttackSprites = getSprites(4);
    
    Sprite[] computerRepairSprites = getSprites(5);
    Sprite[] playerRepairSprites = getSprites(6);
    
    // computer and player bases
    Sprite[] computerBaseSprites = getSprites(7);
    Sprite[] playerBaseSprites = getSprites(8);
    
    Sprite[] computerDestroyedBases = getSprites(9);
    Sprite[] playerDestroyedBases = getSprites(10);
    
    // initial player and computer position (y-center of all sprites)
    int playerPosition = PLAYER_START_POS;
    int computerPosition = COMPUTER_START_POS;
    
    /**
     * MyMiniGame constructor puts sprites and playerSprites onto the field
     */
    public MyMiniGame()
    {
    	// paint grey background
		getBackgroundPicture().paintImage("icons/space.jpg"); //paintRectangle(0, 0, 640, 640, -1, 100, 100, 100);
		
		// create computer and player bases
		// cases for intact and destroyed bases
		/*
		computerBaseSprites[0].paintEllipse(5, 5, 22, 22, -1, 0, 255, 0);
		playerBaseSprites[0].paintEllipse(5, 5, 22, 22, -1, 0, 255, 0);
		computerDestroyedBases[0].paintEllipse(5, 5, 22, 22, -1, 255, 0, 0);
		playerDestroyedBases[0].paintEllipse(5, 5, 22, 22, -1, 255, 0, 0);
		*/
		computerBaseSprites[0].paintImage("icons/upperBase.png");
		playerBaseSprites[0].paintImage("icons/lowerBase.png");
		
		computerDestroyedBases[0].paintImage("icons/upperJunk.png");
		playerDestroyedBases[0].paintImage("icons/lowerJunk.png");
		
		// laser fire animation
		computerAttackSprites[0].paintEllipse(10, 10, 12, 12, -1, 255, 0, 0);
		playerAttackSprites[0].paintEllipse(10, 10, 12, 12, -1, 255, 0, 0);
		
		computerRepairSprites[0].paintEllipse(10, 10, 12, 12, -1, 0, 255, 0);
		playerRepairSprites[0].paintEllipse(10, 10, 12, 12, -1, 0, 255, 0);
	
		// create player and computer sprite
		playerSprite.paintImage("icons/shipPointingUpwards.png");
		computerSprite.paintImage("icons/shipPointingDownwards.png");
		
    }
    
    /**
     * initialize game
     */
    @Override
    protected void initGame()
    {
		for (int i = 0; i < FIELD_SIZE; i++)
		{     
		    computerBaseSprites[i].setPosition(i * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_BASE_Y);
			playerBaseSprites[i].setPosition(i * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_BASE_Y);
			
			computerDestroyedBases[i].setPosition(i * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_BASE_Y);
			playerDestroyedBases[i].setPosition(i * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_BASE_Y);
		    
		    // make player and computer conquered sprites invisible
			computerDestroyedBases[i].dontShow();
			playerDestroyedBases[i].dontShow();
	
		    playerBaseStatus[i] = 1;
		    computerBaseStatus[i] = 1;
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
			computerAttackSprites[computerPosition].setPosition(computerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_Y_POS);
			computerRepairSprites[computerPosition].setPosition(computerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_Y_POS);
			computerAttackSprites[computerPosition].dontShow();
			computerRepairSprites[computerPosition].dontShow();
			
			if(computerPosition == playerPosition)
			{
				computerAttackSprites[computerPosition].animateTo(computerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_BASE_Y /*PLAYER_Y_POS*/, LASER_ANIMATION_TIME);
				computerExtraScore++;
				playerExtraScore--;
			}
			else
			{	
				// change status of sprites
				if((playerBaseStatus[computerPosition] == 0) &&  (computerBaseStatus[computerPosition] == 0))
				{	
					computerRepairSprites[computerPosition].animateTo(computerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_BASE_Y, LASER_ANIMATION_TIME);
					
					// enemy base already destroyed, repair computer base
			    	computerBaseStatus[computerPosition] = 1;
			    	// 'reapir' a base
				    computerBaseSprites[computerPosition].show();
					computerDestroyedBases[computerPosition].dontShow();
				}
			    else if(playerBaseStatus[computerPosition] == 1)
			    {
			    	// enemy base intact -> destroy it
			    	
			    	computerAttackSprites[computerPosition].animateTo(computerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_BASE_Y, LASER_ANIMATION_TIME);

			    	playerBaseStatus[computerPosition] = 0;
			    	// 'destroy' a base
					playerBaseSprites[computerPosition].dontShow();
					playerDestroyedBases[computerPosition].show();
			    }
	   
			    // 'destroy' a base
				playerBaseSprites[computerPosition].dontShow();
				playerDestroyedBases[computerPosition].show();
			}
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
			playerAttackSprites[playerPosition].setPosition(playerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_Y_POS);
			playerRepairSprites[playerPosition].setPosition(playerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_Y_POS);
			playerAttackSprites[playerPosition].dontShow();
			playerRepairSprites[playerPosition].dontShow();
			
			if(computerPosition == playerPosition)
			{
				playerAttackSprites[playerPosition].animateTo(playerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_BASE_Y/*COMPUTER_Y_POS*/, LASER_ANIMATION_TIME);
				playerExtraScore++;
				computerExtraScore--;
			}
			else
			{
				if((computerBaseStatus[playerPosition] == 0) && (playerBaseStatus[playerPosition] == 0))
				{
					playerRepairSprites[playerPosition].animateTo(playerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_BASE_Y, LASER_ANIMATION_TIME);
					
					// computer base already destroyed -> repair player base
			    	playerBaseStatus[playerPosition] = 1;
			    	// 'repair' a base
				 	playerBaseSprites[playerPosition].show();
					playerDestroyedBases[playerPosition].dontShow();
				}
			    else if (computerBaseStatus[playerPosition] == 1)
			    {
			    	// computer base still intact -> destroy it
			    	
			    	// move a sprite (representing a laser being fired) 
					// from the player's position to the sprite in the middle
					playerAttackSprites[playerPosition].animateTo(playerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_BASE_Y, LASER_ANIMATION_TIME);
			    	computerBaseStatus[playerPosition] = 0;
			    	// 'destroy' a base
				    computerBaseSprites[playerPosition].dontShow();
					computerDestroyedBases[playerPosition].show();
			    }
				
			}
			
		    break;
		default:
		    break;
		}
    }
    
    /**
     * check who won and create an info string accordingly
     * then display it in an info window
     */
    @Override
    protected void gameHasFinished()
    {
    	// check who won and create an info string accordingly
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
		for (int i = 0; i < playerBaseStatus.length; i++)
		{
			// for every destroyed player base (base status 0) the computer get's a point
		    if (playerBaseStatus[i] == 0)
		    {
		    	++computerScore;
		    }
		}
		
		computerScore += computerExtraScore;
		return Math.max(computerScore, 0);
    }
    
    @Override
    public int getCurrentPlayerScore()
    {
		int playerScore = 0;
		for (int i = 0; i < computerBaseStatus.length; i++)
		{
			// for every destroyed computer base (base status 0) the player get's a point
		    if (computerBaseStatus[i] == 0)
		    {
		    	++playerScore;
		    }
		}
		playerScore += playerExtraScore;
		return Math.max(playerScore, 0);
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
    	playerSprite.setPosition(playerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, PLAYER_Y_POS);
    	computerSprite.setPosition(computerPosition * NEXT_FIELD_MULTIPLIER + POSITION_OFFSET, COMPUTER_Y_POS);
    }

}
