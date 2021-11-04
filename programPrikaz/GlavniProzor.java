package programPrikaz;

import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalIconFactory;

import programPrikaz.grafik.Grafik;
import programPrikaz.korisnik.Korisnik;

public class GlavniProzor extends JTabbedPane {
	
	
	public GlavniProzor(int id,String userName){
		super(JTabbedPane.TOP);
		
		
		this.addTab("Grafik",MetalIconFactory.getTreeLeafIcon(), new Grafik(), "Grafik1");
		this.addTab("Moje Akcije",null, new Korisnik(id, userName), "Moje Akcije");
	}
	
}
