package parser;

import java.util.Comparator;

import parser.indikatori.Indikator;

public class Sveca {
    double timestamp;
	double open;
    double close;
    double high;
    double low;
    public Sveca() {
    	
    }
    public Sveca(double timestamp, double open, double close, double high, double low) {
		this.timestamp = timestamp;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}
	public double getTimestamp() {
		return timestamp;
	}
	public double getOpen() {
		return open;
	}
	public double getClose() {
		return close;
	}
	public double getHigh() {
		return high;
	}
	public double getLow() {
		return low;
	}
	public double getVrsta(Indikator.Vrsta vrsta) {
		if (vrsta == Indikator.Vrsta.CLOSE) {
			return this.close;
		}
		else if (vrsta == Indikator.Vrsta.HIGH) {
			return this.high;
		}
		else if (vrsta == Indikator.Vrsta.LOW) {
			return this.low;
		}
		else
			return this.open;
	}
	public static Comparator<Sveca> max = new Comparator<Sveca>() {
	    @Override
	    public int compare(Sveca s1, Sveca s2) {
	        return Double.compare(s1.getHigh(), s2.getHigh());
	    }
	};
	public static Comparator<Sveca> min = new Comparator<Sveca>() {
	    @Override
	    public int compare(Sveca s1, Sveca s2) {
	        return Double.compare(s1.getLow(), s2.getLow());
	    }
	};
}
