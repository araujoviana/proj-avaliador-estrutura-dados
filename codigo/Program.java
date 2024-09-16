 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707	    //
 /////////////////////////////////////////////

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {

		// Menu básico, poderia ser uma classe separada
		Scanner scanner = new Scanner(System.in);
		String input = "-";

		while (!input.equals("EXIT")) {

			System.out.print("> ");

			// Recebe o texto e converte pra maiúsculo
			input = scanner.nextLine().toUpperCase();

			switch (input) {
				case "RESET":
					System.out.println("Variáveis reiniciadas");
					break;
				case "EXIT":
					break;
				default:
					System.out.println("Erro: expressão inválida");
					break;
			}
		}

		scanner.close();
		return;
	}

}
