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

			String input = repl.formatInput(scanner.nextLine());

			repl.readFormattedInput(input);


		}

		scanner.close();
	}

}
