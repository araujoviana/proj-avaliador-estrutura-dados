 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707     //
 /////////////////////////////////////////////


 import java.util.Scanner;

// TODO organizar ordem dos métodos
// TODO Criar sistema de erros
 // Essa classe pode ser renomeada depois
public class Repl {

    private final int MAX_VARIABLES = 26;

    private final Queue<String> recordedCommands = new Queue<>(10);
    private boolean isRecording = false;

    // Poderia ser uma stack mas não tenho certeza
    // Comandos
     String[] commands = {"ERASE", "EXIT", "PLAY", "REC", "RESET", "STOP", "VARS"};

     // Operadores válidos
    private final Character[] operators = {'+','-','*','/','^'};

    private final Character[] variableNames = new Character[MAX_VARIABLES];
    private final float[] variableValues = new float[MAX_VARIABLES];
    private int variableCount = 0;

    /**
     * Armazena o valor de uma variável
     * @param name nome da variável
     * @param value valor da variável
     */
    public void storeVariable(Character name, float value) {
        // Verifica se a variável já existe para atualizá-la
        for (int i = 0; i < variableCount; i++) {
            if (variableNames[i] == name) {
                variableValues[i] = value;
                return;
            }
        }

        // Se a variável não existir, adiciona uma nova
        if (variableCount < MAX_VARIABLES) {
            variableNames[variableCount] = name;
            variableValues[variableCount] = value;
            variableCount++;
        } else {
            // TODO Arrumar essa mensagem de erro, contextualizar melhor
            System.out.println("Erro: número máximo de variáveis atingido.");
        }
    }


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

     private boolean isVariableName(Character character) {
         for (Character variableName : variableNames) {
             if (character.equals(variableName)) {
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
     * Verifica se a entrada é uma definição válida de variável.
     * @param input entrada do usuário
     * @return true se for uma atribuição válida
     */
    public boolean isVariableDefinition(String input) {
        // Verifica se o formato é uma atribuição de variável (letra seguida de '=' e um número)
        if (Character.isLetter(input.charAt(0)) && input.charAt(1) == '=' && Character.isDigit(input.charAt(2))) {
            boolean isNumber = true;
            boolean hasDecimalPoint = false;

            // Verifica os caracteres restantes após o '='
            for (int i = 2; i < input.length(); i++) {
                char c = input.charAt(i);

                // Verifica se é um ponto decimal e se já existe um
                if (c == '.') {
                    if (hasDecimalPoint) {
                        isNumber = false;
                        break;
                    }
                    hasDecimalPoint = true;
                }
                else if (!Character.isDigit(c)) {
                    isNumber = false;
                    break;
                }
            }

            return isNumber;
        }
        return false;
    }

    /**
     * Lê a entrada não formatada do usuário e avalia se ela é um comando, cálculo ou atribuição de variável
     * @param input entrada formatada do usuário
     */
    public void readFormattedInput(String input) {
        // Verifica se entrada é um comando
        if (isCommand(input)) {
            evaluateCommand(input);
        }
        else {
            // Verifica definição de variável
            if (isVariableDefinition(input)) {
                // Extraí o nome da variável
                Character variableName = input.charAt(0);

                // Extrai o valor da variável (parte após '=')
                String valueString = input.substring(2);
                float value = Float.parseFloat(valueString);
                storeVariable(variableName, value);

                System.out.println(input);
            }
            // Verifica cálculo
            else if (validateCalculationInput(input)) {
                // TODO
                // - Converter para pósfixo
                // - Fazer o cálculo

                // print temporário
                System.out.println("VALIDADO!");
            }
            else {
                System.out.println("Erro: Entrada inválida");
            }
        }
    }

     /**
      * Verifica se a entrada infixa formatada é válida, rejeitando:
      * <ol>
      *     <li>Contas usando números ao invés de variáveis</li>
      *     <li>Parenteses que não combinam</li>
      *     <li>Operadores inválidos</li>
      *     <li>Variáveis indefinidas</li>
      *     <li>Operações começando com operandos</li>
      * </ol>
      * @param input entrada infixa formatada
      * @return true se a entrada é válida
      */
     public boolean validateCalculationInput(String input) {
         boolean inputIsValid;

         // Verifica a presença de números
         for (int i = 0; i < input.length(); i++) {
             if (Character.isDigit(input.charAt(i))) {
                 return false;
             }
         }

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
             if (!Character.isLetter(c)) {
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

         // Verifica se as variáveis acessadas foram definidas
         for (int i = 0; i < input.length(); i++) {
             if (Character.isLetter(input.charAt(i))) {
                 if (!isVariableName(input.charAt(i))) {
                     return false;
                 }
             }
         }

         // Verifica a posição de operadores
         // Sempre haverá uma única variável na stack se a operação é válida
         Stack<Character> variables = new Stack<>();

         for (int i = 0; i < input.length(); i++) {
             char c = input.charAt(i);

             if (Character.isLetter(c)) {
                 variables.push(c);
             }
             else if (isOperator(c)) {
                 if (variables.isEmpty()) {
                     return false;
                 }
                 variables.pop();
             }
         }

         // No final, deve haver apenas uma variável restante na pilha
         if (!(variables.size() == 1)) {
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
    private Float evaluatePostfixCalculation(String postfix) {
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
