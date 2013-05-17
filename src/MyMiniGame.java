import MiniGamePackage.MiniGame;
import MiniGamePackage.Sprite;

public class MyMiniGame extends MiniGame
{

    final int NR_OF_FIELDS = 8;

    // 0: not set, 1: computer, 2: player
    int[] fieldStatus = new int[NR_OF_FIELDS]; 

    Sprite[] fieldSprites = getSprites(0);
    Sprite[] computerStoneSprites = getSprites(3);
    Sprite[] playerStoneSprites = getSprites(4);

    Sprite computerSprite = getSprite(1, 0);
    Sprite playerSprite = getSprite(2, 0);

    int playerPosition = 0;
    int computerPosition = 0;

    public MyMiniGame()
    {
		getBackgroundPicture().paintRectangle(0, 0, 640, 640, -1, 255, 255, 0);
	
		fieldSprites[0].paintRectangle(0, 0, 32, 32, -1, 255, 255, 255);
		fieldSprites[0].paintEllipse(5, 5, 22, 22, -1, 0, 0, 0);
	
		playerStoneSprites[0].paintEllipse(5, 5, 22, 22, -1, 255, 128, 0);
		computerStoneSprites[0].paintEllipse(5, 5, 22, 22, -1, 0, 0, 255);
	
		computerSprite.paintRectangle(5, 0, 22, 32, -1, 0, 0, 255);
		playerSprite.paintRectangle(5, 0, 22, 32, -1, 255, 128, 0);

    }

    @Override
    protected void initGame()
    {
		for (int i = 0; i < NR_OF_FIELDS; i++)
		{
		    fieldSprites[i].setPosition(304, i * 40 + 100);
		    playerStoneSprites[i].setPosition(304, i * 40 + 100);
		    computerStoneSprites[i].setPosition(304, i * 40 + 100);
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
    }

    @Override
    public int getCurrentComputerScore()
    {
		int cs = 0;
		for (int i = 0; i < fieldStatus.length; i++)
		{
		    if (fieldStatus[i] == 1)
		    {
		    	++cs;
		    }
		}
		return cs * 100;
    }

    @Override
    public int getCurrentPlayerScore()
    {
		int ps = 0;
		for (int i = 0; i < fieldStatus.length; i++)
		{
		    if (fieldStatus[i] == 2)
		    {
		    	++ps;
		    }
		}
		return ps * 100;
    }

    @Override
    public String getName()
    {
    	return "My great game!";
    }

    @Override
    public int getNrOfPlayerGoActionsMax(int difficulty)
    {
    	return 10;
    }

    @Override
    public int getNrofComputerGoActions(int difficulty)
    {
    	return difficulty * 10;
    }

    private void updatePositions()
    {
		computerSprite.setPosition(200, computerPosition * 40 + 100);
		playerSprite.setPosition(400, playerPosition * 40 + 100);
    }

}
