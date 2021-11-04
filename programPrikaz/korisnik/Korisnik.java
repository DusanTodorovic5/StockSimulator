package programPrikaz.korisnik;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import parser.Podaci;
import sql_parser.Akcije;
import sql_parser.SQLParser;
import sql_parser.Vrednosti;

public class Korisnik extends JPanel {
	GornjaTraka gt;
	DonjaTraka dt;
	AkcijePanel ap;
	TransakcijePanel tp;
	SQLParser sql;
	int id;
	String userName;
	public Korisnik(int id,String userName){
		super(new BorderLayout());
		this.id = id;
		this.userName = userName;
		sql = new SQLParser(id,userName);
		sql.getTransakcije().azuriraj();
		sql.getAkcije().azuriraj();
		gt = new GornjaTraka(sql);
		dt = new DonjaTraka(this);
		ap = new AkcijePanel(sql.getAkcije());
		tp = new TransakcijePanel(sql.getTransakcije());
		this.add(gt,BorderLayout.NORTH);
		this.add(dt,BorderLayout.SOUTH);
		this.add(ap);
		this.add(tp,BorderLayout.EAST);
	}
	
	void azuriraj() {
		
		sql = new SQLParser(id,"dusan");
		sql.getTransakcije().azuriraj();
		sql.getAkcije().azuriraj();
		gt.azuriraj(sql);
		gt.revalidate();
		ap.azuriraj(sql.getAkcije());
		ap.revalidate();
		tp.azuriraj(sql.getTransakcije());
		tp.revalidate();
	}
	void sortiraj(Comparator<Vrednosti> cv) {
		Akcije a = sql.getAkcije();
		Collections.sort(a.getAkcije(),cv);
		ap.sortAzuriraj(a);
		ap.revalidate();
	}
	void prodaj(int id,int kol) {
		Double cena = ap.getCena(id);
		if (cena == null) {
			JOptionPane.showMessageDialog(new JFrame(), "Neispravan ID");
			return;
		}
		int ret = sql.prodaj(id, cena, kol);
		if (ret == 0) {
			JOptionPane.showMessageDialog(new JFrame(""), "PRODATO!");
			azuriraj();
		}
		else {
			if (ret == -1) {
				JOptionPane.showMessageDialog(new JFrame(""), "Neuspesno");
			}
			else if (ret == -2) {
				JOptionPane.showMessageDialog(new JFrame(""), "Nije vasa akcija");
			}
			else if (ret == -3) {
				JOptionPane.showMessageDialog(new JFrame(""), "Nemate toliko akcija");
			}
			else if (ret == -4) {
				JOptionPane.showMessageDialog(new JFrame(""), "Neuspesno");
			}
			else if (ret == -5) {
				JOptionPane.showMessageDialog(new JFrame(""), "Neuspesan upis u bazi");
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(""), "Neuspesan upis u bazi");
			}
		}
		
	}
	void kupi(int kol,String sim) {
		Podaci p = new Podaci();
		long unixTime = System.currentTimeMillis() / 1000L;
		p.parse(sim, "" + (unixTime-259200), "" + unixTime, 3);
		Double cena = p.getSvece().get(p.getSvece().size()-1).getClose();
		int ret = sql.kupi(sim, kol, cena);
		if (ret == 0) {
			JOptionPane.showMessageDialog(new JFrame(), "KUPLJENO");
			azuriraj();
		}
		else {
			if (ret == -1) {
				JOptionPane.showMessageDialog(new JFrame(), "Neuspesno");
			}
			else if (ret == -2) {
				JOptionPane.showMessageDialog(new JFrame(), "Neuspesno upisivanje");
			}
			else if (ret == -3) {
				JOptionPane.showMessageDialog(new JFrame(), "Neuspeno upisivanje");
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Nedovoljno novca");
			}
		}
	}
}
