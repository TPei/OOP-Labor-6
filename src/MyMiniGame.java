import MiniGamePackage.MiniGame;
import MiniGamePackage.Sprite;

/**
 * game logic
 * @author Thomas
 */
public class MyMiniGame extends MiniGame
{
	
    final int NR_OF_FIELDS = 12;
    
    // 0: not set, 1: computer, 2: player
    int[] fieldStatus = new int[NR_OF_FIELDS]; 
    
    Sprite[] gameSprites = getSprites(0);
    Sprite[] computerStoneSprites = getSprites(3);
    Sprite[] playerStoneSprites = getSprites(4);
    
    Sprite computerSprite = getSprite(1, 0);
    Sprite playerSprite = getSprite(2, 0);
    
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
		//gameSprites[0].paintRectangle(0, 0, 32, 32, -1, 255, 255, 255);
		gameSprites[0].paintEllipse(5, 5, 22, 22, -1, 255, 255, 255);
	
		// create sprites that have been taken by player or computer
		computerStoneSprites[0].paintEllipse(5, 5, 22, 22, -1, 255, 0, 0);
		playerStoneSprites[0].paintEllipse(5, 5, 22, 22, -1, 0, 255, 0);
	
		// create player and computer sprite
		//computerSprite.paintRectangle(5, 0, 32, 32, -1, 0, 0, 255);
		computerSprite.paintImage("icons/ship.png");
		
		//playerSprite.paintRectangle(5, 0, 32, 32, -1, 255, 0, 0);
		playerSprite.paintImage("icons/ship.png");

    }
    
    /**
     * initialize game
     */
    @Override
    protected void initGame()
    {
		for (int i = 0; i < NR_OF_FIELDS; i++)
		{
		    gameSprites[i].setPosition(304, i * 40 + 80);
		    playerStoneSprites[i].setPosition(304, i * 40 + 80);
		    computerStoneSprites[i].setPosition(304, i * 40 + 80);
		    playerStoneSprites[i].dontShow();
		    computerStoneSprites[i].dontShow();
	
		    fieldStatus[i] = 0;
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
		    computerPosition = Math.min(computerPosition + 1, NR_OF_FIELDS - 1);
		    updatePositions();
		    break;
		case UP:
		    computerPosition = Math.max(computerPosition - 1, 0);
		    updatePositions();
		    break;
		case GO:
		    fieldStatus[computerPosition] = 1;
		    playerStoneSprites[computerPosition].dontShow();
		    computerStoneSprites[computerPosition].show();
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
		    playerPosition = Math.min(playerPosition + 1, NR_OF_FIELDS - 1);
		    updatePositions();
		    break;
		case UP:
		    playerPosition = Math.max(playerPosition - 1, 0);
		    updatePositions();
		    break;
		case GO:
		    fieldStatus[playerPosition] = 2;
		    playerStoneSprites[playerPosition].show();
		    computerStoneSprites[playerPosition].dontShow();
		    break;
		default:
		    break;
		}
    }
    
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
    	
    	System.out.println(winner);
    }
    
    @Override
    public int getCurrentComputerScore()
    {
		int computerScore = 0;
		for (int i = 0; i < fieldStatus.length; i++)
		{
		    if (fieldStatus[i] == 1)
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
		for (int i = 0; i < fieldStatus.length; i++)
		{
		    if (fieldStatus[i] == 2)
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
