package packager;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class TitleDisplayWidget extends JPanel {

	public TitleDisplayWidget(SyllabusEvent[] syllabusEventArray) {
		
		setLayout(new GridLayout(1, syllabusEventArray.length));
		Border labelBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
		
		for (int i = 0; i < syllabusEventArray.length; i++) {
			JLabel currentLabel = new JLabel();
			currentLabel.setText(syllabusEventArray[i].getName());
			String eventType = syllabusEventArray[i].getType();
			if (eventType == "Sim") {
				currentLabel.setForeground(new Color(220, 140, 0));
			} else if (eventType == "Flight") {
				currentLabel.setForeground(new Color(0, 150, 0));
			}
			currentLabel.setOpaque(true);
			currentLabel.setBackground(new Color(220, 220, 220));
			currentLabel.setBorder(labelBorder);
			currentLabel.setHorizontalAlignment(SwingConstants.CENTER);
			currentLabel.setVerticalAlignment(SwingConstants.CENTER);
			this.add(currentLabel);
		}
	}
}