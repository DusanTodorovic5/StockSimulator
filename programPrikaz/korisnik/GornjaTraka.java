package programPrikaz.korisnik;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sql_parser.SQLParser;

public class GornjaTraka extends JPanel {

	GornjaTraka(SQLParser sqlParser){
		super(new GridLayout(1,2));
		this.setBorder(BorderFactory.createTitledBorder(""));
		azuriraj(sqlParser);
	}
	void azuriraj(SQLParser sqlParser) {
		this.removeAll();
		Font f = new Font(Font.DIALOG,Font.BOLD,16);
		JPanel levi = new JPanel(new FlowLayout());
		((FlowLayout)levi.getLayout()).setAlignment(FlowLayout.LEFT);
		
		JLabel kor = new JLabel("Korisnik: " + sqlParser.getUserName());
		kor.setFont(f);
		levi.add(kor);
		this.add(levi);
		
		JPanel desni = new JPanel(new FlowLayout());
		((FlowLayout)desni.getLayout()).setAlignment(FlowLayout.RIGHT);
		JLabel novac = new JLabel("Novac: " + String.format("%.5f", sqlParser.novac()));
		novac.setFont(f);
		desni.add(novac);
		this.add(desni);
	}
}
