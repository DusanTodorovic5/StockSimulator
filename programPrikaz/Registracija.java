package programPrikaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import sql_parser.SQLParser;

public class Registracija extends JPanel {
	private Okvir okvir;
	
	private TextField userNameField;
	private JPasswordField passwordField;
	private JPasswordField passwordConfirm;
	private TextField moneyField;
	
	Registracija(Okvir okvir){
		super(new BorderLayout());
		this.okvir = okvir;
		init();
	}
	private void init() {
		JPanel flowIznad = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel naslov = new JLabel("Registracija");
		naslov.setFont(new Font(Font.DIALOG,Font.BOLD,25));
		flowIznad.add(naslov);
		this.add(flowIznad,BorderLayout.NORTH);
		
		JPanel horFlow = new JPanel();
		horFlow.setLayout(new BoxLayout(horFlow, BoxLayout.Y_AXIS));
		
		JPanel flowUser = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowUser.add(new JLabel("Unesite korisnicko ime"));
		horFlow.add(flowUser);
		
		userNameField = new TextField();
		userNameField.setPreferredSize(new Dimension(120,20));
		JPanel flowUserField = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowUserField.add(userNameField);
		horFlow.add(flowUserField);
		
		JPanel flowPass = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowPass.add(new JLabel("Unesite sifru"));
		horFlow.add(flowPass);
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(120,20));
		JPanel flowPassField = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowPassField.add(passwordField);
		horFlow.add(flowPassField);
		
		JPanel flowConf = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowConf.add(new JLabel("Potvrdite sifru"));
		horFlow.add(flowConf);
		
		passwordConfirm = new JPasswordField();
		passwordConfirm.setPreferredSize(new Dimension(120,20));
		JPanel flowPassConf = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowPassConf.add(passwordConfirm);
		horFlow.add(flowPassConf);
		
		JPanel flowMoney = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowMoney.add(new JLabel("Unesite pocetnu kolicinu novca"));
		horFlow.add(flowMoney);
		
		moneyField = new TextField();
		moneyField.setPreferredSize(new Dimension(120,20));
		JPanel flowMoneyField = new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowMoneyField.add(moneyField);
		horFlow.add(flowMoneyField);
		
		JPanel flowReg = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton reg = new JButton("Registruj se");
		reg.addActionListener((ae)->{
			int suma = 0;
			try {
				suma = Integer.parseInt(moneyField.getText());
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, "NEISPRAVNO UNET NOVAC");
				return;
			}
			if (!(new String(passwordConfirm.getPassword()).equals(new String(passwordField.getPassword())))) {
				JOptionPane.showMessageDialog(null, "SIFRE SE NE POKLAPAJU");
				return;
			}
			String s = SQLParser.registracije(userNameField.getText(), new String(passwordField.getPassword()), suma);
			if (s.length() < 1) {
				JOptionPane.showMessageDialog(null, "Registrovan");
				okvir.krajReg();
				return;
			}
			JOptionPane.showMessageDialog(null, s);
		});
		flowReg.add(reg);
		horFlow.add(flowReg);
		this.add(horFlow);
		
		JPanel flowCancel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton cancel = new JButton("Odustani");
		cancel.addActionListener((ae)->{
			okvir.krajReg();
		});
		flowCancel.add(cancel);
		horFlow.add(flowCancel);
		
		this.add(horFlow);
	}
	
}
