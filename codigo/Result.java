
 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707	    //
 /////////////////////////////////////////////

// Usado no retorno dos métodos push(), pop() e top() da classe Stack.
public class Result {

	// errorCode = 0 significa "sem erros".
	private int errorCode;
	
	// value deve ser ignorado se errorCode != 0.
	private int value;
	
	public Result(int errorCode, int value) {
		this.errorCode = errorCode;
		this.value = value;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public int getValue() {
		return value;
	}
}
