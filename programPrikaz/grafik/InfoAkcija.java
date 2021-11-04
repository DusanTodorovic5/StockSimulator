package programPrikaz.grafik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import parser.Podaci;
import parser.Sveca;

public class InfoAkcija extends JPanel {
	Podaci podaci;
	JLabel simbol;
	
	JLabel exchName;
	JLabel valuta;
	JLabel timezone;
	
	JLabel high;
	JLabel closed;
	JLabel low;
	InfoAkcija(Podaci podaci){
		super(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.podaci = podaci;
		
		simbol = new JLabel(podaci.getSimbol());
		simbol.setFont(new Font(Font.DIALOG,Font.BOLD,24));
		FlowLayout flGornji = new FlowLayout();
		flGornji.setAlignment(FlowLayout.CENTER);
		JPanel gornji = new JPanel(flGornji);
		gornji.add(simbol);
		this.add(gornji,BorderLayout.NORTH);
		JPanel sred = new JPanel(new GridLayout(2,1));
		
		JPanel infoPanel = new JPanel(new GridLayout(3,1));
		JPanel flowExch = new JPanel(new FlowLayout());
		flowExch.setAlignmentX(FlowLayout.LEFT);
		exchName = new JLabel("ExchName: " + podaci.getExchangeName());
		exchName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		flowExch.add(exchName);
		
		infoPanel.add(flowExch);
		
		JPanel flowValuta = new JPanel(new FlowLayout());
		flowValuta.setAlignmentX(FlowLayout.LEFT);
		valuta = new JLabel("Valuta: " + podaci.getValuta());
		valuta.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		flowValuta.add(valuta);
		
		infoPanel.add(flowValuta);
		
		JPanel flowTime = new JPanel(new FlowLayout());
		flowTime.setAlignmentX(FlowLayout.LEFT);
		timezone = new JLabel("VremZona: " + podaci.getVremenskaZona());
		timezone.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		flowTime.add(timezone);
		
		infoPanel.add(flowTime);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		sred.add(infoPanel);
		
		JPanel vrednostPanel = new JPanel(new GridLayout(3,1));
		
		JPanel flowHigh = new JPanel(new FlowLayout());
		Font fVr = new Font(Font.DIALOG,Font.BOLD,16);
		high = new JLabel("H: " + String.format("%.5f",Collections.max(podaci.getSvece(),Sveca.max).getHigh()));
		high.setFont(fVr);
		high.setForeground(Color.GREEN);
		flowHigh.add(high);
		vrednostPanel.add(flowHigh);
		vrednostPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel flowClosed = new JPanel(new FlowLayout());
		closed = new JLabel("C: " + String.format("%.5f",podaci.getSvece().get(podaci.getSvece().size()-1).getClose()));
		closed.setFont(fVr);
		closed.setForeground(Color.BLUE);
		flowClosed.add(closed);
		vrednostPanel.add(flowClosed);
		
		JPanel flowLow = new JPanel(new FlowLayout());
		low = new JLabel("L: " + String.format("%.5f",Collections.min(podaci.getSvece(),Sveca.min).getLow()));
		low.setFont(fVr);
		low.setForeground(Color.RED);
		flowLow.add(low);
		vrednostPanel.add(flowLow);
		
		sred.add(vrednostPanel);
		
		this.add(sred);
		

	}
	
	private void init() {
		simbol.setText(podaci.getSimbol());
		simbol.revalidate();
		
		exchName.setText("ExchName: " + podaci.getExchangeName());
		exchName.revalidate();
		
		valuta.setText("Valuta: " + podaci.getValuta());
		valuta.revalidate();
		
		timezone.setText("VremZona: " + podaci.getVremenskaZona());
		timezone.revalidate();
		
		high.setText("H: " + String.format("%.5f",Collections.max(podaci.getSvece(),Sveca.max).getHigh()));
		high.revalidate();
		
		closed.setText("C: " + String.format("%.5f",podaci.getSvece().get(podaci.getSvece().size()-1).getClose()));
		closed.revalidate();
		
		low.setText("L: " + String.format("%.5f",Collections.min(podaci.getSvece(),Sveca.min).getLow()));
		low.revalidate();
		
	}
	public void setPodaci(Podaci podaci) {
		this.podaci = podaci;
		init();
	}
}
