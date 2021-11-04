package programPrikaz.grafik;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import parser.Podaci;
import parser.indikatori.Indikator;

public class Grafik extends JPanel {
	PrikazSveca prikaz;
	Podaci podaci;
	InfoAkcija infoAkcija;
	double zoom = 1;
	public Grafik(){
		super(new BorderLayout());
		podaci = new Podaci();
		//JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		podaci.parse("aapl","" + (1623391263-3600000),"" + 1623391263 ,3);
		setPreferredSize(new Dimension(1000,500));
		prikaz = new PrikazSveca(podaci.getSvece(),1000,500);
		this.add(prikaz);
		
		JPanel desni = new JPanel(new GridLayout(2,1));
		infoAkcija = new InfoAkcija(podaci);
		desni.add(infoAkcija);
		desni.add(new PodaciPicker(this));
		this.add(desni, BorderLayout.EAST);

		this.add(new KontrolTraka(this),BorderLayout.SOUTH);
	}
	public void azurirajPodaci(Podaci podaci) {
		if (podaci.getSvece().size() > 0) {
			this.podaci = podaci;
			prikaz.setSvece(this.podaci.getSvece());
			infoAkcija.setPodaci(this.podaci);
		}
	}	
	public void setEma(boolean ema, int n, Indikator.Vrsta vrsta) {
		prikaz.setEma(ema, n,vrsta);
	}
	public void setMa(boolean ma,int n, Indikator.Vrsta vrsta) {
		prikaz.setMa(ma, n, vrsta);
	}
}
