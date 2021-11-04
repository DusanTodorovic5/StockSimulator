package sql_parser;

import java.util.ArrayList;

public class Transakcije {
	ArrayList<Transakcija> transakcije;
	int id;
	Transakcije(int id){
		transakcije = new ArrayList<Transakcija>();
		this.id = id;
	}
	
	void dodaj(Transakcija t) {
		transakcije.add(t);
	}

	public ArrayList<Transakcija> getTransakcije() {
		return transakcije;
	}
	
	public native void azuriraj(int id);
	
	public void azuriraj() {
		transakcije.clear();
		azuriraj(id);
	}
}
