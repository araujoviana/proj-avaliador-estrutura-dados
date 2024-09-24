 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707     //
 /////////////////////////////////////////////


 import java.util.Scanner;

// TODO organizar ordem dos métodos

 // Essa classe pode ser renomeada depois
public class Repl {

     private final Queue<String> recordedCommands = new Queue<>(10);
     private boolean isRecording = false;

    // Poderia ser uma stack mas não tenho certeza
    // Comandos
     String[] commands = {"ERASE", "EXIT", "PLAY", "REC", "RESET", "STOP", "VARS"};

     // Operadores válidos
    private final Character[] operators = {'+','-','*','/','^'};
    
    private Character[] variableNames;
    private Float[] variableValues;

     private boolean isOperator(char c) {
         for (char operator : operators) {
             if (c == operator) {
                 return true;
             }
         }
         return false;
     }

     private boolean isCommand(String string) {
         for (String command : commands) {
             if (string.equals(command)) {
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
      * Lê a entrada não formatada do usuário e avalia se ela é um comando, cálculo ou atribuição de variável
      * @param input entrada formatada do usuário
      */
     public void readFormattedInput(String input) {
         // Verifica se entrada é um comando
         // TODO Lidar com criação de variáveis
         if (isCommand(input)) {
            evaluateCommand(input);
         }
         else {
             if (validateCalculationInput(input)) {
                 // TODO
                 System.out.println("CERTO!");
             }
         }
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
    public void evaluateCommand(String command) {

        switch (command) {

        // Comandos de gravação
            case "REC":
                isRecording = true;
                startRecording();
                break;

            // FIXME Comando STOP é implementado direto na função startRecording

            case "ERASE":
                System.out.println("Gravação apagada.");
                cleanRecordedCommands();
                break;

            // Executa os comandos sem apagar o conteúdo da fila
            case "PLAY":
                System.out.println("Reproduzindo gravação...");
                for (int i = 0; i < recordedCommands.count(); i++) {
                    String playedCommand = recordedCommands.dequeue();
                    String formattedPlayedCommand = formatInput(playedCommand);
                    System.out.println(formattedPlayedCommand);
                    readFormattedInput(formattedPlayedCommand);
                    recordedCommands.enqueue(formattedPlayedCommand);
                }
                break;

            // Comando EXIT é implementado na classe Program
        }
    }

    private void cleanRecordedCommands() {
        while (!recordedCommands.isEmpty()) {
            recordedCommands.dequeue();
        }
    }

     /** Começa a gravação, armazena todos os comandos em uma fila de no máximo 10 elementos,
      * reinicia a fila se os comandos REC ou STOP são executados
      *
      */
    private void startRecording() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Iniciando gravação...");

        // Limpa
        cleanRecordedCommands();

        while (recordedCommands.count() <= 10 && isRecording) {
            System.out.println("REC: ("+recordedCommands.count()+"/10)");

            System.out.print("> ");

            String input = formatInput(scanner.nextLine());

            // Lida com comandos inválidos
            if (input.equals("REC") || input.equals("PLAY") || input.equals("ERASE")) {
                System.out.println("Erro: comando inválido para gravação.");
                continue;
            }

            // Adiciona entrada pra fila
            // TODO Inserir criação de variáveis
            if (input.equals("STOP")) {
                System.out.println("Parando gravação... "+" REC: ("+ recordedCommands.count()+"/10)");
                isRecording = false;
                break;
            }
            else if (isCommand(input)) {
                recordedCommands.enqueue(input);
            }
            else {
                if (validateCalculationInput(input)) {
                    recordedCommands.enqueue(input);
                }
            }

        }


    }

     /**
      * Executa o cálculo formatado em pósfixo usando uma pilha e retorna o resultado
      * @param postfix cálculo do usuário formatado em pósfixo tipo: "AB+CD-/E*"
      * @return resultado final
      */
    public Float evaluatePostfixCalculation(String postfix) {
        Stack<Float> result = new Stack<>();

        // TODO atribuir valores númericos às variáveis da expressão a ser avaliada

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            // FIXME deveria lidar APENAS com variáveis
            // Empilha operandos
            if (Character.isDigit(c)) {
                String number = "";

                while (i < postfix.length() && Character.isDigit(postfix.charAt(i))) {
                    number += postfix.charAt(i);
                    i++;
                }

                result.push(Float.parseFloat(number));
            }

            if (isOperator(c)) {
                Float firstNumber = result.pop().getValue();
                Float secondNumber = result.pop().getValue();

                switch (c) {
                    case '+':
                        result.push(firstNumber + secondNumber);
                        break;
                    case '-':
                        result.push(firstNumber - secondNumber);
                        break;
                    case '*':
                        result.push(firstNumber * secondNumber);
                        break;
                    case '/':
                        result.push(firstNumber / secondNumber);
                        break;
                    case '^':
                        result.push((float) Math.pow(firstNumber,secondNumber));
                }
            }
        }

        return result.pop().getValue();
    }

}
