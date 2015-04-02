import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;


public class GUI extends JFrame{
	private static int exists = 0; //Used for singleton pattern
	private static final long serialVersionUID = 1L;
		
	private GUI(){
		
	}
	
	//Ensures only one GUI instance is ever created at once
	public static GUI getInstance(){
		if(exists == 0){
			exists = 1;
			return new GUI();
		}
		return null;
	}
	
	public void displaySimulator(){
		JFrame loginPane = new JFrame("Login");
		JEditorPane pane = new JEditorPane();
		File f = new File("C:\\Users\\Greg\\workspace 3\\Software-Engineering-Team-1\\AirTrafficControlLCMS\\src\\simulator\\atc-gh-pages\\index.html");
		try {
			pane.setPage(f.toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginPane.add(pane);
		loginPane.setVisible(true);
		
	}
	
	
	
	
}
