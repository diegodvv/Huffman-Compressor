package classes;

public class Codigo implements Cloneable {
	//int cod;
	String cod = "";
	
	public String getCod() {
		return cod;
	}
	
	public void mais(int bit) throws IllegalArgumentException{
		if (bit < 0 || bit > 1)
			throw new IllegalArgumentException("Bit inválido");
		
		this.cod += bit;//(this.cod << 1) | bit;
	}
	
	public void removerUltimo(){
		if (this.cod.length() >= 2)
			this.cod = this.cod.substring(0, this.cod.length()-2);//(this.cod >> 1);
		else
			this.cod = "";
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
		return "Codigo [cod=" + cod + "]";
	}
	
	public Object clone() {
		Codigo ret = new Codigo();
		ret.cod = this.cod;
		
		return ret;
	}
}
