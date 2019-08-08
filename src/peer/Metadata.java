package peer;

import java.nio.file.attribute.BasicFileAttributes;

public class Metadata {
	
	private String nomeArquivo;
	public long tamanhoArquivo;
	public String peerDono;
	
	
	public Metadata(BasicFileAttributes atributos, String nomeArquivo, String peerDono) {
		this.tamanhoArquivo = atributos.size();
		System.out.println("TAMANHO DO ARQUIVO " + nomeArquivo + ": "+atributos.size());
		this.nomeArquivo = nomeArquivo; 
		this.peerDono = peerDono;
	}
	
	public Metadata(String msg) {
		String[] msgArray = msg.split(",");
		this.peerDono = msgArray[0];
	}
	
	@Override
	public String toString() {
		return this.nomeArquivo;
	}
	
	//Metodo que compara metadata por ID
//	@Override
//	public boolean equals(Object obj) {
//		
//		if(obj == null) return false;
//		if(!Metadata.class.isAssignableFrom(obj.getClass())) return false;
//	  
//		final Metadata outros = (Metadata) obj; 
//		if(this.metadataId != outros.metadataId) 
//			return false;
//		 
//		return true;		
//	}
	
//	@Override
//	public int hashCode() {
//		return (int) metadataId;
//	}
}
