package peer;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
	private Peer peer;
	
	public TCPClient(Peer peer) {
		this.peer = peer;
	}
	
	public void enviarArquivo(String file, String host, int portaTCP) {
		try {
			String caminhoCompleto = peer.caminho + "\\" + file;
			Socket socket = new Socket(host,portaTCP);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			FileInputStream fis = new FileInputStream(caminhoCompleto);
			byte[] buffer = new byte[4096];
			
			while (fis.read(buffer) > 0) {
				dos.write(buffer);
			}
			fis.close();
			dos.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}

}
