package programPrikaz.grafik;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import parser.Podaci;
import programPrikaz.grafik.vreme.Vreme;
import programPrikaz.grafik.vreme.VremeDialog;
import programPrikaz.grafik.vreme.VremeDialogDo;

public class PodaciPicker extends JPanel {

	JTextField simbol;
	JButton odDugme;
	JButton doDugme;
	JComboBox<String> period;
	Grafik grafik;
	Vreme vOd = new Vreme();
	Vreme vDo = new Vreme();
	PodaciPicker(Grafik grafik){
		super(new GridLayout(6,1));
		this.grafik = grafik;
		setVreme();
		JPanel flowSimbol = new JPanel(new FlowLayout());
		flowSimbol.add(new JLabel("Simbol: "));
		simbol = new JTextField("aapl");
		simbol.setPreferredSize(new Dimension(70,24));
		flowSimbol.add(simbol);
		this.add(flowSimbol);
		
		
		JPanel flowOd = new JPanel(new FlowLayout());
		flowOd.add(new JLabel("od: "));
		odDugme = new JButton(vOd.formatDate());
		odDugme.addActionListener((ae)->{
			VremeDialog vd = new VremeDialog(((JFrame)SwingUtilities.getWindowAncestor(grafik)),vOd);
			vd.setVisible(true);
			vd.setEnabled(false);
			vd.setVisible(false);
			odDugme.setText(vOd.formatDate());
		});
		flowOd.add(odDugme);
		this.add(flowOd);
		
		JPanel flowDo = new JPanel(new FlowLayout());
		flowDo.add(new JLabel("do: "));
		doDugme = new JButton(vDo.formatDate());
		doDugme.addActionListener((ae)->{
			VremeDialog vd = new VremeDialogDo(((JFrame)SwingUtilities.getWindowAncestor(grafik)),vDo);
			vd.setVisible(true);
			vd.setEnabled(false);
			vd.setVisible(false);
			doDugme.setText(vDo.formatDate());
		});
		flowDo.add(doDugme);
		this.add(flowDo);
		
		JPanel flowIzb = new JPanel(new FlowLayout());
		String[] izbor = {"1 minut", "5 minuta", "1 sat", "1 dan", "1 nedelja", "1 mesec", "3 meseca"};
		period = new JComboBox<String>(izbor);
		period.setPreferredSize(new Dimension(100,30));
		flowIzb.add(period);
		this.add(flowIzb);
		
		JPanel flowDug1 = new JPanel(new FlowLayout());
		JButton dugme1 = new JButton("Prikaz");
		dugme1.setPreferredSize(new Dimension(100,30));
		dugme1.addActionListener((ae)->{
			Podaci p = new Podaci();
			p.parse(simbol.getText(), vOd.toString(), vDo.toString(), period.getSelectedIndex()+1);
			grafik.azurirajPodaci(p);
		});
		flowDug1.add(dugme1);
		this.add(flowDug1);
		
		JPanel flowDug2 = new JPanel(new FlowLayout());
		JButton dugme2 = new JButton("Prikazi tren");
		dugme2.addActionListener((ae)->{
			Podaci p = new Podaci();
			long unixTime = System.currentTimeMillis() / 1000L;
			p.parse(simbol.getText(),"" + (unixTime-259200), "" + unixTime, period.getSelectedIndex()+1);
			grafik.azurirajPodaci(p);
		});
		dugme2.setPreferredSize(new Dimension(100,30));
		flowDug2.add(dugme2);
		this.add(flowDug2);
		
		
	}
	
	private void setVreme() {
		Date date = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		vDo.set(c.get(Calendar.SECOND), c.get(Calendar.MINUTE), c.get(Calendar.HOUR), c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH)+1,c.get(Calendar.YEAR));
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
		vOd.set(c.get(Calendar.SECOND), c.get(Calendar.MINUTE), c.get(Calendar.HOUR), day, month, year);
	}
}
