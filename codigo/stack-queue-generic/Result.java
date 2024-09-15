
// Usado no retorno dos métodos push(), pop() e top() da classe Stack.
public class Result<T> {

	// errorCode = 0 significa "sem erros".
	private int errorCode;
	
	// value deve ser ignorado se errorCode != 0.
	private T value;
	
	public Result(int errorCode, T value) {
		this.errorCode = errorCode;
		this.value = value;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public T getValue() {
		return value;
	}
}
