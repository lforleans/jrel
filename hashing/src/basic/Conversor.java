package basic;

import java.nio.ByteBuffer;

public class Conversor {
	
	public static byte[] converteStringEmByteArray(String valor) {
		char[] conteudoEmChar = valor.toCharArray();
		byte[] conteudoEmBytes = new byte[2 * conteudoEmChar.length];
		
		for (int i = 0; i < conteudoEmChar.length; i++) {
			int posicao = i * 2;
			conteudoEmBytes[posicao] = (byte) ((conteudoEmChar[i]&0xFF00)>>8);
			conteudoEmBytes[posicao+1] = (byte) ((conteudoEmChar[i]&0x00FF));
		}
		return conteudoEmBytes;
	}
	
	
	public static String converteByteArrayEmString(byte[] array){
		char[] buffer = new char[array.length >> 1];
        for(int i = 0; i < buffer.length; i++) {
        	int posicao = i * 2;
        	char c = (char)(((array[posicao]&0x00FF)<<8) + (array[posicao+1]&0x00FF));
        	buffer[i] = c;
        }
        return new String(buffer);
	}
	
	public static byte[] converteIntEmByteArray(int valor){
		return ByteBuffer.allocate(4).putInt(valor).array();
	}
	
	public static int converteByteArrayEmInt(byte[] array){
		return ByteBuffer.wrap(array).getInt();
	}
	
	public static byte[] converteFloatEmByteArray(float valor){
		return ByteBuffer.allocate(4).putFloat(valor).array();
	}
	
	public static float converteByteArrayEmFloat(byte[] array){
		return ByteBuffer.wrap(array).getFloat();
	}	
	
	
	public static void main(String[] args) {
		byte[] array = Conversor.converteStringEmByteArray("TESTE");
		System.out.println(Conversor.converteByteArrayEmString(array));
	}
	
}
