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

    // Não vi mas provavelmente a avaliação do cálculo
    // precisar de mais funções
    public double evaluate() {
        // Avalia a expressão e calcula o resultado
        // TODO: Implementar avaliação
        System.out.println("Fazendo cálculo...");
        return 0.2;
    }

    public String getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(String infixExpression) {
        this.infixExpression = infixExpression;
    }


}
