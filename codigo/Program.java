 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707	    //
 /////////////////////////////////////////////

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		Repl repl = new Repl();

		while (!repl.getInfixExpression().equals("EXIT")){

			System.out.print("> ");

			// Recebe o texto
			// Converte para maiusculo
			String input = scanner.nextLine().toUpperCase().replaceAll(" ","");
			repl.setInfixExpression(input);

			// Verifica se a expressão é válida e
			// converte para pósfixo
			if (repl.isValid()) {
				// Converteria a expressão infixo pra posfixo
				repl.setInfixExpression(repl.toPostfix());
				// Executa o comando/cálculo
				repl.evaluateCalculation(repl.getInfixExpression());
			}
			else {
				System.out.println("Erro: comando inválido");
            }
		}

		scanner.close();
	}

}
