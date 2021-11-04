package programPrikaz.korisnik;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import sql_parser.Vrednosti;

public class DonjaTraka extends JPanel {
	JTextField kolKupi;
	JTextField simKupi;
	JTextField kolProdaj;
	JTextField idProdaj;
	JComboBox<String> izbor;
	DonjaTraka(Korisnik korisnik){
		super(new BorderLayout());
				
		JPanel holder = new JPanel();
		JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sortPanel.setBorder(BorderFactory.createTitledBorder("Sortiraj akcije"));
		sortPanel.add(new JLabel("Sortiranje: "));
		String[] odabir = {"Rastuce id","Opadajuce id", "Rastuce cena", "Opadajuce cena",
				"Rastuce kolicina","Opadajuce kolicina"};
		izbor = new JComboBox<String>(odabir);
		izbor.setPreferredSize(new Dimension(200,20));
		izbor.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        Comparator<Vrednosti> cv = null;
		        int i = izbor.getSelectedIndex();
		        if (i == 1) cv = Vrednosti.idOpadajuce;
		        else if (i == 2) cv = Vrednosti.cenaRastuce;
		        else if (i == 3) cv = Vrednosti.cenaOpadajuce;
		        else if (i == 4) cv = Vrednosti.kolRastuce;
		        else if (i == 5) cv = Vrednosti.kolOpadajuce;
		        else cv = Vrednosti.idRastuce;
		        korisnik.sortiraj(cv);
		    }
		});
		sortPanel.add(izbor);
		holder.add(sortPanel);
		this.add(holder,BorderLayout.WEST);

		JPanel desni = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel kupiPanel = new JPanel(new FlowLayout());
		kupiPanel.setBorder(BorderFactory.createTitledBorder("Kupovina akcije"));
		kupiPanel.add(new JLabel("Kolicina: "));
		kolKupi = new JTextField();
		kolKupi.setPreferredSize(new Dimension(100,20));
		kupiPanel.add(kolKupi);
		kupiPanel.add(new JLabel("Simbol: "));
		simKupi = new JTextField();
		simKupi.setPreferredSize(new Dimension(100,20));
		kupiPanel.add(simKupi);
		JButton kupi = new JButton("Kupi");
		kupi.addActionListener((ae)->{
			int kol = 0;
			try {
				kol = Integer.parseInt(kolKupi.getText());
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravnu kolicinu");
				kolProdaj.setText("");
				idProdaj.setText("");
				return;
			}
			if (simKupi.getText().length() < 1) {
				JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravni simbol");
				kolProdaj.setText("");
				idProdaj.setText("");
				return;
			}
			korisnik.kupi(kol, simKupi.getText().toLowerCase());
			kolKupi.setText("");
			simKupi.setText("");
		});
		kupiPanel.add(kupi);
		desni.add(kupiPanel);
		
		JPanel prodPanel = new JPanel(new FlowLayout());
		prodPanel.setBorder(BorderFactory.createTitledBorder("Prodaja akcije"));
		prodPanel.add(new JLabel("Kolicina: "));
		kolProdaj = new JTextField();
		kolProdaj.setPreferredSize(new Dimension(100,20));
		prodPanel.add(kolProdaj);
		prodPanel.add(new JLabel("ID Akcije: "));
		idProdaj = new JTextField();
		idProdaj.setPreferredSize(new Dimension(100,20));
		prodPanel.add(idProdaj);
		JButton prod = new JButton("Prodaj");
		prod.addActionListener((ae)->{
			int idAkc = 0; int kol = 0;
			try {
				idAkc = Integer.parseInt(idProdaj.getText());
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravan ID akcije");
				kolProdaj.setText("");
				idProdaj.setText("");
				return;
			}
			try {
				kol = Integer.parseInt(kolProdaj.getText());
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(""), "Unesite ispravnu kolicinu");
				kolProdaj.setText("");
				idProdaj.setText("");
				return;
			}
			korisnik.prodaj(idAkc, kol);
			kolProdaj.setText("");
			idProdaj.setText("");
		});
		prodPanel.add(prod);
		desni.add(prodPanel);
		
		this.add(desni,BorderLayout.EAST);
	}
}
