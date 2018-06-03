package descompactador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Descompactador {
	PrintWriter arquivo_novo;
	String nome_arquivo_velho;
	
	public Descompactador (String nome_arquivo_velho, String nome_arquivo_novo) throws IllegalArgumentException, IOException {
		if (nome_arquivo_velho == null || nome_arquivo_novo == null)
			throw new IllegalArgumentException("Argumento nulo");
		
		this.arquivo_novo = new PrintWriter(new FileOutputStream(nome_arquivo_novo));
		this.nome_arquivo_velho = nome_arquivo_velho;
	}
	
	public static void p(Object s) {
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			p("Caminho para o arquivo a ser lido:");
			BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
			String nome_arquivo_velho = t.readLine();
			
			p("Caminho para o arquivo a ser escrito:");
			String nome_arquivo_novo = t.readLine();
			//String texto = "o\nescencial\ninvisivel\naos\nolhos\n";
			
			Descompactador d = new Descompactador(nome_arquivo_velho, nome_arquivo_novo);
			
			String texto_descompactado = d.descompactar();
			
			d = null;
			
			p(texto_descompactado);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public String descompactar() throws IOException {
		RandomAccessFile arquivo_velho = new RandomAccessFile(nome_arquivo_velho, "r");
		
		//Lê tamanho do arquivo
		long tamanho_arquivo = new File(nome_arquivo_velho).length();
		
		//Lê o arquivo
		//==============
		//Lê o cabeçalho
		
		//Lê a qtd_bytes_lixo
		int qtd_bytes_lixo = arquivo_velho.readInt();
		
		//Lê a qtd_diferentes_chr
		int qtd_diferentes_chr = arquivo_velho.readInt();
		
		//Lê a tabela_chr_cod
		Object[][] tabela_chr_cod = new Object [qtd_diferentes_chr][4];
		
		int tamanho_tabela_chr_cod = 0;
		for (Object[] chr_cod : tabela_chr_cod) {
			/*arquivo_novo.write(intToByteArray(chr_cod[0]), 0, 4);
			arquivo_novo.write(intToByteArray(chr_cod[1]), 0, 4);*/
			chr_cod[0] = arquivo_velho.readChar(); 								//char correspondente
			chr_cod[1] = arquivo_velho.readInt();  								//Tamanho do cod
			chr_cod[2] = arquivo_velho.readInt();  								//Tamanho em bytes do cod
			chr_cod[3] = new byte[(int) chr_cod[2]];
			arquivo_velho.read((byte[]) chr_cod[3]);							//Bits do cod
			String cod = "";
			for (byte b : (byte[]) chr_cod[3]) {
				cod += Integer.toBinaryString(b);
			}
			cod = cod.substring(cod.length() - (int)chr_cod[2], cod.length()-1);
			chr_cod[3] = cod;
			tamanho_tabela_chr_cod += 2 + 4 + 4 + (int)chr_cod[2];
		}
		
		//Lê o texto
		//								       bts_lx qt_d           tab_chr_cod
		int tamanho_texto = (int) tamanho_arquivo -4   -4   -tamanho_tabela_chr_cod;
		
		byte[] texto_em_bytes = new byte[tamanho_texto];
		arquivo_velho.readFully(texto_em_bytes);
		
		arquivo_velho.close();
		//Termina de ler o arquivo
		//=========================
		
		//Converter texto
		//================
		String texto_em_bits_str = "";
		for (byte b : texto_em_bytes) {
			texto_em_bits_str += Integer.toBinaryString(b);
		}
		
		//Tira os bits de lixo
		texto_em_bits_str = texto_em_bits_str.substring(qtd_bytes_lixo, texto_em_bits_str.length());
		
		String texto_convertido = "";
		
		while (!texto_em_bits_str.isEmpty()) {
			for (int i = 0; i <= tabela_chr_cod.length-1; i++) {
				//Tenta-se encontrar um dos codigos no início do texto_em_bits_str
				if (texto_em_bits_str.indexOf((String)tabela_chr_cod[i][3]) == 0) {
					texto_convertido += (char)tabela_chr_cod[i][0];
					
					//É retirado o cod do texto_em_bits_str
					if ((int) tabela_chr_cod[i][1] >= texto_em_bits_str.length()) 
						texto_em_bits_str = ""; //Se o tamanho do cod é igual ao tamanho da string, string tornar-se vazia
					else
						texto_em_bits_str = texto_em_bits_str.substring((int) tabela_chr_cod[i][1], texto_em_bits_str.length()); //O cod é 'cortado' da string
					break;
				}
			}	
		}
		
		return texto_convertido;
		//Termina de converter o texto
		//================
	}
}
