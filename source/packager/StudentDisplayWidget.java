package packager;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import packager.SyllabusEvent.eventStatus;

@SuppressWarnings("serial")
public class StudentDisplayWidget extends JPanel {
	
	public StudentDisplayWidget(Student[] studentArray, int numOfEvents) {
	
		setLayout(new GridLayout(studentArray.length, numOfEvents));
		Border labelBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
		
		for (int j = 0; j < studentArray.length; j++) {
			JPanel individualStudent = new JPanel();
			individualStudent.setLayout(new GridLayout(1, numOfEvents));
			for (int i = 0; i < numOfEvents; i++) {
				JLabel currentLabel = new JLabel();
				currentLabel.setToolTipText(studentArray[j].getName().split(",")[2] + " " + studentArray[j].getName().split(",")[0] + ", " + studentArray[j].getSyllabusEventArray()[i].getName() + ", " + studentArray[j].getSyllabusEventArray()[i].getEventStatus());
				if (getLabelColor(studentArray[j].getSyllabusEventArray()[i]).equals("green")) {
					currentLabel.setOpaque(true);
					currentLabel.setBackground(new Color(0, 170, 0));
				}
				currentLabel.setBorder(labelBorder);
				individualStudent.add(currentLabel);
			}
			add(individualStudent);
		}
	}
	
	public String getLabelColor(SyllabusEvent syllabusEvent) {
		eventStatus status = syllabusEvent.getEventStatus();
		String color = "";
		switch (status) {
			case Pass_Unsigned: color = "green";
			break;
			case Pass_Signed: color = "green";
			break;
			case Pass_Reviewed: color = "green";
			break;
			case Incomplete_Unsigned: color = "white";
			break;
			case Incomplete_Signed: color = "white";
			break;
			case Incomplete_Reviewed: color = "white";
			break;
			case Conditional_Pass_Unsigned: color = "green";
			break;
			case Conditional_Pass_Signed: color = "green";
			break;
			case Conditional_Pass_Reviewed: color = "green";
			break;
			case Unsat_Unsigned: color = "white";
			break;
			case Unsat_Signed: color = "white";
			break;
			case Unsat_Reviewed: color = "white";
			break;
			case Ready_Room_Unsat_Unsigned: color = "white";
			break;
			case Ready_Room_Unsat_Signed: color = "white";
			break;
			case Ready_Room_Unsat_Reviewed: color = "white";
			break;
			case Eligible: color = "white";
			break;
			case Ineligible: color = "white";
			break;
			case Waived: color = "white";
			break;
			case Proficiency_Advanced: color = "white";
			break;
			case CANX: color = "white";
			break;
			case Deferred: color = "white";
			break;
			case Executing: color = "white";
			break;
			case Scheduled: color = "white";
			break;
			case Active: color = "white";
			break;
			case Blank: color = "white";
			break;
		}
		return color;
	}

}