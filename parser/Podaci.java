package parser;

import java.util.ArrayList;

public class Podaci {
	
	String valuta;
    String simbol;
    String exchangeName;
    String vremenskaZona;
    
    ArrayList<Sveca> niz;
    static {
    	System.loadLibrary("libcurl-x64");
    	System.loadLibrary("parser_Podaci");
    }
    public Podaci(){
    	niz = new ArrayList<Sveca>();
    }
    private void dodaj(Sveca s) {
    	niz.add(s);
    }
    public ArrayList<Sveca> getSvece(){
    	return this.niz;
    }
    public String getValuta() {
    	return this.valuta;
    }
    public String getSimbol() {
    	return this.simbol;
    }
    public String getExchangeName() {
    	return this.exchangeName;
    }
    public String getVremenskaZona() {
    	return this.vremenskaZona;
    }

    public native void parse(String simbol,
    			String od_datum, String do_datum, int period);
    

    

}
