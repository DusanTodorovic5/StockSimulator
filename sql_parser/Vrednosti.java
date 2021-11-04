package sql_parser;

import java.util.Comparator;

public class Vrednosti {
	int idAkcije;
	String simbol;
	String kolicina;
	String cena;
	public int getIdAkcije() {
		return idAkcije;
	}
	public String getSimbol() {
		return simbol;
	}
	public String getKolicina() {
		return kolicina;
	}
	public String getCena() {
		return cena;
	}
	public static Comparator<Vrednosti> idRastuce = new Comparator<Vrednosti>() {
	    @Override
	    public int compare(Vrednosti v1, Vrednosti v2) {
	        return Integer.compare(v1.getIdAkcije(),v2.getIdAkcije());
	    }
	};
	public static Comparator<Vrednosti> idOpadajuce = new Comparator<Vrednosti>() {
	    @Override
	    public int compare(Vrednosti v1, Vrednosti v2) {
	        return Integer.compare(v2.getIdAkcije(),v1.getIdAkcije());
	    }
	};
	public static Comparator<Vrednosti> cenaRastuce = new Comparator<Vrednosti>() {
	    @Override
	    public int compare(Vrednosti v1, Vrednosti v2) {
	    	return Double.compare(Double.parseDouble(v1.getCena()),Double.parseDouble(v2.getCena()));
	    }
	};
	public static Comparator<Vrednosti> cenaOpadajuce = new Comparator<Vrednosti>() {
	    @Override
	    public int compare(Vrednosti v1, Vrednosti v2) {
	        return Double.compare(Double.parseDouble(v2.getCena()),Double.parseDouble(v1.getCena()));
	    }
	};
	public static Comparator<Vrednosti> kolRastuce = new Comparator<Vrednosti>() {
	    @Override
	    public int compare(Vrednosti v1, Vrednosti v2) {
	    	return Double.compare(Double.parseDouble(v1.getKolicina()),Double.parseDouble(v2.getKolicina()));
	    }
	};
	public static Comparator<Vrednosti> kolOpadajuce = new Comparator<Vrednosti>() {
	    @Override
	    public int compare(Vrednosti v1, Vrednosti v2) {
	        return Integer.compare(Integer.parseInt(v2.getKolicina()),Integer.parseInt(v1.getKolicina()));
	    }
	};
	
}
