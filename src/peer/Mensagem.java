package peer;

import java.util.Objects;

public class Mensagem {
	private String ip;
	private int porta;
	private String nomeArquivo;
	
	public Mensagem(String ip, int porta, String nomeArquivo) {
		this.ip = ip;
		this.porta = porta;
		this.nomeArquivo = nomeArquivo;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		
		if(!(o instanceof Mensagem))
			return false;
		
		Mensagem msg = (Mensagem) o;
		return porta == msg.porta && 
				Objects.equals(ip, msg.ip) &&
				Objects.equals(nomeArquivo, msg.nomeArquivo);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ip, porta, nomeArquivo);
	}
}
