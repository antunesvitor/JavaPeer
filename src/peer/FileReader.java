package peer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileReader extends Thread{
	private String caminho;
	private Peer peer;
	
	private long pausa;

	public FileReader(Peer peer, long pausa) {
		this.caminho = peer.caminho;
		this.peer = peer;
		this.pausa = pausa;
	}
	
	@Override
	public void run() {
		File pasta = new File(caminho);
		File[] listaDeArquivos = pasta.listFiles();
		try 
		{
			synchronized(this.peer.metadadosLocais) 
			{
				for(int i = 0;i < listaDeArquivos.length;i++) 
				{
					BasicFileAttributes attr;
					Path file = FileSystems.getDefault().getPath(listaDeArquivos[i]+"");
					attr = Files.readAttributes(file, BasicFileAttributes.class);
					
					Metadata metadado = new Metadata(attr, listaDeArquivos[i].getName(), this.peer.nome);
					
					this.peer.metadadosLocais.add(metadado);
					Thread.sleep(pausa);
				}
			}
		}
		catch(IOException ex) 
		{
			ex.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
