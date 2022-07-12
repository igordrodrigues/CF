package projeto.CF.exception;

public class ErroVinculoDetectado extends RuntimeException{

	public ErroVinculoDetectado (String msg) {
		super(msg+"\nVerifique a possibilidade de apenas desativar o registro!");
	}

}
