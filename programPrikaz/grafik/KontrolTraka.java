package programPrikaz.grafik;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import parser.indikatori.Indikator;

public class KontrolTraka extends JPanel {

	JComboBox<String> maTip;
	JComboBox<String> emaTip;
	
	JTextField maN;
	JTextField emaN;
	Grafik grafik;
	KontrolTraka(Grafik grafik){
		super(new FlowLayout());
		this.grafik = grafik;
		((FlowLayout)this.getLayout()).setAlignment(FlowLayout.LEFT);
		String[] tipovi = {"Close", "Open", "High", "Low"};
		JPanel flowMa = new JPanel(new FlowLayout());
		JRadioButton ma = new JRadioButton("Prikazi");
		maTip = new JComboBox<String>(tipovi);
		flowMa.add(maTip);
		flowMa.add(new JLabel("n: "));
		maN = new JTextField("5");
		maN.setPreferredSize(new Dimension(40,20));
		flowMa.add(maN);
		ma.addActionListener((ae)->{
			Indikator.Vrsta vrsta = Indikator.Vrsta.CLOSE;
			if (maTip.getSelectedIndex() == 1) vrsta = Indikator.Vrsta.OPEN;
			else if (maTip.getSelectedIndex() == 2) vrsta = Indikator.Vrsta.HIGH;
			else if (maTip.getSelectedIndex() == 3) vrsta = Indikator.Vrsta.LOW;
			try {
				int n = Integer.parseInt(maN.getText());
				grafik.setMa(ma.isSelected(), n, vrsta);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravno N");
				ma.setSelected(false);
			}
		});
		maTip.addActionListener((ae)->{
			if (ma.isSelected()) {
				Indikator.Vrsta vrsta = Indikator.Vrsta.CLOSE;
				if (maTip.getSelectedIndex() == 1) vrsta = Indikator.Vrsta.OPEN;
				else if (maTip.getSelectedIndex() == 2) vrsta = Indikator.Vrsta.HIGH;
				else if (maTip.getSelectedIndex() == 3) vrsta = Indikator.Vrsta.LOW;
				try {
					int n = Integer.parseInt(maN.getText());
					grafik.setMa(ma.isSelected(), n, vrsta);
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravno N");
					ma.setSelected(false);
				}
			}
		});
		flowMa.add(ma);
		flowMa.setBorder(BorderFactory.createTitledBorder("MA indikator"));
		this.add(flowMa);
		
		this.add(new JSeparator(SwingConstants.VERTICAL));
		
		JPanel flowEma = new JPanel(new FlowLayout());
		JRadioButton ema = new JRadioButton("Prikazi");
		emaTip = new JComboBox<String>(tipovi);
		flowEma.add(emaTip);
		flowEma.add(new JLabel("n: "));
		emaN = new JTextField("5");
		emaN.setPreferredSize(new Dimension(40,20));
		flowEma.add(emaN);
		
		ema.addActionListener((ae)->{
			Indikator.Vrsta vrsta = Indikator.Vrsta.CLOSE;
			if (emaTip.getSelectedIndex() == 1) vrsta = Indikator.Vrsta.OPEN;
			else if (emaTip.getSelectedIndex() == 2) vrsta = Indikator.Vrsta.HIGH;
			else if (emaTip.getSelectedIndex() == 3) vrsta = Indikator.Vrsta.LOW;
			try {
				int n = Integer.parseInt(emaN.getText());
				grafik.setEma(ema.isSelected(), n, vrsta);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravno N");
			}
		});
		emaTip.addActionListener((ae)->{
			if (ema.isSelected()) {
				Indikator.Vrsta vrsta = Indikator.Vrsta.CLOSE;
				if (emaTip.getSelectedIndex() == 1) vrsta = Indikator.Vrsta.OPEN;
				else if (emaTip.getSelectedIndex() == 2) vrsta = Indikator.Vrsta.HIGH;
				else if (emaTip.getSelectedIndex() == 3) vrsta = Indikator.Vrsta.LOW;
				try {
					int n = Integer.parseInt(emaN.getText());
					grafik.setEma(ema.isSelected(), n, vrsta);
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravno N");
				}
			}
		});
		flowEma.add(ema);
		flowEma.setBorder(BorderFactory.createTitledBorder("EMA indikator"));
		this.add(flowEma);
	}
}
