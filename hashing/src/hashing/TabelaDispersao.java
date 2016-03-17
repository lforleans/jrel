package hashing;

public class TabelaDispersao<E> {
	
	private E [] elementos;
	
	public void adicionar(E elemento){
		int indice = elemento.hashCode() % elementos.length;
		elementos[indice] = elemento;
	}

}
