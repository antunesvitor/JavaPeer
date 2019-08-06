package main;

import java.util.ArrayList;

import peer.Mensagem;
import peer.Peer;

public class Main {

	public static void main(String[] args) {
		
		  String nome = args[0]; 
		  String porta = args[1]; 
		  String IPPeer = args[2];
		  String caminho = args[3]; 
		  String[] vizinhos= args[4].split(",");
		 
		  Peer peer = new Peer(nome, IPPeer, porta, vizinhos,caminho);
		  
		  peer.start();
		  
		 
	}

}
