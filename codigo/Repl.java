/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////


import java.util.Scanner;

public class Repl {

    private final int MAX_VARIABLES = 26;
    private final Character[] variableNames = new Character[MAX_VARIABLES];
    private final float[] variableValues = new float[MAX_VARIABLES];
    private int variableCount = 0;


    private final Queue<String> recordedCommands = new Queue<>(10);
    private boolean isRecording = false;


    // Verifica se um caractere é um dos operadores válidos em operators
    private boolean isOperator(char c) {
        // Operadores válidos
        final Character[] operators = {'+', '-', '*', '/', '^'};

        for (char operator : operators) {
            if (c == operator) {
                return true;
            }
        }
        return false;
    }

    // Verifica se uma String é um dos comandos válidos em commands
    private boolean isCommand(String string) {
        // Comandos
        final String[] commands = {"ERASE", "EXIT", "PLAY", "REC", "RESET", "STOP", "VARS"};

        for (String command : commands) {
            if (string.equals(command)) {
                return true;
            }
        }
        return false;
    }

    // Verifica se uma String é um caractere é o nome de uma váriavel definida
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
     * Variáveis são válidas se seguem a estrutura "LETRA=NÚMERO_REAL"
     *
     * @param input string formatada do usuário
     * @return true se for uma atribuição válida
     */
    private boolean isVariableDefinition(String input) {
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
                } else if (!Character.isDigit(c)) {
                    isNumber = false;
                    break;
                }
            }

            return isNumber;
        }
        return false;
    }


    /**
     * Verifica se a entrada infixa formatada é válida, rejeitando:
     * <ol>
     *     <li>Contas usando números ao invés de variáveis</li>
     *     <li>Parenteses que não combinam</li>
     *     <li>Operadores inválidos</li>
     *     <li>Variáveis indefinidas</li>
     *     <li>Operações começando com operandos</li>
     *     <li>Cálculos com variáveis indefinidas</li>
     * </ol>
     *
     * @param input entrada infixa formatada
     * @return true se a entrada é válida
     */
    private boolean isCalculation(String input) {
        boolean inputIsValid;

        // Verifica a presença de números
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                printError("uso direto de números sem variáveis");
                return false;
            }
        }

        // Verifica parênteses
        Stack<Character> parenthesis = new Stack<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                parenthesis.push(input.charAt(i));
            } else if (input.charAt(i) == ')') {
                if (parenthesis.isEmpty()) {
                    // Parenteses não combinam
                    printError("parênteses não combinam");
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
                        printError("operador inválido");
                        return false;
                }
            }
        }

        // Verifica se as variáveis acessadas foram definidas
        for (int i = 0; i < input.length(); i++) {
            if (Character.isLetter(input.charAt(i))) {
                if (!isVariableName(input.charAt(i))) {
                    printError("variável " + input.charAt(i) + " não definida.");
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
            } else if (isOperator(c)) {
                if (variables.isEmpty()) {
                    printError("cálculo não estruturado corretamente");
                    return false;
                }
                variables.pop();
            }
        }

        // No final, deve haver apenas uma variável restante na pilha
        if (!(variables.count() == 1)) {
            return false;
        }

        if (!inputIsValid) {
            printError("parênteses não combinam");
        }
        return inputIsValid;
    }


    /**
     * Armazena ou atualiza o valor de uma variável.
     * Se a variável já existir, atualiza seu valor. Caso contrário, cria uma nova
     * variável, desde que o limite não seja excedido.
     *
     * @param name  nome da variável a ser armazenada ou atualizada.
     * @param value valor associado à variável.
     */
    private void storeVariable(Character name, float value) {
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
            printError("número máximo de variáveis atingido");
        }
    }


    /**
     * Converte a entrada inicial para maíusculo e remove todos os espaços
     *
     * @param input entrada infixa tipo " (a + 7)  "
     * @return entrada infixa formatada tipo "(A+7)"
     */
    public String formatInput(String input) {
        return input.toUpperCase().replaceAll(" ", "");
    }


    /**
     * Lê a entrada não formatada do usuário e avalia se ela é um comando,
     * cálculo ou atribuição de variável.
     *
     * @param input entrada formatada do usuário
     */
    public void readFormattedInput(String input) {
        // Verifica se a entrada é vazia
        if (input.isEmpty()) {
            return;
        }
        // Verifica se a entrada é uma única variável
        if (input.length() == 1) {
            if (isVariableName(input.charAt(0))) {
                for (int i = 0; i < variableCount; i++) {
                    if (variableNames[i] == input.charAt(0)) {
                        System.out.println(variableValues[i]);
                    }
                }
            } else {
                printError("impossível verificar o valor de " + input.charAt(0));
            }
        }
        // Verifica se entrada é um comando
        else if (isCommand(input)) {
            evaluateCommand(input);
        }
        // Verifica definição de variável
        else if (isVariableDefinition(input)) {
            // Extraí o nome da variável
            Character variableName = input.charAt(0);

            // Extrai o valor da variável (parte após '=')
            String valueString = input.substring(2);
            float value = Float.parseFloat(valueString);
            storeVariable(variableName, value);

            System.out.println(input);
        }
        // Verifica cálculo
        else if (isCalculation(input)) {

            String postfixInput = convertInfixToPostfix(input);

            // Imprime resultado final
            System.out.println(evaluatePostfixCalculation(postfixInput));

        } else {
            printError("entrada inválida");
        }
    }

    /**
     * Converte expressão infixa para pósfixa
     *
     * @param infixExpression String de expressão infixa tipo "A*(B+C)/D"
     * @return String de expressão pósfixa tipo "ABC+*D/"
     */
    private String convertInfixToPostfix(String infixExpression) {
        Stack<Character> stack = new Stack<>();
        String output = "";

        for (int i = 0; i < infixExpression.length(); i++) {
            char c = infixExpression.charAt(i);

            // Se for um operando
            if (Character.isLetter(c)) {
                output += c;
            }
            // Se for um parêntese esquerdo, empilha
            else if (c == '(') {
                stack.push(c);
            }
            // Se for um parêntese direito, desempilha até encontrar o parêntese esquerdo
            else if (c == ')') {
                while (!stack.isEmpty() && stack.top().getValue() != '(') {
                    output += stack.pop().getValue();
                }
                stack.pop(); // Remove o '(' da pilha
            }
            // Operadores
            else {
                while (!stack.isEmpty() && operatorPrecedence(c) <= operatorPrecedence(stack.top().getValue())) {
                    output += stack.pop().getValue();
                }
                stack.push(c);
            }
        }

        // Desempilha todos os operadores restantes
        while (!stack.isEmpty()) {
            output += stack.pop().getValue();
        }

        return output;
    }

    // Define a precedência dos operadores para o cálculo
    private static int operatorPrecedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    /**
     * Faz o cálculo pósfixo formatado.
     *
     * @param postfix cálculo do usuário formatado em pósfixo tipo: "AB+CD-/E*"
     * @return resultado final
     */
    private Float evaluatePostfixCalculation(String postfix) {

        Stack<Float> result = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);


            // Empilha o valor da variável
            if (Character.isLetter(c)) {
                boolean variableFound = false;

                // Encontra o índice da variável no array de nomes
                for (int j = 0; j < variableCount; j++) {
                    if (variableNames[j] == c) {
                        // Empilha o valor da variável correspondente
                        result.push(variableValues[j]);
                        variableFound = true;
                        break;
                    }
                }

                // Se a variável não for encontrada, lança um erro ou imprime uma mensagem
                if (!variableFound) {
                    printError("uso de variável indefinida.");
                }
            }

            if (isOperator(c)) {
                Float firstNumber = result.pop().getValue();
                Float secondNumber = result.pop().getValue();

                switch (c) {
                    case '+':
                        result.push(secondNumber + firstNumber);
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
                        result.push((float) Math.pow(secondNumber, firstNumber));
                }
            }
        }

        return result.pop().getValue();
    }

    /**
     * Executa o comando inserido pelo usuário, comandos mais complexos usam métodos separados.
     *
     * @param command comando formatado inserido pelo usuário
     */
    private void evaluateCommand(String command) {

        switch (command) {

            // Comandos de variáveis
            case "VARS":
                if (variableNames[0] == null) {
                    printError("nenhuma variável definida.");
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
                    printError("Não há gravação para ser reproduzida");
                    break;
                }

                System.out.println("Reproduzindo gravação...");
                for (int i = 0; i < recordedCommands.count(); i++) {
                    String playedCommand = recordedCommands.dequeue();
                    String formattedPlayedCommand = formatInput(playedCommand);
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


    // Começa a gravação, armazena todos os comandos em uma fila de no máximo 10 elementos.
    private void startRecording() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Iniciando gravação...");

        // Limpa fila
        cleanRecordedCommands();

        while (isRecording) {

            System.out.println("REC: (" + recordedCommands.count() + "/10)");

            // Verifica se fila de comandos está cheia
            if (recordedCommands.isFull()) {
                printError("fila de comandos cheia");
                isRecording = false;
                return;
            }

            System.out.print("> ");

            String input = formatInput(scanner.nextLine());

            // Rejeita comandos inválidos
            if (input.equals("REC") || input.equals("PLAY") || input.equals("ERASE") || input.equals("EXIT")) {
                printError("Comando inválido para gravação");
                continue;
            }

            // Interrompe gravação
            if (input.equals("STOP")) {
                System.out.println("Parando gravação... " + " REC: (" + recordedCommands.count() + "/10)");
                isRecording = false;
                break;
            }

            // Não grava entradas vazias
            if (input.isEmpty()) {
                continue;
            }

            // Grava entrada
            recordedCommands.enqueue(input);

        }

    }

    // Limpa a fila de comandos gravados
    private void cleanRecordedCommands() {
        while (!recordedCommands.isEmpty()) {
            recordedCommands.dequeue();
        }
    }

    // Imprime erros para o usuário
    private void printError(String error) {
        System.out.println("Erro: " + error);
    }

}
