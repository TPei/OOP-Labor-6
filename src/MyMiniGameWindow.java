import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import MiniGamePackage.MiniGameObserver;

/**
 * creates MiniGameWindow, buttons and adds Game
 * 
 * @author Thomas
 * 
 */
public class MyMiniGameWindow extends JFrame implements MiniGameObserver
{
	ImageButton upButton = new ImageButton("icons/upArrow.png");
	ImageButton downButton = new ImageButton("icons/downArrow.png");
	ImageButton attackButton = new ImageButton("icons/attack.png");
	ImageButton startButton = new ImageButton("icons/start.png");

	ImageButton infoButton = new ImageButton("icons/info.png");

	MyMiniGame theGame = new MyMiniGame();

	/**
	 * difficulty of the game
	 */
	int difficulty = 1;

	private int gameStartDelay;

	/**
	 * creates MiniGameWindow, buttons and adds Game, Menu ActionListener
	 */
	public MyMiniGameWindow()
	{
		setSize(640, 825);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add buttons to toolbar
		createButtons();

		// Menu for setting difficulty
		createMenu();

		add(theGame, BorderLayout.CENTER);
		theGame.registerMiniGameObserver(this);

		theGame.newGame(difficulty);

		setTitle("Time left: 10s, Player: 0, Computer: 0");
		
		setVisible(true);
		// askForLevel();
		
	}

	// get and set Difficulty
	public int getDifficulty()
	{
		return difficulty;
	}

	public void setDifficulty(int difficulty)
	{
		this.difficulty = Math.min(1, Math.max(10, difficulty));
	}

	/**
	 * shows game status in title bar of window
	 */
	@Override
	public void gameStatusUpdate(int remainingTime, int playerScore, int computerScore, boolean isRunning)
	{
		// always keep show score and remaining time at the top of the window
		setTitle("Time left: " + remainingTime + ", Player: " + playerScore + ",  Computer: " + computerScore);// + ", Still running: " + isRunning);
	}

	
	/**
	 * asks user for difficulty and then sets the difficulty for the game
	 */
	public void askForLevel()
	{
		try
		{
			JFrame levelDialog = new JFrame();
			levelDialog.setLocation(300, 300);
			Object[] possibilities = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

			difficulty = (int) JOptionPane.showInputDialog(levelDialog, "Choose a level:", "Choose a level", JOptionPane.PLAIN_MESSAGE, null, possibilities, 1);

			System.out.println("difficulty: " + difficulty);

			/*
			 * JOptionPane optionPane = new
			 * JOptionPane("Schwierigkeit(1-10) waehlen" ,
			 * JOptionPane.PLAIN_MESSAGE , JOptionPane.DEFAULT_OPTION , null,
			 * null, "Level waehlen"); optionPane.setWantsInput(true); JDialog
			 * dialog = optionPane.createDialog(null, "Level waehlen");
			 * dialog.setLocation(175, 200); dialog.setVisible(true);
			 * setDifficulty((int)optionPane.getInputValue());
			 * System.out.println("level: "+difficulty);
			 */
		} catch (Exception exc)
		{
			System.out.println("Exception: " + exc);
		}
	}

	/**
	 * handles delayed start of game and displays countdown in window title bar
	 */
	public void startCountdown()
	{
		final String GAME_START = "Game starting in ";
		gameStartDelay = 3;
		setTitle(GAME_START + gameStartDelay + "...");

		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gameStartDelay--;
				Timer t = (Timer) e.getSource();
				String string = GAME_START + gameStartDelay + "...";
				MyMiniGameWindow.this.setTitle(string);

				if (gameStartDelay == 0)
				{
					t.stop();
					theGame.newGame(difficulty);
					theGame.playerActionGo();
				}

			}
		};

		final Timer DELAY_TIMER = new Timer(1000, listener);
		DELAY_TIMER.start();

	}

	/**
	 * creates and returns JPanel with all needed buttons
	 * 
	 * @return JPanel consisting of all the buttons
	 */
	public void createButtons()
	{
		JPanel buttonContainer = new JPanel();
		add(buttonContainer, BorderLayout.SOUTH);
		buttonContainer.add(upButton);
		buttonContainer.add(downButton);
		buttonContainer.add(attackButton);
		buttonContainer.add(startButton);

		// add action listeners for buttons
		upButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				theGame.playerActionUp();
			}
		});
		downButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				theGame.playerActionDown();
			}
		});
		attackButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				theGame.playerActionGo();
			}
		});
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// start a new game with set difficulty
				startCountdown();
			}
		});
	}

	/**
	 * creates and returns a menubar with all needed menu entries
	 * 
	 * @return menubar with all needed menu entries
	 */
	public void createMenu()
	{
		MenuBar myMenuBar = new MenuBar();
		Menu myMenu = new Menu("Preferences");
		MenuItem levelSettingMenu = new MenuItem("Choose Difficulty");
		MenuItem getGameInfoMenu = new MenuItem("Game Info");
		myMenuBar.add(myMenu);
		myMenu.add(levelSettingMenu);
		myMenu.add(getGameInfoMenu);
		setMenuBar(myMenuBar);

		// set difficulty via menu
		levelSettingMenu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				askForLevel();
			}
		});

		// get game info and explain rules
		getGameInfoMenu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String infotext = "Attack planets with your space ship and conquer them. " + "\nWhoever occupies more planets in the end wins. "
						+ "\nYour ship is on the left and when you conquer a planet it turns green. "
						+ "\nNeutral Planets are white. Your opponent's planets are green. " + "\nThe Game ends after 10 seconds.";

				JOptionPane.showMessageDialog(new JFrame(), infotext);
			}
		});

	}
}
