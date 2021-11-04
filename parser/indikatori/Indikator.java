package parser.indikatori;

import java.util.ArrayList;

import parser.Sveca;

public interface Indikator {
	public enum Vrsta {HIGH,LOW,OPEN,CLOSE}
	public ArrayList<Double> izracunaj(ArrayList<Sveca> sveca, int n,Vrsta vrsta);
}
