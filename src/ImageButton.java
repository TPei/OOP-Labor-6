import javax.swing.ImageIcon;
import javax.swing.JButton;

/** extends JButton
 * automatically sets image path for button to given path
 * 
 * @author Thomas
 */
public class ImageButton extends JButton
{
	ImageButton(String imagePath)
	{
		// default path for my machines
		ImageIcon nextButtonImage = new ImageIcon(imagePath);
		this.setIcon(nextButtonImage);
	}
}
