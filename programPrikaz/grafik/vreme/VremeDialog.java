package programPrikaz.grafik.vreme;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class VremeDialog extends JDialog {

	JTextField sec;
	JTextField min;
	JTextField hour;
	JTextField day;
	JTextField month;
	JTextField year;
	
	JPanel down;
	
	Vreme vreme;
	protected JLabel label(String text) {
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(60,20));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}
	public VremeDialog(JFrame parent, Vreme vreme){
		super(parent, "Vreme", ModalityType.DOCUMENT_MODAL);
		this.removeAll();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setRootPane(new JRootPane());
		this.setResizable(false);
		
		this.vreme = vreme;
		
		
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel up = new JPanel(new FlowLayout(FlowLayout.CENTER));
		up.add(new JLabel("Izaberite vreme"));
		panel.add(up,BorderLayout.NORTH);
		
		JPanel sred = new JPanel(new GridLayout(2,1));
		
		JPanel gornji = new JPanel(new FlowLayout());
		

		gornji.add(label("Sekunde: "));
		sec = new JTextField("0");
		sec.setPreferredSize(new Dimension(30,20));
		gornji.add(sec);
		
		
		
		gornji.add(label("Minuti: "));
		min = new JTextField("0");
		min.setPreferredSize(new Dimension(30,20));
		gornji.add(min);
		
		gornji.add(label("Sati: "));
		hour = new JTextField("0");
		hour.setPreferredSize(new Dimension(30,20));
		gornji.add(hour);
		
		sred.add(gornji);

		JPanel donji = new JPanel(new FlowLayout());
		
		donji.add(label("Dan: "));
		day = new JTextField("0");
		day.setPreferredSize(new Dimension(30,20));
		donji.add(day);
		
		donji.add(label("Mesec: "));
		month = new JTextField("0");
		month.setPreferredSize(new Dimension(30,20));
		donji.add(month);
		
		donji.add(label("Godina: "));
		year = new JTextField("0");
		year.setPreferredSize(new Dimension(40,20));
		donji.add(year);
		
		sred.add(donji);
		
		panel.add(sred);
		down = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton show = new JButton("Prikazi");
		show.addActionListener((ae)->{
			end();
		});
		down.add(show);
		
		panel.add(down,BorderLayout.SOUTH);
		
		this.add(panel);
		this.pack();
		this.setBounds(new Rectangle(MouseInfo.getPointerInfo().getLocation().x-this.getWidth()/2,MouseInfo.getPointerInfo().getLocation().y-this.getHeight()/2
						,this.getWidth(),this.getHeight()));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Date date = new Date();
				Calendar c = new GregorianCalendar();
				c.setTime(date);
				int day = c.get(Calendar.DAY_OF_MONTH);
				int month = c.get(Calendar.MONTH)+1;
				int year = c.get(Calendar.YEAR);
				if (c.get(Calendar.DAY_OF_MONTH) == 1) {
					if (month == 1) {
						day = 31;
						month = 12;
						year--;
					}
					else {
						day = 28;
						month--;
					}
				}
				else {
					day--;
				}
				vreme.set(c.get(Calendar.SECOND), c.get(Calendar.MINUTE), c.get(Calendar.HOUR), day, month, year);
				dispose();
			}
		});
	}
	private void end() {
		try {
			int isec = Integer.parseInt(sec.getText());
			int imin = Integer.parseInt(min.getText());
			int ihour = Integer.parseInt(hour.getText());
			int iday = Integer.parseInt(day.getText());
			int imonth = Integer.parseInt(month.getText());
			int iyear = Integer.parseInt(year.getText());
			if (iyear < 1970 && iyear > 2021) throw new Exception();
			if (imonth < 1 || imonth > 12) throw new Exception();
			if (iday < 1 || iday > 31) throw new Exception();
			if (ihour < 0 || ihour > 23) throw new Exception();
			if (imin < 0 || imin > 59) throw new Exception();
			if (isec < 0 || imin > 59) throw new Exception();
			vreme.set(isec, imin, ihour, iday, imonth, iyear);
			JOptionPane.showMessageDialog(new JFrame(""), "Datum postavljen na: " + vreme.format());
			dispose();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravan datum!");
		}
		
	}

}
