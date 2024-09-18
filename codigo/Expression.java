 /////////////////////////////////////////////
 // Matheus Gabriel Viana Araujo - 10420444 //
 // Enzo Carvalho Pagliarini - 10425707     //
 /////////////////////////////////////////////

public class Expression {
    // Esboço incompleto da classe
	private String infixExpression = "";

    public boolean isValid() {
        // Verifica se a expressão é válida
        // segundo o que o professor pede
        // TODO: Implementar verificação
        System.out.println("Verificando se expressão " + infixExpression + " é válida...");
        return true;
    }

    public String toPostfix() {
        // Converte a expressão infixa pra posfixa
        // TODO: Implementar conversão
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
        }

    }

    public String getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(String infixExpression) {
        this.infixExpression = infixExpression;
    }


}
