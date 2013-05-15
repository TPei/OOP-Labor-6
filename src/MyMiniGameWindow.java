import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import MiniGamePackage.MiniGameObserver;

/**
 * creates MiniGameWindow and its contents
 * @author Thomas
 *
 */
public class MyMiniGameWindow extends JFrame
{
	JButton upButton = new JButton("up");
	JButton downButton = new JButton("down");
	JButton moveButton = new JButton("move");
	JButton startButton = new JButton("start");
	
	MyMiniGame theGame = new MyMiniGame();
	
	public MyMiniGameWindow()
	{
		setSize(640, 640);
		setLayout(new BorderLayout());
		
		JToolBar toolbar = new JToolBar();
		add(toolbar, BorderLayout.NORTH);
		toolbar.add(upButton);
		toolbar.add(downButton);
		toolbar.add(moveButton);
		toolbar.add(startButton);
		
		
		
		
		
		setVisible(true);
	}
}
