 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707	    //
 /////////////////////////////////////////////

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String input = "";

		Repl repl = new Repl();

		while (!input.equals("EXIT")){
			// Execução ideal
			System.out.print("> ");

			input = repl.formatInput(scanner.nextLine());

			repl.readFormattedInput(input);



//			// DEBUG evaluatePostfixCalculation
//			repl.readFormattedInput("A=10");
//			repl.readFormattedInput("B=3");
//			System.out.println(repl.evaluatePostfixCalculation("AB*"));
//			System.out.println(repl.evaluatePostfixCalculation("AB+"));
//			System.out.println(repl.evaluatePostfixCalculation("AB-"));
//			System.out.println(repl.evaluatePostfixCalculation("AB/"));
//			System.out.println(repl.evaluatePostfixCalculation("AB^"));
//			input = "EXIT";


		}

		scanner.close();
	}

}
