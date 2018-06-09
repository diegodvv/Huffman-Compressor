package classes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import classes.Arvre.Node;

public class Arvre<X> implements Cloneable {
	private Node raiz;
	
	private Arvre(Node raiz) {
		this.raiz = raiz;
	}
	
	public Arvre(X info) throws IllegalArgumentException{
		if (info == null)
			throw new IllegalArgumentException("Argumento nulo");
		
		this.raiz = new Node (info);
	}
	
	public Arvre<X> juntar (Arvre<X> other, X info_raiz) throws IllegalArgumentException{
		if (other == null || info_raiz == null)
			throw new IllegalArgumentException("Argumento nulo");
		
		Node raiz = new Node(info_raiz, this.raiz.clone(), other.raiz.clone());

		return new Arvre<X>(raiz);
	}
	
	/*public void addLeft (X info) {
		Node n = raiz;
		
		while (n.getLeft() != null) {
			n = n.getLeft();
		}
		
		n.setLeft(new Node(info));
	}
	
	public void addRight (X info) {
		Node n = raiz;
		
		while (n.getRight() != null) {
			n = n.getRight();
		}
		
		n.setRight(new Node(info));
	}
	
	public Arvre<X> clone() {
		return new Arvre<X>(this);
	}
	
	private Arvre (Arvre<X> a){
		this.raiz = a.raiz.clone();
	}*/
	
	public X getInfoRaiz() {
		return raiz.info;
	}
	
	public void atribuirCodigos(Codigo[] vet_cod) throws IllegalArgumentException{
		if (vet_cod == null)
			throw new IllegalArgumentException("Argumento nulo");
		
		this.atribuirCodigos(this.raiz, new Codigo(), vet_cod);
	}
	
	private void atribuirCodigos(Node raiz, Codigo c, Codigo[] vet_cod) {
		Informacao info = (Informacao)raiz.getInfo();
		
		if (info.getChr() != null) {
			vet_cod[info.getChr()-1] = (Codigo)c.clone();
		}
		else {
			c.mais(0);
			atribuirCodigos(raiz.getLeft(), c, vet_cod);
			c.removerUltimo();
			c.mais(1);
			atribuirCodigos(raiz.getRight(), c, vet_cod);
			c.removerUltimo();
		}
	}
	
	@Override
	public String toString() {
		return "Arvre " + raiz;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raiz == null) ? 0 : raiz.hashCode());
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
		Arvre other = (Arvre) obj;
		if (raiz == null) {
			if (other.raiz != null)
				return false;
		} else if (!raiz.equals(other.raiz))
			return false;
		return true;
	}

	public class Node implements Cloneable{
		private X info;
		private Node left;
		private Node right;
		
		public Node(X info) throws IllegalArgumentException{
			this(info, null, null);
		}
		

		public Node(X info, Node left, Node right) throws IllegalArgumentException{
			if (info == null)
				throw new IllegalArgumentException("Null item cannot be added.");
			
			if (info instanceof Cloneable)
				info = cloneOfX(info);
			
			this.info = info;
			this.left = left;
			this.right = right;
		}
		
		public X getInfo() {
			return info;
		}
		
		public void setInfo(X info) {
			this.info = info;
		}
		
		public Node getLeft() {
			return left;
		}


		public void setLeft(Node left) {
			this.left = left;
		}


		public Node getRight() {
			return right;
		}


		public void setRight(Node right) {
			this.right = right;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((info == null) ? 0 : info.hashCode());
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			result = prime * result + ((right == null) ? 0 : right.hashCode());
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
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (info == null) {
				if (other.info != null)
					return false;
			} else if (!info.equals(other.info))
				return false;
			if (left == null) {
				if (other.left != null)
					return false;
			} else if (!left.equals(other.left))
				return false;
			if (right == null) {
				if (other.right != null)
					return false;
			} else if (!right.equals(other.right))
				return false;
			return true;
		}


		protected X cloneOfX (X x)
	    {
	        X ret = null;

	        try
	        {
	            Class<?> classe = x.getClass();
	            Class<?>[] tipoDoParametroFormal = null; // pq clone tem 0 parametros
	            Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
	            Object[] parametroReal = null;// pq clone tem 0 parametros
	            ret = ((X)metodo.invoke (x, parametroReal));
	        }
	        catch (NoSuchMethodException erro)
	        {}
	        catch (InvocationTargetException erro)
	        {}
	        catch (IllegalAccessException erro)
	        {}

	        return ret;
	    }
		
		public Node clone() {
			Node ret;
			X info;
			
			if (this.info instanceof Cloneable)
				info = cloneOfX(this.info);
			else
				info = this.info;
			
			ret = new Node(info, (this.left == null ? null : this.left.clone()), (this.right == null ? null : this.right.clone()));
			return ret;
		}

		private Arvre getOuterType() {
			return Arvre.this;
		}

		@Override
		public String toString() {
			return "(" + (this.left == null ? "" : this.left) + info + (this.right == null ? "" : this.right) + ")";
		}
	}
}
