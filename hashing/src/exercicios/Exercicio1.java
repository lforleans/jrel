package exercicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import basic.Conversor;

public class Exercicio1 {

	private static final String ARQUIVO_DADOS = "dados" + File.separator +"temp.data"; 
	private String conteudo = "abced2";
		
	
	public void escreverArquivo(){
		try(FileOutputStream file = new FileOutputStream(ARQUIVO_DADOS)){
			
			byte[] conteudoEmBytes = Conversor.converteStringEmByteArray(conteudo);
			file.write(conteudoEmBytes);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void lerArquivo(){
		try(FileInputStream arquivo = new FileInputStream(ARQUIVO_DADOS)){
			byte[] buffer = new byte[1];
			int bytesLidos = 0;
			while(arquivo.read(buffer) != -1){
				bytesLidos++;
			}
			System.out.println("Bytes lidos: "+bytesLidos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Exercicio1 exercicio1 = new Exercicio1();
		exercicio1.escreverArquivo();
		exercicio1.lerArquivo();
	}
	

	
}
