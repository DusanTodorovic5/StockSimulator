package programPrikaz.korisnik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import parser.Podaci;

import sql_parser.Akcije;
import sql_parser.Vrednosti;

public class AkcijePanel extends JPanel {
	HashMap<String,Double> cene;
	HashMap<Integer,String> idSim;
	AkcijePanel(Akcije a){
		super(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Moje Akcije"));
		
		azuriraj(a);
		
	}
	
	public void azuriraj(Akcije a) {
		this.removeAll();
		
		Podaci pod = new Podaci();
		
		cene = new HashMap<String,Double>();
		
		for (Vrednosti v : a.getAkcije()) {
			cene.put(v.getSimbol(),0.0);
		}
		long unixTime = System.currentTimeMillis() / 1000L;
		for (String s : cene.keySet()) {
			pod.parse(s, "" + (unixTime-259200), "" + unixTime, 3);
			cene.put(s, pod.getSvece().get(pod.getSvece().size()-1).getClose());
		}
		idSim = new HashMap<Integer,String>();
		sortAzuriraj(a);
	}
	public void sortAzuriraj(Akcije a) {
		String[] naslovKolona = {"ID Akcije","Simbol","Kolicina","Cena","Trenutna Cena","Razlika","Razlika u %" };
		String[][] data = new String[a.getAkcije().size()][7];
		for (int i=0;i<a.getAkcije().size();i++) {
			data[i][0] = a.getAkcije().get(i).getIdAkcije() + "";
			data[i][1] = a.getAkcije().get(i).getSimbol();
			data[i][2] = a.getAkcije().get(i).getKolicina();
			data[i][3] = a.getAkcije().get(i).getCena();
			idSim.put(a.getAkcije().get(i).getIdAkcije(), a.getAkcije().get(i).getSimbol());
			data[i][4] =  String.format("%.5f", cene.get(a.getAkcije().get(i).getSimbol()));
			double raz = cene.get(a.getAkcije().get(i).getSimbol()) - Double.parseDouble(a.getAkcije().get(i).getCena());
			data[i][5] = String.format("%.5f",raz);
			data[i][6] = String.format("%.5f",(Math.abs(raz)/((Double.parseDouble(a.getAkcije().get(i).getCena()) + cene.get(a.getAkcije().get(i).getSimbol()))/2) *100));
		}
		JTable jt = new JTable(data,naslovKolona){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
                return false;               
			}
		};
		jt.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
		jt.setRowHeight(18);
		jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		    	if (cene.get(a.getAkcije().get(row).getSimbol()) - Double.parseDouble(a.getAkcije().get(row).getCena()) < 0) {
			        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			        c.setBackground(new Color(255, 172, 166));
			        return c;
		    	}
		    	final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        c.setBackground(Color.WHITE);
		        return c;
		    }
		});
		JScrollPane p = new JScrollPane(jt);
		this.add(p);
	}
	
	Double getCena(int id) {
		try {
		return cene.get(idSim.get(id));
		}
		catch(Exception e) {
			
		}
		return 0.0;
	}
	Double getCena(String s) {
		return cene.get(s);
	}
}
