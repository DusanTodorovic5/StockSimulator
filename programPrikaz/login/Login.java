package programPrikaz.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import programPrikaz.Okvir;

import sql_parser.SQLParser;

public class Login extends JPanel{

	LoginData ld;
	TextField userNameField;
	JPasswordField passwordField;
	Okvir okvir;
	public Login(LoginData ld,Okvir okvir) {
		super(new BorderLayout());
		this.ld = ld;
		this.okvir = okvir;
		init();
	}
	void init() {
		JPanel flowIznad = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel naslov = new JLabel("Uloguj se");
		naslov.setFont(new Font(Font.DIALOG,Font.BOLD,25));
		flowIznad.add(naslov);
		this.add(flowIznad,BorderLayout.NORTH);
		
		JPanel horFlow = new JPanel();
		
		horFlow.setLayout(new BoxLayout(horFlow, BoxLayout.Y_AXIS));
		JPanel flowUser = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowUser.add(new JLabel("Username"));
		horFlow.add(flowUser);
		
		
		userNameField = new TextField();
		userNameField.setPreferredSize(new Dimension(120,20));
		JPanel flowUserField = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowUserField.add(userNameField);
		horFlow.add(flowUserField);
		
		JPanel flowPass = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowPass.add(new JLabel("Password"));
		horFlow.add(flowPass);
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(120,20));
		passwordField.addActionListener((ae)->{
			log(userNameField.getText(),new String(passwordField.getPassword()));
		});
		JPanel flowPassField = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowPassField.add(passwordField);
		horFlow.add(flowPassField);
		
		JPanel flowLogin = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton login = new JButton("Login");
		login.addActionListener((ae)->{
			log(userNameField.getText(),new String(passwordField.getPassword()));
		});
		flowLogin.add(login);
		horFlow.add(flowLogin);
		
		JPanel flowReg = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton reg = new JButton("Registruj se");
		reg.addActionListener((ae)->{
			okvir.register();
		});
		flowReg.add(reg);
		horFlow.add(flowReg);
		this.add(horFlow);
	}
	void log(String user,String pass) {
		int i = SQLParser.loginPass(user, pass);
		if (i >= 0) {
			ld.userId = i;
			ld.userName = user;
			okvir.login();
		}
		else {
			JOptionPane.showMessageDialog(null, "Neispravan username ili sifra!!");
			userNameField.setText("");
		}
	}
}
