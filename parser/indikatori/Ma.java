package parser.indikatori;

import java.util.ArrayList;

import parser.Sveca;

public class Ma implements Indikator {

	@Override
	public ArrayList<Double> izracunaj(ArrayList<Sveca> sveca, int n, Vrsta vrsta) {
		ArrayList<Double> ma = new ArrayList<Double>();
		for (int i=0;i<n;i++) {
			if (i < sveca.size())
				ma.add(sveca.get(i).getVrsta(vrsta));
			else
				return ma;
		}
		for (int i=n;i<sveca.size();i++) {
			double s = 0;
			for (int j=0;j<n;j++) {
				s += sveca.get(i-j-1).getVrsta(vrsta);
			}
			ma.add(s/(double)n);
		}
		return ma;
	}

}
