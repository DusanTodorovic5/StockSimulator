package programPrikaz;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.WindowConstants;

import programPrikaz.login.Login;
import programPrikaz.login.LoginData;

public class Okvir extends JFrame {
	enum Stanje {LOGIN,REGISTER,GLAVNI}
	LoginData ld = new LoginData();
	Stanje stanje = Stanje.LOGIN;
	
	public Okvir() {
		super("Poop Projekat");
		this.setBounds(new Rectangle(100, 100, 250, 400));
		draw();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if (stanje == Stanje.GLAVNI) {
					String[] options = new String[3];
					options[0] = new String("Izloguj se");
					options[1] = new String("Izadji");
					options[2] = new String("Odustani");
					int confirmed = JOptionPane.showOptionDialog(null, "Da li ste sigurni da zelite izaci?",
							"Ovde ide naslov dijaloga", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
					if (confirmed == 1) {
						dispose();
					} else if (confirmed == 0) {
						stanje = Stanje.LOGIN;
						draw();
					}
				} else if (stanje == Stanje.REGISTER) {
					stanje = Stanje.LOGIN;
					draw();
				} else {
					dispose();
				}
			}
		});
	}
	void draw() {
		this.removeAll();
		this.setRootPane(new JRootPane());
		setResizable(false); 
		switch (stanje) {
		case LOGIN: 
			drawLogin();
			break;
		case REGISTER:
			drawRegister();
			break;
		case GLAVNI:
			drawGlavni();
			break;
		}
		setVisible(true);
		revalidate();
	}
	private void drawLogin() {
		this.setTitle("Login");
		this.setBounds(new Rectangle(this.getX(),this.getY(),250,400));
		this.add(new Login(ld,this));
	}
	private void drawRegister() {
		this.setTitle("Registracija");
		this.setBounds(new Rectangle(this.getX(),this.getY(),250,400));
		this.add(new Registracija(this));
	}
	private void drawGlavni() {
		this.setTitle("Korisnik : " + ld.getUserName());
		this.setBounds(new Rectangle(this.getX(),this.getY(),1200,700));
		this.add(new GlavniProzor(ld.getUserId(),ld.getUserName()));
	}
	public void login() {
		stanje = Stanje.GLAVNI;
		draw();
	}
	public void register() {
		stanje = Stanje.REGISTER;
		draw();
	}
	public void krajReg() {
		stanje = Stanje.LOGIN;
		draw();
	}
	public static void main(String[] arg) {
		new Okvir();
	}
}
