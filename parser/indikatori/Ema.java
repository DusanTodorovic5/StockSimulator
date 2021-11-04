package parser.indikatori;

import java.util.ArrayList;

import parser.Sveca;

public class Ema implements Indikator {

	@Override
	public ArrayList<Double> izracunaj(ArrayList<Sveca> sveca, int n, Vrsta vrsta) {
		ArrayList<Double> ema = new ArrayList<Double>();
		ema.add(sveca.get(0).getVrsta(vrsta));
		for (int i=1;i<sveca.size();i++) {
			double s = sveca.get(i).getVrsta(vrsta) * (2 / ((double)n+1)) + ema.get(i-1) * (1 - 2 / ((double)n + 1));
			ema.add(s);
		}
		return ema;
	}

}
