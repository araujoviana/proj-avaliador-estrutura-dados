 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707	    //
 /////////////////////////////////////////////

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {

		// Menu básico
		Scanner scanner = new Scanner(System.in);

		Expression expression = new Expression();

		while (!expression.getInfixExpression().equals("EXIT")){

			System.out.print("> ");

			// Recebe o texto e converte pra maiúsculo
			String input = scanner.nextLine().toUpperCase();
			expression.setInfixExpression(input);

			// Verifica se a expressão é válida e
			// converte para pósfixo
			if (expression.isValid()) {
				// Converteria a expressão infixo pra posfixo
				expression.setInfixExpression(expression.toPostfix());
				// Executa o comando/cálculo
				expression.evaluate();
			}
			else {
				System.out.println("Erro: comando inválido");
            }
		}

		scanner.close();
	}

}
