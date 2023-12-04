package packager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class View extends JLayeredPane implements ActionListener {
	
	protected Curriculum curriculum;
	protected JLabel controller_messages;
	
	public View(Curriculum curriculum) {
		setLayout(new BorderLayout());
		
		this.curriculum = curriculum;
		
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridBagLayout());
		gridPanel.setBorder(null);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.ipadx = 15 * curriculum.getNumOfEvents();
		constraints.ipady = 4;
		constraints.gridy = 0;
		constraints.gridx = 0;
		gridPanel.add(new TitleDisplayWidget(curriculum.getStudents()[0].getSyllabusEventArray()), constraints);
		
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.ipadx = 0;
		constraints.ipady = 20 * curriculum.getNumOfStudents();
		constraints.fill = GridBagConstraints.BOTH;
		gridPanel.add(new StudentDisplayWidget(curriculum.getStudents(), curriculum.getNumOfEvents()), constraints);
	    
        JScrollPane scrollPane = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(6);
		add(scrollPane);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new GridBagLayout());
		GridBagConstraints sideConstraints = new GridBagConstraints();
		sideConstraints.ipady = 4 * (curriculum.getNumOfStudents() + 1);
		sideConstraints.ipadx = 5;
		sideConstraints.gridx = 0;
		sideConstraints.gridy = 1;
		NameDisplayWidget nameWidget = new NameDisplayWidget(this, curriculum.getStudents());
		sidePanel.add(nameWidget, sideConstraints);
		
        JScrollPane sideScrollPane = new JScrollPane(sidePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sideScrollPane.getVerticalScrollBar().setUnitIncrement(6);
        scrollPane.setVerticalScrollBar(sideScrollPane.getVerticalScrollBar());
        scrollPane.setBorder(null);
        sideScrollPane.setBorder(null);
		
		add(sideScrollPane, BorderLayout.WEST);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		JButton modifyButton = new JButton("Modify curriculum");
		modifyButton.setActionCommand("modify");
		modifyButton.addActionListener(this);
		bottomPanel.add(modifyButton);
		
		Border bottomConsolePanelBorder = BorderFactory.createLineBorder(java.awt.Color.decode("#C0C0C0"), 1);
		bottomPanel.setBorder(bottomConsolePanelBorder);
		
		Box.Filler consoleWhiteSpace = new Box.Filler(new Dimension(8, 5), new Dimension(8, 5), new Dimension(8, 5));
		bottomPanel.add(consoleWhiteSpace);
		
		controller_messages = new JLabel("Syllabus "+ curriculum.getSyllabusVersion() + ", data current as of " + curriculum.getDataTime() + ", " + curriculum.getNumOfStudents() + " students loaded, " + curriculum.getNumOfEvents() + " events displayed");
		bottomPanel.add(controller_messages);
		
		JButton aboutButton = new JButton("?");
		aboutButton.setActionCommand("about");
		aboutButton.addActionListener(this);
		aboutButton.setToolTipText("About this program");
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(aboutButton);
		
		add(bottomPanel, BorderLayout.SOUTH);
		
	}
	
	private String createDialogString(String student) throws Throwable {
		Student[] studentArray = curriculum.getStudents();
		String eligibleEvents = null;
		for (int i = 0; i < studentArray.length; i++) {
			if (studentArray[i].getName().equals(student)) {
				eligibleEvents = studentArray[i].getEligibleEvents();
			}
		}
		return ("<html><U>" + student + "</U>\nEligible to be scheduled for the following events:\n" + eligibleEvents);
	}
	

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("modify")) {
			if (Desktop.isDesktopSupported()) {
				JOptionPane.showMessageDialog(this, "Please reference the instructions included with this program for details on modifying the curriculum file.\nAny changes made will require restarting the program to take effect.");
			    try {
			    	File curriculum = new File(this.curriculum.getCurriculumFilePath());
			        Desktop.getDesktop().open(curriculum);
			    } catch (IOException ex) {
					controller_messages.setForeground(Color.RED);
					controller_messages.setText("Unable to open curriculum file for modification");
			    }
			} else {
				controller_messages.setForeground(Color.RED);
				controller_messages.setText("Desktop not supported");
			}
		} else if (e.getActionCommand().equals("about")) {
			JOptionPane.showMessageDialog(this, "This program was created by a student awaiting assignment to a primary squadron to assist schedulers and commanders in tracking the progress of their students.\nIt tracks a subset of events from tSharp, in which the subset is definable by the user, and imitates a squadron scheduling office physical tracking board.\nThe program expands on the functionality of tSharp and references prerequisite data to list eligible events a student can be scheduled for.\nIt accesses no data via the internet, was written in Java, and was published in early 2023.", "About", JOptionPane.PLAIN_MESSAGE);
		} else {
			try {
				JOptionPane.showMessageDialog(this, createDialogString(e.getActionCommand()), "Student Information", JOptionPane.PLAIN_MESSAGE);
			} catch (Throwable f) {
				controller_messages.setForeground(Color.RED);
				controller_messages.setText("ERROR: " + f.getMessage());
			}
		}
	}

}