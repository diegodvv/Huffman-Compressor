package descompactador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.BitSet;

import classes.Arvre;
import classes.Arvre.Node;
import classes.Codigo;
import classes.Informacao;

public class Descompactador {
	PrintWriter arquivo_novo;
	RandomAccessFile arquivo_velho;
	
	public Descompactador (String nome_arquivo_velho, String nome_arquivo_novo) throws IllegalArgumentException, IOException {
		if (nome_arquivo_velho == null || nome_arquivo_novo == null)
			throw new IllegalArgumentException("Argumento nulo");
		
		this.arquivo_novo = new PrintWriter(new FileOutputStream(nome_arquivo_novo));
		arquivo_velho = new RandomAccessFile(nome_arquivo_velho, "r");
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
			
			d.descompactar();
			
			d = null;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void descompactar() throws IOException {
		/*int[] freq = this.contarFreq();
		
		Arvre<Informacao>[] vetArvre = new Arvre[256];
		
		for (int i = 0; i <= freq.length-1 ; i++) {
			if (freq[i] > 0) {
				int chr = i + 1;
				
				int i2 = 0;
				while (vetArvre[i2] != null) {
					i2++;
				}
				
				vetArvre[i2] = new Arvre<Informacao>(new Informacao(chr, freq[i]));
			}
		}
		
		int lengthReal = 0;
		while (vetArvre[lengthReal] != null) {
			lengthReal++;
		}
		
		vetArvre = this.ordenarVetorDec(vetArvre);
		for (int i = lengthReal-1; i >= 1 ; i--) {
			if (i >= 1) {
				int soma_freq = vetArvre[i-1].getInfoRaiz().getFreq() + vetArvre[i].getInfoRaiz().getFreq();
				vetArvre[i-1] = vetArvre[i-1].juntar(vetArvre[i], new Informacao(soma_freq));
				vetArvre[i] = null;
			}
			
			vetArvre = this.ordenarVetorDec(vetArvre);
		}
		
		Arvre<Informacao> arvre = vetArvre[0];
		vetArvre = null;
		
		Codigo[] vet_cod = new Codigo[256];
		arvre.atribuirCodigos(vet_cod);
		
		this.escreverArquivoNovo(vet_cod);*/
		
		
	}
	
	public void lerArquivoVelho(Codigo[] vet_cod) throws IOException {
		RandomAccessFile arquivo_novo = new RandomAccessFile(this.nome_arquivo_novo, "rw");
		
		int i = 0;
		int[] texto_em_cod = new int[this.texto.toCharArray().length];
		for (char c : this.texto.toCharArray()) {
			texto_em_cod[i] = vet_cod[c-1].getCod();
			i++;
		}
		
		
		//qtd_diferentes_chr
		int qtd_diferentes_chr = 0;
		for (Codigo cod : vet_cod) {
			if (cod != null)
				qtd_diferentes_chr++;
		}
		
		//tabela_chr_cod
		int[][] tabela_chr_cod = new int [qtd_diferentes_chr][2];
		for(int[] vi : tabela_chr_cod) {
			vi[0] = -1;
			vi[1] = -1;
		}
		
		int index = 0;
		for (Codigo cod : vet_cod) {
			if (cod != null) {
				int i2 = 0;
				while (tabela_chr_cod[i2][0] != -1 && tabela_chr_cod[i2][1] != -1) {
					i2++;
				}
				tabela_chr_cod[i2][0] = index+1;
				tabela_chr_cod[i2][1] = cod.getCod();
			}
			index++;
		}
		
		//qtd_bytes_lixo
		String texto_em_cod_str = "";
		for (char c : this.texto.toCharArray()) {
			texto_em_cod_str += Integer.toBinaryString(vet_cod[c-1].getCod());
		}
		
		BitSet bs = new BitSet(texto_em_cod_str.length());
		index = 0;
		for (char c : texto_em_cod_str.toCharArray()) {
			if (c == '1')
				bs.set(index);
			index++;
		}
		
		int qtd_bytes_lixo = 8 - Math.floorDiv(texto_em_cod_str.length(), 8);
		if (qtd_bytes_lixo == 8)
			qtd_bytes_lixo = 0;
		
		//Escreve o arquivo
		
		//Escreve o cabeçalho
		
		//Escreve a qtd_bytes_lixo
		//arquivo_novo.write(intToByteArray(qtd_bytes_lixo), 0, 4);
		arquivo_novo.writeInt(qtd_bytes_lixo);
		
		//Escreve a qtd_diferentes_chr
		//arquivo_novo.write(intToByteArray(qtd_diferentes_chr), 0, 4);
		arquivo_novo.writeInt(qtd_diferentes_chr);
		
		//Escreve a tabela_chr_cod
		for (int[] chr_cod : tabela_chr_cod) {
			/*arquivo_novo.write(intToByteArray(chr_cod[0]), 0, 4);
			arquivo_novo.write(intToByteArray(chr_cod[1]), 0, 4);*/
			arquivo_novo.writeInt(chr_cod[0]);
			arquivo_novo.writeInt(chr_cod[1]);
		}
		
		//Escreve o texto
		arquivo_novo.write(bs.toByteArray());
		
		arquivo_novo.close();
	}
	
	public int[] contarFreq() {
		int[] freq = new int[256];
		
		for (char c : this.texto.toCharArray()) {
			freq[c-1] += 1;
		}
		
		return freq;
	}
	
	public Arvre<Informacao>[] ordenarVetorDec (Arvre<Informacao>[] vet){
		Arvre<Informacao>[] ret = new Arvre[256];
		
		int lengthReal = 0;
		while (vet[lengthReal] != null) {
			lengthReal++;
		}
		
		//Percorre o vetor inteiro
		for (int i = 0; i <= lengthReal-1; i++) {
			//Encontra a árvore com a maior frequência na raiz
			int index_maior = 0;
			for(int i2 = 0; i2 <= lengthReal-1; i2++) {
				if (vet[i2] != null)
					if (vet[index_maior] != null) {
						if (vet[i2].getInfoRaiz().getFreq() > vet[index_maior].getInfoRaiz().getFreq())
							index_maior = i2;
					}
					else
						index_maior = i2;
			}
			ret[i] = vet[index_maior];
			vet[index_maior] = null;
		}
		
		return ret;
		
	}
}
