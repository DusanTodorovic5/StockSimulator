package programPrikaz.korisnik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import sql_parser.Transakcije;

public class TransakcijePanel extends JPanel {

	
	TransakcijePanel(Transakcije t){
		super(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Moje Transakcije"));
		
		azuriraj(t);
	}
	
	public void azuriraj(Transakcije t) {
		this.removeAll();
		String[] naslovKolona = {"ID Transakcije","Kolicina", "Cena", "Operacija"};
		String[][] data = new String[t.getTransakcije().size()][4];
		for (int i=0;i<t.getTransakcije().size();i++) {
			data[i][0] = t.getTransakcije().get(i).getId();
			data[i][1] = t.getTransakcije().get(i).getKolicina();
			data[i][2] = t.getTransakcije().get(i).getCena();
			data[i][3] = t.getTransakcije().get(i).getOperacija();
		}
		JTable jt = new JTable(data,naslovKolona) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
                return false;               
			}
		};
		jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		    	if (t.getTransakcije().get(row).getOperacija().equals("Prodao")) {
			        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			        c.setBackground(Color.WHITE);
			        return c;
		    	}
		    	final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        c.setBackground(new Color(235, 234, 230));
		        return c;
		    }
		});
		//jt.revalidate();
		JScrollPane p = new JScrollPane(jt);
		this.add(p);
	}
}
