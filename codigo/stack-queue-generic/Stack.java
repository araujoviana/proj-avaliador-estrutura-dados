
// Classe Stack genérica (generics, template).
// T = define o tipo de dado que a classe pode usar.
// Em todo lugar que aparece T, será substituído por algum tipo de dado.

// Observação: T não pode ser um tipo primitivo (ex. int, char, boolean, ...).
// Nesse caso, devemos usar classes que representam os tipos primitivos.
// ex. Integer, Character, Boolean.

// Criar um objeto de uma classe não-genérica:
// Stack s = new Stack();
// Criar um objeto de uma classe genérica (sendo T = Float):
// Stack<Float> s = new Stack<>();

public class Stack<T> {
	private static final int DEFAULT_SIZE = 10;
	
	private T data[];
	private int count;
	
	public Stack() {
		this(DEFAULT_SIZE);
	}
	
	@SuppressWarnings("unchecked")
	public Stack(int size) {
		data = (T[])new Object[size];
		count = 0;
	}
	
	public Result<T> push(T value) {
		if (isFull()) {
			// OBSERVAÇÃO: Evite instruções de saída (print),
			// pois nem sempre queremos ter essas saídas no programa.
			// Exemplo: a mensagem abaixo está em português, mas e
			// se o programa fosse inteiro em outra língua?
			//System.out.println("Erro push(): pilha cheia.");
			return new Result<T>(-1, null);
		}
		
		data[count] = value;
		++count;
		
		return new Result<T>(0, null);
	}
	
	public Result<T> pop() {
		if (isEmpty()) {
			//System.out.println("Erro pop(): pilha vazia.");
			return new Result<T>(-1, null);
		}

		--count;
		T value = data[count];
		
		// Reinicia o conteúdo do elemento na posição removida.
		// Se data[] for um array de objetos, atribuir o valor null.
		data[count] = null;
		
		return new Result<T>(0, value);
	}
	
	public Result<T> top() {
		if (isEmpty()) {
			return new Result<T>(-1, null);
		}
		
		return new Result<T>(0, data[count - 1]);
	}
	
	public boolean isFull() {
		return count == data.length;
	}
	
	public boolean isEmpty() {
		return count == 0;
	}
	
	// Retorna tamanho (capacidade) da pilha.
	public int size() {
		return data.length;
	}
	
	// Retorna quantidade de elementos empilhados.
	public int count() {
		return count;
	}
	
	@Override
	public String toString() {
		/*
		// String é imutável.
		// Toda concatenação cria uma string temporária.
		// Isso é ruim.
		String str = "data: ";
		for (var x : data) {
			str += x + ", ";
		}
		str += "count: " + count;
		return str;
		*/
		
		// Código abaixo faz a mesma coisa, mas de forma
		// mais eficiente (não há criação de strings temporárias
		// a cada concatenação).
		StringBuilder sb = new StringBuilder();
		for (var x : data) {
			sb.append(x).append(", ");
		}
		sb.append("count: ").append(count);
		return sb.toString();
	}

}
