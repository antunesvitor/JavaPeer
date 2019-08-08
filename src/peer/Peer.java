package peer;

import java.util.ArrayList;
import java.util.Random;

public class Peer {
	public String nome;
	public int porta;
	public String IP;
	public ArrayList<Metadata> metadadosLocais;
	private ArrayList<Vizinho> vizinhos;
	public String caminho;
	
	public Peer(String nome, String IP,String porta, String[] enderecos, String caminho) 
	{
		this.nome = nome;
		this.porta = Integer.parseInt(porta);
		this.caminho = caminho;
		this.IP = IP;
		metadadosLocais = new ArrayList<Metadata>();
		vizinhos = new ArrayList<Vizinho>();
		
		for(int i = 0; i < enderecos.length; i++) 
		{
			String nomeVizinho = enderecos[i].split(":")[0];
			String ip = enderecos[i].split(":")[1];
			int portaVizinho = Integer.parseInt(enderecos[i].split(":")[2]);
			
			Vizinho vizinho = new Vizinho(nomeVizinho, ip, portaVizinho);
			vizinhos.add(vizinho);
		}
	}
	
	public void PrintInfo() {
		System.out.printf("nome do peer: %s;\n recebe dados pela porta: %d;\n lê arquivos em: %s\n",nome, porta, caminho);
		System.out.println("Tem como vizinhos os enderecos:");
		
		for(Vizinho vizinho: vizinhos) 
		{
			System.out.println(vizinho.IP+":"+vizinho.porta);
		}
		
		System.out.println("Metadados:");
		
		for(Metadata metadados : metadadosLocais) 
		{
			System.out.println(metadados.toString());
		}
	}
	
	public Vizinho getVizinhoAleatorio() {
		Random rnd = new Random();
		int indice = rnd.nextInt(this.vizinhos.size());
		return vizinhos.get(indice);
	}
	
	public Boolean possuiArquivo(String nome) {
		for(Metadata metadados : metadadosLocais) 
		{
			if(metadados.toString().equals(nome)) 
				return true;
		}
		return false;
	}
	
	public Metadata getArquivo(String nome) {
		for(Metadata metadados : metadadosLocais) 
		{
			if(metadados.toString().equals(nome)) 
				return metadados;
		}
		return null;
	}
	
	public void start() 
	{
		FileReader fileReader = new FileReader(this, 200);
		UDPListener listen = new UDPListener(this);
		
		fileReader.start();
		listen.start();
	}
}
