import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import MiniGamePackage.MiniGameObserver;

/**
 * creates MiniGameWindow and its contents
 * @author Thomas
 *
 */
public class MyMiniGameWindow extends JFrame implements ActionListener, MiniGameObserver
{
	JButton upButton = new JButton("up");
	JButton downButton = new JButton("down");
	JButton fireButton = new JButton("bam");
	JButton startButton = new JButton("start");
	
	MyMiniGame theGame = new MyMiniGame();
	
	public MyMiniGameWindow()
	{
		setSize(640, 640);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// add buttons to toolbar
		JToolBar toolbar = new JToolBar();
		add(toolbar, BorderLayout.NORTH);
		toolbar.add(upButton);
		toolbar.add(downButton);
		toolbar.add(fireButton);
		toolbar.add(startButton);
		
		// add action listeners for buttons
		upButton.addActionListener(this);
		downButton.addActionListener(this);
		fireButton.addActionListener(this);
		startButton.addActionListener(this);
		
		add(theGame, BorderLayout.CENTER);
		theGame.registerMiniGameObserver(this);
		
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
		else if(e.getSource() == fireButton)
		{
			theGame.playerActionGo();
		}
		else if(e.getSource() == startButton)
		{
			theGame.newGame(1);
		}
	}
}
