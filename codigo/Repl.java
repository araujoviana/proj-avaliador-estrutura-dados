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

    Stack<Integer> variables;

     public Repl() {
         // Variáveis criadas pelo usuário
         variables = new Stack<>();
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

         // TODO: Adiciona terceira validação

         return inputIsValid;
    }

    public String toPostfix() {
        // Converte a expressão infixa pra posfixa
        // TODO: Implementar conversão
        // teste
        System.out.println("Convertendo para posfixo...");
        return "Expressão em posfixo";
    }

    // Não vi, mas provavelmente a avaliação do cálculo
    // precisar de mais funções
    // TODO: Verificar valor de retorno da função
    public void evaluateCalculation(String input) {
        // Avalia a expressão e calcula o resultado
        Stack<Integer> calculationStack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char currentElement = input.charAt(i);

            // Números são empilhados
            if (Character.isDigit(currentElement)) {
                calculationStack.push(Character.getNumericValue(currentElement));
                continue;
            }
            // TODO: Adicionar condição de converter variáveis para os seus valores respectivos

            /* Operandos
             * os últimos dois valores são desempilhados,
             * a operação respectiva é feita
             * e o resultado é empilhado novamente
            */
            Integer x = calculationStack.pop().getValue();
            Integer y = calculationStack.pop().getValue();
            switch (currentElement) {
                case '+':
                    calculationStack.push(x+y);
                    break;
                case '-':
                    calculationStack.push(x-y);
                    break;
                case '*':
                    calculationStack.push(x*y);
                    break;
                case '/':
                    calculationStack.push(x/y);
                    break;
                case '%':
                    calculationStack.push(x%y);
                    break;
                case '^':
                    calculationStack.push(x^y);
                    break;
            }
      System.out.println(calculationStack.pop().getValue());
        }

    }

    public String getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(String infixExpression) {
        this.infixExpression = infixExpression;
    }


}
