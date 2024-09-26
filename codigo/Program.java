/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707	   //
/////////////////////////////////////////////

 /*
 Referências:

 Javadocs: https://www.jetbrains.com/help/idea/javadocs.html

  */

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = "";

        Repl repl = new Repl();

        while (!input.equals("EXIT")) {
            System.out.print("> ");

            // Entrada é formatada antes de ser avaliada
            input = repl.formatInput(scanner.nextLine());

            repl.readFormattedInput(input);

        }

        scanner.close();
    }

}
