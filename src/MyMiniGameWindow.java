import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import MiniGamePackage.MiniGameObserver;

/**
 * creates MiniGameWindow, buttons and adds Game
 * @author Thomas
 *
 */
public class MyMiniGameWindow extends JFrame implements ActionListener, MiniGameObserver, KeyListener
{
	ImageButton upButton = new ImageButton("icons/up.png");
	ImageButton downButton = new ImageButton("icons/down.png");
	ImageButton attackButton = new ImageButton("icons/attack.png");
	ImageButton startButton = new ImageButton("icons/start.png");
	
	MyMiniGame theGame = new MyMiniGame();
	
	public MyMiniGameWindow()
	{
		setSize(640, 825);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// add buttons to toolbar
		JPanel buttonContainer = new JPanel();
		add(buttonContainer, BorderLayout.SOUTH);
		buttonContainer.add(upButton);
		buttonContainer.add(downButton);
		buttonContainer.add(attackButton);
		buttonContainer.add(startButton);
		
		// add action listeners for buttons
		upButton.addActionListener(this);
		downButton.addActionListener(this);
		attackButton.addActionListener(this);
		startButton.addActionListener(this);
		
		
		add(theGame, BorderLayout.CENTER);
		theGame.registerMiniGameObserver(this);
		
		this.addKeyListener(this);
		setVisible(true);
	}

	@Override
	public void gameStatusUpdate(int remainingTime, int playerScore, int computerScore, boolean isRunning)
	{
		// TODO Auto-generated method stub
		setTitle("Time left: " + remainingTime + " Player: " + playerScore + "  Computer: " + computerScore + " Still running: " + isRunning);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// action events for pressed buttons
		if(e.getSource() == upButton)
		{
			theGame.playerActionUp();
		}
		else if(e.getSource() == downButton)
		{
			theGame.playerActionDown();
		}
		else if(e.getSource() == attackButton)
		{
			theGame.playerActionGo();
		}
		else if(e.getSource() == startButton)
		{
			theGame.newGame(1);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) 
		{
        case KeyEvent.VK_W:
            System.out.println("up");
            break;

        case KeyEvent.VK_S:
            System.out.println("down");
            break;

        case KeyEvent.VK_A:
            System.out.println("A");
            break;
            
        case KeyEvent.VK_D:
            System.out.println("D");
            break;

        default: 
        	System.out.println("default");
        	break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
