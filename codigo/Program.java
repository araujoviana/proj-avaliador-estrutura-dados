/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707	   //
/////////////////////////////////////////////

 /*
 Referências:

 Javadocs: https://www.jetbrains.com/help/idea/javadocs.html
 Final: https://www.w3schools.com/java/ref_keyword_final.asp
 Switch: https://docs.oracle.com/en/java/javase/22/language/switch-expressions.html
 Substring: https://www.digitalocean.com/community/tutorials/java-string-substring
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

            // Interpreta e avalia o tipo de entrada específica,
            // imprimindo o resultado
            repl.readFormattedInput(input);

        }

        scanner.close();
    }

}
