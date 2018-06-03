package classes;

public class Informacao {
	Integer chr;
	int freq;
	
	public Informacao (int chr, int freq) {
		this.chr = chr;
		this.freq = freq;
	}
	
	public Informacao (int freq) {
		this.freq = freq;
	}

	public Integer getChr() {
		return chr;
	}

	public int getFreq() {
		return freq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chr;
		result = prime * result + freq;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Informacao other = (Informacao) obj;
		if (chr != other.chr)
			return false;
		if (freq != other.freq)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Informacao [chr=" + chr + ", freq=" + freq + "]";
	}
}
