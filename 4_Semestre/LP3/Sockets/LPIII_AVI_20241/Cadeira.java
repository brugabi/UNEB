
public class Cadeira {
	private boolean fumante;
	private int numero;
	private boolean acupada;
	
	public boolean getFumante(){
		return this.fumante;
	}
	
	public int getNumero(){
		return this.numero;	
	}
	
	public boolean getOcupada(){
		return this.acupada;
	}
	
	public void setFumante(boolean fumante_){
		this.fumante = fumante_;
	}
	
	public void setNumero(int numero_){	
		this.numero = numero_;
	}
	
	public void setOcupada(boolean ocupada_){
		 this.acupada = ocupada_;
	}
	
	public boolean equals(Object o){
		Cadeira c = (Cadeira) o;
		return  ((this.numero == c.getNumero())?true:false);
	}
	
	public String toString(){
		return "Numero=" + this.numero+" fumante="+this.fumante+ " Ocupada="+this.acupada+ "\n";	
	}
}
