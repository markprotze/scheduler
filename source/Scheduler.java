package packager;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

public class Scheduler {
	
	public static void main(String[] args) throws Exception {
		
		JFrame main_frame = new JFrame();
		ToolTipManager.sharedInstance().setInitialDelay(1000);
		ToolTipManager.sharedInstance().setDismissDelay(4000);
		UIManager.put("ToolTip.background", Color.white);
		main_frame.setTitle("Student Scheduler v1.0");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    File directory = new File("./");
	    int directoryLength = directory.getAbsolutePath().length();
	    String relativePath = directory.getAbsolutePath().substring(0, directoryLength - 1);
	    
		JFileChooser tSharpFileChooser = new JFileChooser(relativePath);
		tSharpFileChooser.showDialog(null,"Select tSharp export excel sheet");
		tSharpFileChooser.setVisible(true);
		
		File curriculumPath = new File(relativePath + "Curriculum.json");
	    
		Curriculum testCurriculum = new Curriculum(curriculumPath.getAbsolutePath(), tSharpFileChooser.getSelectedFile().getAbsolutePath());
		View view = new View(testCurriculum);
		
		main_frame.setContentPane(view);
		main_frame.setSize(new Dimension(1500, 800));
		main_frame.setLocationRelativeTo(null);
		main_frame.setVisible(true);
		
	}
}