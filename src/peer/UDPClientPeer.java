package peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class UDPClientPeer {
	private Peer peer;
	
	public UDPClientPeer(Peer peer) 
	{
		this.peer = peer;
	}
	
	public void enviaConfirmacaoArquivo(String IPCliente, int portaCliente, Metadata arquivo) {
		
		String msg = String.format("%d",arquivo.tamanhoArquivo);
		enviarMensagem(msg, IPCliente, portaCliente);
	}
	
	public void encaminhaRequisicaoDeArquivo(String IPCliente, int portaCliente, String nomeArquivo, int TTL, int portaClienteTCP){
		
		Vizinho proximoPeer = peer.getVizinhoAleatorio();
		String msg = String.format("%s;%d;%s;%d;%d", IPCliente, portaCliente, nomeArquivo, TTL, portaClienteTCP);
		
		System.out.printf("Console %s: recebendo pesquisa %s, NAO tenho o arquivo, encaminho para %s.\n", peer.nome, nomeArquivo, proximoPeer.nome);
		
		enviarMensagem(msg, proximoPeer.IP, proximoPeer.porta);
	}
			
	private void enviarMensagem(String mensagem, String IPdestino, int portaDestino) {
		
		try {
			DatagramSocket clienteSocket = new DatagramSocket();
			InetAddress endereco = InetAddress.getByName(IPdestino);
			
			byte[] dados = new byte[1024];
			dados = mensagem.getBytes();
			
			DatagramPacket pacoteParaEnviar = new DatagramPacket(dados, dados.length, endereco, portaDestino);
			
			clienteSocket.send(pacoteParaEnviar);
			
			clienteSocket.close();
		} 
		catch (SocketException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
