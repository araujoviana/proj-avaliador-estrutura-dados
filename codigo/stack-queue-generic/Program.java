import java.util.Scanner;

public class Program {
	
	public static boolean isPalindrome(String str) {
		// PARA PENSAR...
		// Preciso empilhar todos os caracteres da string?
		Stack<Character> s = new Stack<>(str.length());
		
		for (int i = 0; i < str.length(); ++i) {
			s.push(str.charAt(i));
		}
		
		//System.out.println("Conteúdo da pilha: " + s);
		
		int index = 0;
		while (!s.isEmpty()) {
			Result<Character> r = s.pop();
			//System.out.println("pop resultado: " + r.getErrorCode() + ", valor: " + r.getValue());
			//System.out.println("Conteúdo da pilha: " + s);
			
			if (str.charAt(index) != r.getValue()) {
				break;
			}
			++index;
		}
		
		/*
		if (s.isEmpty()) {
			System.out.println("'" + str + "' é palíndromo.");
		} else {
			System.out.println("'" + str + "' não é palíndromo.");
		}
		*/
		
		return s.isEmpty();
	}

	public static void main(String[] args) {
		Stack<Float> pilhaFloat = new Stack<>();
		System.out.println("pilhaFloat: " + pilhaFloat);
		for (int i = 0; i < pilhaFloat.size(); ++i) {
			pilhaFloat.push((i + 1) * 0.5f);
			System.out.println("pilhaFloat: " + pilhaFloat);
		}

		Queue<Float> filaFloat = new Queue<>(pilhaFloat.size());
		System.out.println("filaFloat: " + filaFloat);
		while (!pilhaFloat.isEmpty()) {
			filaFloat.enqueue(pilhaFloat.pop().getValue());
			System.out.println("filaFloat: " + filaFloat);
		}
		
		Stack<Integer> pilhaInt = new Stack<>();
		System.out.println("pilhaInt: " + pilhaInt);
		for (int i = 0; i < pilhaInt.size(); ++i) {
			pilhaInt.push(99 - i);
			System.out.println("pilhaInt: " + pilhaInt);
		}
		
		Generic2<Integer, String> kv1 = new Generic2<>(10, "Número 10");
		System.out.println("kv1: " + kv1);

		Generic2<String, String> kv2 = new Generic2<>("Hello", "Ola");
		System.out.println("kv2: " + kv2);
		
		/*
		Scanner input = new Scanner(System.in);
		
		System.out.print("Digite uma palavra: ");
		String str = input.nextLine();
		
		System.out.println("Palavra informada: " + str);
		
		// Se quiser usar um array de char igual linguagem C,
		// é necessário converter a string para array:
		//char arr[] = str.toCharArray();
		
		System.out.println("'" + str + "'"
				+ (isPalindrome(str) ? " " : " não ")
				+ "é palíndromo.");
		*/
	}

}
