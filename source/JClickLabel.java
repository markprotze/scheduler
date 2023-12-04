package packager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JClickLabel extends JLabel {

  public JClickLabel(String string) {

    super(string.split(",")[2] + " " + string.split(",")[0]);

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me) {
        fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, string));
      }
    });
  }

  public void addActionListener(ActionListener l) {
    listenerList.add(ActionListener.class, l);
  }

  public void removeActionListener(ActionListener l) {
    listenerList.remove(ActionListener.class, l);
  }

  protected void fireActionPerformed(ActionEvent ae) {

    Object[] listeners = listenerList.getListeners(ActionListener.class);

    for (int i = 0; i < listeners.length; i++) {
      ((ActionListener) listeners[i]).actionPerformed(ae);
    }
  }
  
}