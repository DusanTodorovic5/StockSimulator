package programPrikaz.grafik.vreme;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VremeDialogDo extends VremeDialog {

	public VremeDialogDo(JFrame parent, Vreme vreme) {
		super(parent, vreme);
		JButton jb = new JButton("Trenutno");
		jb.addActionListener((ae)->{
			kraj();
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				kraj();
			}
		});
		down.add(jb);
		revalidate();
	}
	private void kraj() {
		Date date = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		vreme.set(c.get(Calendar.SECOND), c.get(Calendar.MINUTE), c.get(Calendar.HOUR), c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH)+1,c.get(Calendar.YEAR));
		this.dispose();
	}
}
