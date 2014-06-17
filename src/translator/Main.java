package translator;

public class Main {
	private static final boolean debug = true;

	public static void main(String[] args) {
		try {
			LexicalAnalysis test = new LexicalAnalysis();
			SemanticAnalysis test2 = new SemanticAnalysis();
			String a = test
					.convert("2X1^2 - ( 3+ 2)X2^2 -333.1415X1X2e^2X1sin(X1) + 0.17E-27*PI*lg(10)+ 12E13");
			
			//We should delete all spaces from output string.. (We don't need ' ' separator here)
			//We should not use here the code below !!!... just a hotfix that should be refactored!!!
			//TODO: Fix code, so we won't use ' ' separator anymore.
			Main.log("Answer before fixing: " + a);
			a = a.replaceAll(" S4", "");
			a = a.replaceAll("S4 ", "");
			//Removing last symbol.
			a = a.substring(0, a.length() - 1);
			Main.log("Answer after fixing: " + a);
			//*** END OF HOTFIX ***//
			
			String b = test2.convert(a);
		} catch (NumberFormatException e) {
			Main.log("Wrong number format. " + e.getMessage());
		} catch (Exception e) {
			Main.log("Exception: " + e.getMessage());
		}

	}

	public static void log(Object input) {
		if (debug)
			System.out.println(input);
	}

}
