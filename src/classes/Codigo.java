package classes;

public class Codigo implements Cloneable {
	int cod;
	
	public int getCod() {
		return cod;
	}
	
	public void mais(int bit) throws IllegalArgumentException{
		if (bit < 0 || bit > 1)
			throw new IllegalArgumentException("Bit inválido");
		
		this.cod = (this.cod << 1) | bit;
	}
	
	public void removerUltimo(){
		this.cod = (this.cod >> 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + new Integer(cod).hashCode();
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
		Codigo other = (Codigo) obj;
		if (cod != other.cod)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Codigo [cod=" + Integer.toBinaryString(cod) + "]";
	}
	
	public Object clone() {
		Codigo ret = new Codigo();
		ret.cod = this.cod;
		
		return ret;
	}
}
