 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707     //
 /////////////////////////////////////////////


 import java.util.Scanner;

// TODO Criar sistema de erros

 public class Repl {

    private final int MAX_VARIABLES = 26;
    private final Character[] variableNames = new Character[MAX_VARIABLES];
    private final float[] variableValues = new float[MAX_VARIABLES];
    private int variableCount = 0;

    // Comandos
    private final String[] commands = {"ERASE", "EXIT", "PLAY", "REC", "RESET", "STOP", "VARS"};

    private final Queue<String> recordedCommands = new Queue<>(10);
    private boolean isRecording = false;

    // Operadores válidos
    private final Character[] operators = {'+','-','*','/','^'};
    
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
     * Armazena o valor de uma variável nos vetores Variable
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
            throwError("número máximo de variáveis atingido");
        }
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

                String postfixInput; //  = métod\o de conversão infixo pra posfixo TODO

                // Temporário enquanto o métod\o não existe
                postfixInput = "AB+";
                evaluatePostfixCalculation(postfixInput);

            }
            else {
                throwError("Entrada inválida");
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

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            // Empilha o valor da variável
            if (Character.isLetter(c)) {
                // Encontra o índice da variável no array de nomes
                for (int j = 0; j < variableCount; j++) {
                    if (variableNames[j] == c) {
                        // Empilha o valor da variável correspondente
                        result.push(variableValues[j]);
                        break;
                    }
                }
            }

            if (isOperator(c)) {
                Float firstNumber = result.pop().getValue();
                Float secondNumber = result.pop().getValue();

                switch (c) {
                    case '+':
                        result.push( secondNumber + firstNumber);
                        break;
                    case '-':
                        result.push(secondNumber - firstNumber);
                        break;
                    case '*':
                        result.push(secondNumber * firstNumber);
                        break;
                    case '/':
                        result.push(secondNumber / firstNumber);
                        break;
                    case '^':
                        result.push((float) Math.pow(secondNumber,firstNumber));
                }
            }
        }

        return result.pop().getValue();
    }

    /**
      * Executa o comando inserido pelo usuário, comandos mais complexos usam métodos separados
      * @param command comando inserido pelo usuário
      */
    public void evaluateCommand(String command) {

        switch (command) {

            // Comandos de variáveis
            case "VARS":
                if (variableNames[0] == null) {
                    throwError("nenhuma variável definida.");
                    break;
                }
                for (int i = 0; i < variableNames.length; i++) {
                    if (variableNames[i] != null) {
                        System.out.println(variableNames[i] + " = " + variableValues[i]);
                    }
                }
                break;

            case "RESET":
                for (int i = 0; i < variableNames.length; i++) {
                    variableNames[i] = null;
                    variableValues[i] = 0;
                }
                variableCount = 0;
                System.out.println("Variáveis reiniciadas.");
                break;

            // Comandos de gravação
            case "REC":
                isRecording = true;
                startRecording();
                break;

            case "ERASE":
                System.out.println("Gravação apagada.");
                cleanRecordedCommands();
                break;

            // Executa os comandos sem apagar o conteúdo da fila
            case "PLAY":

                if (recordedCommands.isEmpty()) {
                    throwError("Não há gravação para ser reproduzida");
                    break;
                }

                System.out.println("Reproduzindo gravação...");
                for (int i = 0; i < recordedCommands.count(); i++) {
                    String playedCommand = recordedCommands.dequeue();
                    String formattedPlayedCommand = formatInput(playedCommand);
                    System.out.println(formattedPlayedCommand);
                    readFormattedInput(formattedPlayedCommand);
                    recordedCommands.enqueue(formattedPlayedCommand);
                }
                break;

            /*
             Comando STOP é implementado no métod\o startRecording
             Comando EXIT é implementado na classe Program
            */
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
                throwError("Comando inválido para gravação");
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

    // Limpa a fila de comandos gravados
    private void cleanRecordedCommands() {
        while (!recordedCommands.isEmpty()) {
            recordedCommands.dequeue();
        }
    }

    // Imprime erros para o usuário
     private void throwError(String error) {
         System.out.println("Erro: " + error);
     }

}
