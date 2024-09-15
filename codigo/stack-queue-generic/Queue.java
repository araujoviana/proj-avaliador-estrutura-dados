
public class Queue<T> {
	
	private static final int DEFAULT_SIZE = 10;
	
	private T data[];
	private int count;
	private int first;
	private int last;
	
	public Queue() {
		this(DEFAULT_SIZE);
	}
	
	@SuppressWarnings("unchecked")
	public Queue(int size) {
		data = (T[])new Object[size];
		count = 0;
		first = 0;
		
		// last começando com zero indica que last
		// é a posição correta para um novo elemento.
		last = 0;
	}
	
	// enqueue(value): inserção
	public void enqueue(T value) {
		if (isFull()) {
			throw new RuntimeException("enqueue(): fila cheia.");
		}
		
		data[last] = value;

//		++last;
//		if (last == data.length) {
//			last = 0;
//		}

		// Código abaixo é igual ao incremento + if acima.
		last = (last + 1) % data.length;
		
		++count;
	}
	
	// dequeue(): remoção (neste exemplo, incluir retorno do valor removido).
	public T dequeue() {
		if (isEmpty()) {
			throw new RuntimeException("dequeue(): fila vazia.");
		}
		
		T value = data[first];
		data[first] = null; // se fosse objeto, atribuiria null.
		
//		++first;
//		if (first == data.length) {
//			first = 0;
//		}
		
		// Código abaixo é igual ao incremento + if acima.
		first = (first + 1) % data.length;
		
		--count;
		
		return value;
	}
	
	// front(): consulta
	public T front() {
		if (isEmpty()) {
			throw new RuntimeException("front(): fila vazia.");
		}
		
		return data[first];
	}
	
	public boolean isEmpty() {
		return count == 0;
	}
	
	public boolean isFull() {
		return count == data.length;
	}
	
	public int size() {
		return data.length;
	}
	
	public int count() {
		return count;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("data = { ");
		for (int i = 0; i < data.length - 1; ++i) {
			sb.append(data[i]).append(", ");
		}
		sb.append(data[data.length - 1]);
		sb.append(" }, count = " + count
				+ ", first = " + first
				+ ", last = " + last);
		return sb.toString();
	}

}







