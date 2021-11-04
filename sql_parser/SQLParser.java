package sql_parser;


public class SQLParser {
	Transakcije transakcije;
	Akcije akcije;
	int id;
	String userName;


	public SQLParser(int id,String user){
		transakcije = new Transakcije(id);
		akcije = new Akcije(id);
		this.userName = user;
		this.id = id;
	}

	static {
		System.loadLibrary("ParserSQL");
	}
	public native int kupi(int id,String simbol,int kol,double cena);
	
	public int kupi(String simbol,int kol,double cena) {
		return kupi(this.id,simbol,kol,cena);
	}
	
	public native int prodaj(int id,int id_kup,double cena,int kol);
	
	public int prodaj(int id_kup,double cena,int kol) {
		return prodaj(this.id, id_kup, cena, kol);
	}
	
	public native String simbol(int s);
	
	public static native int loginPass(String userName, String pass);
	
	public static native String registracije(String userName,String pass,int novac);
	
	public native double novac(int id);
	
	public double novac() {
		return novac(id);
	}
	public String getUserName() {
		return userName;
	}
	public Transakcije getTransakcije() {
		return transakcije;
	}

	public Akcije getAkcije() {
		return akcije;
	}
}
