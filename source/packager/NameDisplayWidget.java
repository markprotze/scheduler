package packager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class NameDisplayWidget extends JPanel {
	
	NameDisplayWidget(View view, Student[] studentArray) {
	
		this.setLayout(new GridLayout(studentArray.length + 1, 1));
		Border labelBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
		
		JLabel whiteSpace = new JLabel();
		add(whiteSpace);
		
		for (int i = 0; i < studentArray.length; i++) {
			JClickLabel currentLabel = new JClickLabel(studentArray[i].getName());
			currentLabel.setHorizontalAlignment(SwingConstants.LEADING);
			currentLabel.setOpaque(true);
			currentLabel.setBorder(labelBorder);
			currentLabel.addActionListener(view);
			currentLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(currentLabel);
		}
	}
}