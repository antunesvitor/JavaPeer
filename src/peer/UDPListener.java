package peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPListener extends Thread{
	private int porta;
	private Peer peer;
	private UDPClientPeer cliente;
//	private ArrayList<String> arquivosRequisitados1;
	private ArrayList<Mensagem> mensagensRecebidas;
	
	public UDPListener(Peer peer) {
		this.peer = peer;
		this.porta = peer.porta;	
		cliente = new UDPClientPeer(peer);
//		arquivosRequisitados = new ArrayList<String>();
		mensagensRecebidas = new ArrayList<Mensagem>();
	}
	
	private boolean msgDuplicata(String IPCliente, int porta, String nomeArquivo) {
		Mensagem msgNova = new Mensagem (IPCliente, porta, nomeArquivo);
		return mensagensRecebidas.contains(msgNova);
	}
	
	@Override
	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(porta);			
			System.out.println("ouvindo...");
			byte[] dadosRecebidos;
			int count = 0;
			while(true) {
				dadosRecebidos = new byte[1024];
				
				DatagramPacket pacoteRecebido = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
				
				serverSocket.receive(pacoteRecebido);
				System.out.println(count++);
				
				String mensagemRecebida = new String(pacoteRecebido.getData());
				String[] mensagemInterpretada = mensagemRecebida.split(";");
				
				String IPCliente = mensagemInterpretada[0];
				int portaCliente = Integer.parseInt(mensagemInterpretada[1].trim());
				String nomeArquivo = mensagemInterpretada[2];
				
				// checa se a msg é duplicada
				if(msgDuplicata(IPCliente, portaCliente, nomeArquivo)) {
					System.out.printf("Console %s: recebendo pesquisa %s, MSG DUPLICADA NÃO ENCAMINHO.\n", peer.nome, nomeArquivo);
					continue;
				}
				
				// checa se o peer possui o arquivo
				if(peer.possuiArquivo(nomeArquivo)) 
				{
					System.out.printf("Console %s: recebendo pesquisa %s, tenho o arquivo %s no meu estado.\n", peer.nome, nomeArquivo, nomeArquivo);
					cliente.enviaConfirmacaoArquivo(IPCliente,portaCliente);
					mensagensRecebidas.add(new Mensagem(IPCliente, portaCliente, nomeArquivo));
				}				
				else	// se não possui pode dar inicio ao processo de encaminhamento 
				{
		
					//checaTTL
					int nivelTTL = Integer.parseInt(mensagemInterpretada[3].trim());
					
					// se o TTL for positivo então pode encaminhar
					if(nivelTTL > 0) {
						//arquivosRequisitados.add(nomeArquivo);
						
						cliente.encaminhaRequisicaoDeArquivo(IPCliente, portaCliente, nomeArquivo, nivelTTL - 1);
						
						mensagensRecebidas.add(new Mensagem(IPCliente, portaCliente, nomeArquivo));
					}
					else {
						// caso contrário não faça nada
						System.out.printf("Console %s - recebendo pesquisa %s, TTL=ZERO NÃO ENCAMINHO.\n", peer.nome, nomeArquivo);
					}
				}
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
