import java.util.*;

public class Aviao {
	
	private List listaCadeira = new ArrayList(); 
	
	private int localizaCadeira(int numero){
		return -1;
	}
	
	public Cadeira getCadeira(int numero){
		return (Cadeira) this.listaCadeira.get(this.localizaCadeira(numero));
	}
	
	private boolean adicionaCadeira(Cadeira cadeira_){
		if (localizaCadeira(cadeira_.getNumero())==-1){
			this.listaCadeira.add(cadeira_);
			return true;
		}else {
			return false;
		}
	}
	
	public void  criaListaCadeiras(int quantidade){
		this.listaCadeira.clear();
		for (int i=1;i<quantidade+1;i++){
			Cadeira c = new Cadeira();
			c.setNumero(i);
			c.setFumante(((i%2==0)?true:false));
			c.setOcupada(false);
			this.adicionaCadeira(c);
    	}
	}
	
	public boolean setCadeiraParaOcupada(int numero){
		return true;
	}
	
	public ArrayList getListaCadeira(){
		return (ArrayList) this.listaCadeira;
	}
	
	public String toString(){
		return this.listaCadeira.toString();
	}
	
	public String listaCadeirasLivresFumanteNao(){
		ArrayList tempLista = new ArrayList();
		Iterator it = this.listaCadeira.iterator();
		while (it.hasNext()){
			Cadeira c = (Cadeira) it.next();
			if ( (!c.getOcupada()) && (!c.getFumante())){
				tempLista.add(c);
			}
		}
		return tempLista.toString();
	}
	
	public static void main(String args[]){	
		Aviao a = new Aviao();
		a.criaListaCadeiras(50);
		System.out.println(a);
		a.setCadeiraParaOcupada(33);
		a.setCadeiraParaOcupada(45);
		System.out.println(a.listaCadeirasLivresFumanteNao());
		System.out.println(a.getCadeira(45));
	}
}