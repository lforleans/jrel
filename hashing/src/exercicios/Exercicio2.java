package exercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import basic.Conversor;

public class Exercicio2 {
	
		
	private static final String ARQUIVO_DADOS = "dados" + File.separator +"filmes.data"; 
	
	static{
		try(FileOutputStream arquivo = new FileOutputStream(ARQUIVO_DADOS)){
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
			
	
	public long salvarFime(Filme filme){
		try(FileOutputStream arquivo = new FileOutputStream(ARQUIVO_DADOS, true)){
			byte[] codigoEmBytes = Conversor.converteIntEmByteArray(filme.codigo);
			arquivo.write(codigoEmBytes);
			
			byte[] nomeEmBytes = Conversor.converteStringEmByteArray(filme.nome);
			byte[] tamanhoNomeEmBytes = Conversor.converteIntEmByteArray(nomeEmBytes.length);
			arquivo.write(tamanhoNomeEmBytes);
			arquivo.write(nomeEmBytes);
			
			byte[] notaEmBytes = Conversor.converteFloatEmByteArray(filme.nota);
			arquivo.write(notaEmBytes);
			return arquivo.getChannel().position();
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public Filme lerFilme(){
		return lerFilme(0);
	}
	
	public Filme lerFilme(long offset){
		try(FileInputStream arquivo = new FileInputStream(ARQUIVO_DADOS)){
			
			arquivo.getChannel().position(offset);
			
			Filme filme = new Filme();
			byte[] buffer = new byte[4];
			
			arquivo.read(buffer);
			filme.codigo = Conversor.converteByteArrayEmInt(buffer);
			
			arquivo.read(buffer);
			int tamanhoNome = Conversor.converteByteArrayEmInt(buffer);
			buffer = new byte[tamanhoNome];
			arquivo.read(buffer);
			filme.nome = Conversor.converteByteArrayEmString(buffer);
			
			buffer = new byte[4];
			arquivo.read(buffer);
			filme.nota = Conversor.converteByteArrayEmFloat(buffer);
			
			return filme;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Filme lerFilmeSequencial(int codigo){
		try(FileInputStream arquivo = new FileInputStream(ARQUIVO_DADOS)){
		
			Filme filme = new Filme();
			byte[] buffer;

			do{
				buffer = new byte[4];
				arquivo.read(buffer);
				filme.codigo = Conversor.converteByteArrayEmInt(buffer);
				
				arquivo.read(buffer);
				int tamanhoNome = Conversor.converteByteArrayEmInt(buffer);
				buffer = new byte[tamanhoNome];
				arquivo.read(buffer);
				filme.nome = Conversor.converteByteArrayEmString(buffer);
				
				buffer = new byte[4];
				arquivo.read(buffer);
				filme.nota = Conversor.converteByteArrayEmFloat(buffer);
			}while(filme.codigo != codigo); 
			
			return filme;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		Filme filme = new Filme();
		Random random = new Random();
		Exercicio2 exercicio2 = new Exercicio2();
		long[] posicoes = new long[1000];
		long anterior = 0;
		for (int i = 0; i < 1000; i++) {
			filme.codigo = i;
			filme.nome = "Filme "+i;
			filme.nota = random.nextFloat();
			posicoes[i] = anterior;
			anterior = exercicio2.salvarFime(filme);
		}

		int[] buscas = new int[10];
		for (int j = 0; j < buscas.length; j++) {
			buscas[j] = random.nextInt(1000);
		}
		
		System.out.println("Busca sequencial das chaves "+buscas.toString());
		long tempoInicial = System.currentTimeMillis();
		for (int i = 0; i < buscas.length; i++) {
			System.out.println("Buscando chave "+buscas[i]);
			System.out.println(exercicio2.lerFilmeSequencial(buscas[i]));
		}
		System.out.println("Tempo: "+(System.currentTimeMillis() - tempoInicial)+"ms");
		
		System.out.println("==============================");
		System.out.println("Busca aleatoria das chaves "+buscas.toString());
		 tempoInicial = System.currentTimeMillis();
		for (int i = 0; i < buscas.length; i++) {
			System.out.println("Buscando chave "+buscas[i]);
			System.out.println(exercicio2.lerFilme(posicoes[buscas[i]]));
		}
		System.out.println("Tempo: "+(System.currentTimeMillis() - tempoInicial)+"ms");
		
		
		
	}

}

class Filme{
	int codigo;
	String nome;
	float nota;
	@Override
	public String toString() {
		return "["+codigo+"]: "+nome+" ("+nota+")";
	}
}
