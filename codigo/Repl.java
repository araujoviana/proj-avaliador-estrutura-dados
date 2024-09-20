 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707     //
 /////////////////////////////////////////////


 // Essa classe pode ser renomeada depois
public class Repl {

	private String infixExpression = "";

    // Poderia ser uma stack mas não tenho certeza
    // Comandos
     String[] commands = {"ERASE", "EXIT", "PLAY", "REC", "RESET", "STOP", "VARS"};

     // Operadores válidos
    private final Character[] operators = {'+','-','*','/','^'};

    Stack<Integer> variables;

     public Repl() {
         // Variáveis criadas pelo usuário
         variables = new Stack<>();
     }

     /**
      * Verifica se um caractere é um operador válido
      * @param c caractere
      * @return true se o caractere é um operador
      */
     private boolean isOperator(char c) {
         for (char operator : operators) {
             if (c == operator) {
                 return true;
             }
         }
         return false;
     }

     /**
      * Converte a entrada inicial para maíusculo e remove todos os espaços
      * @param input entrada infixa tipo " (a + 7)  "
      * @return entrada infixa formatada tipo "(A+7)"
      */
     public String formatInput(String input) {
         return input.toUpperCase().replaceAll(" ","");
     }

     /**
      * Verifica se a entrada infixa formatada é válida, rejeitando:
      * <ol>
      *     <li>Parenteses que não combinam</li>
      *     <li>Operadores inválidos</li>
      *     <li>Operações começando com operandos</li>
      * </ol>
      * @param input entrada infixa formatada
      * @return true se a entrada é válida
      */
     public boolean validateCalculationInput(String input) {
         boolean inputIsValid;

         // Verifica parenteses
         Stack<Character> parenthesis = new Stack<>();
         for (int i = 0; i < input.length(); i++) {
             if (input.charAt(i) == '(') {
                 parenthesis.push(input.charAt(i));
             }
             else if (input.charAt(i) == ')') {
                 if (parenthesis.isEmpty()) {
                     // Parenteses não combinam
                     return false;
                 }
                 parenthesis.pop();
             }
         }
         inputIsValid = parenthesis.isEmpty();

        // Verifica operadores
         for (int i = 0; i < input.length(); i++) {
             char c = input.charAt(i);
             if (!Character.isLetterOrDigit(c)) {
                 switch (c) {
                     case '(':
                     case ')':
                     case '+':
                     case '-':
                     case '*':
                     case '/':
                     case '^':
                         continue;
                     default:
                         return false;
                 }
             }
         }

         // Verifica a posição de operadores
         // Sempre haverá um único número na stack se a operação é válida
         Stack<Integer> numbers = new Stack<>();

         for (int i = 0; i < input.length(); i++) {
             char c = input.charAt(i);
             if (Character.isDigit(c)) {
                 int number = 0;
                 while (i < input.length() && Character.isDigit(input.charAt(i))) {
                     number = number * 10 + Character.getNumericValue(input.charAt(i));
                     i++;
                 }
                 i--;
                 numbers.push(number);
             }
             else if (isOperator(c)) {
                 if (numbers.isEmpty()) {
                     return false;
                 }
                 numbers.pop();
             }
         }
         if (!(numbers.count() == 1)) {
             return false;
         }

         return inputIsValid;
    }

     /**
      * Executa o comando inserido pelo usuário
      * @param command comando inserido pelo usuário
      */
    public void evaluteCommand(String command) {
         
    }


// MÉTODOS RASCUNHO

    public String getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(String infixExpression) {
        this.infixExpression = infixExpression;
    }


}
