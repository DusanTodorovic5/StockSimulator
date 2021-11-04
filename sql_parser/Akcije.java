package sql_parser;

import java.util.ArrayList;

public class Akcije {
	
	ArrayList<Vrednosti> akcije;
	int id;
	Akcije(int id){
		akcije = new ArrayList<Vrednosti>();
		this.id = id;
	}
	
	void dodaj(Vrednosti v) {
		akcije.add(v);
	}

	public ArrayList<Vrednosti> getAkcije() {
		return akcije;
	}
	
	private native void azuriraj(int id);
	
	public void azuriraj() {
		akcije.clear();
		azuriraj(id);
	}
}
