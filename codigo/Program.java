
public class Program {

	public static void main(String[] args) {
		Stack s = new Stack(20);
		
		for (int i = 0; i < s.size(); ++i) {
			Result r = s.push(i);
			if (r.getErrorCode() == 0) {
				System.out.println("s: " + s);
			} else {
				System.out.println("Error while inserting value.");
			}
		}
		
		for (int i = 0; i < s.size(); ++i) {
			Result r = s.pop();
			if (r.getErrorCode() == 0) {
				System.out.println("removido: " + r.getValue());
				System.out.println("s: " + s);
			} else if (r.getErrorCode() == -1) {
				System.out.println("Pilha vazia, nada foi removido.");
			}
		}
		
	}

}
