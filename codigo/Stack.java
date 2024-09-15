 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707	    //
 /////////////////////////////////////////////

public class Stack {
	private static final int DEFAULT_SIZE = 10;
	
	private int data[];
	private int count;
	
	public Stack() {
		this(DEFAULT_SIZE);
	}
	
	public Stack(int size) {
		data = new int[size];
		count = 0;
	}
	
	public Result push(int value) {
		if (isFull()) {
			// OBSERVAÇÃO: Evite instruções de saída (print),
			// pois nem sempre queremos ter essas saídas no programa.
			// Exemplo: a mensagem abaixo está em português, mas e
			// se o programa fosse inteiro em outra língua?
			//System.out.println("Erro push(): pilha cheia.");
			return new Result(-1, 0);
		}
		
		data[count] = value;
		++count;
		
		return new Result(0, 0);
	}
	
	public Result pop() {
		if (isEmpty()) {
			//System.out.println("Erro pop(): pilha vazia.");
			return new Result(-1, 0);
		}

		--count;
		int value = data[count];
		
		// Reinicia o conteúdo do elemento na posição removida.
		// Se data[] for um array de objetos, atribuir o valor null.
		data[count] = 0;
		
		return new Result(0, value);
	}
	
	public Result top() {
		if (isEmpty()) {
			return new Result(-1, 0);
		}
		
		return new Result(0, data[count - 1]);
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
