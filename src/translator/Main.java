package translator;

public class Main {

	public static void main(String[] args) {		
		try {
			LexicalAnalysis test = new LexicalAnalysis();
			test.convert("2X1^2 - ( 3+ 2)X2^2 -333.1415X1X2e^2X1sin(X1) + 0.17E-27*PI*lg(10)+ 12E13");
		} catch (NumberFormatException e) {
			Main.log("Wrong number format. " + e.getMessage());
		} catch (Exception e) {
			Main.log("Exception: " + e.getMessage());
		}

	}

	public static void log(Object input) {
		System.out.println(input);
	}

}
