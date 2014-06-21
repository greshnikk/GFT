package translator;

import java.util.*;

/**
 * @author Greshnikk
 * @version 0.01
 * @since 2014-05-24
 */
public class SemanticAnalysis {
	private static HashtableExt<Integer, String> charConstants = GFT
			.getCharTable();
	private static HashtableExt<Integer, Float> numberConstants = GFT
			.getNumberTable();
	private static HashtableExt<Integer, String> operations = GFT
			.getOperationsTable();
	private static HashtableExt<Integer, Character> separators = GFT
			.getSeparatorsTable();
	private static HashtableExt<Integer, String> identifiers = GFT
			.getIdentifiersTable();
	private static HashtableExt<Integer, String> functions = GFT
			.getFunctionsTable();

	private final static String EMPTY_STRING = "";
	private final static String SPACE = " ";

	public static String convert(String input) throws Exception {
		String output = EMPTY_STRING;
		Stack<String> st = new Stack<String>();
		String[] lexems = input.split(SPACE);
		String prev = EMPTY_STRING;

		for (int i = 0; i < lexems.length; ++i) {
			if (lexems[i].startsWith("N") || lexems[i].startsWith("I") || lexems[i].startsWith("C")) {
				output += lexems[i] + SPACE;
				continue;
			}
			else if (lexems[i].startsWith("F")) {				
				output += lexems[i] + SPACE;
			}
			else if (lexems[i].equals("S1")) { // S1 = '(' TODO: Add method stringToLexeme
										// for not using "S1"
				if (prev.startsWith("F")) {
					st.push("Fn1");
				} else {
					st.push("S1");
				}
			}
			else if (lexems[i].equals("S3")) { // S3 == ','
				while (!st.lastElement().startsWith("Fn")) {
					output += st.pop() + SPACE;
				}
				int counter = Integer.parseInt(st.pop().substring(2));
				st.push("Fn" + ++counter);
			}
			else if (lexems[i].equals("S2")) { // S2 == ')'
				while (!st.lastElement().startsWith("Fn") && !st.lastElement().equals("S1")) {
					output += st.pop() + SPACE;
				}
				if (st.lastElement().equals("S1")) {
					st.pop();
				}
				else {
					int counter = Integer.parseInt(st.pop().substring(2));
					output += "Fn" + ++counter + SPACE;
				}
			}
			else if (st.isEmpty()) {
				st.push(lexems[i]);
			}
			else if (GFT.getPriority(st.lastElement()) < GFT.getPriority(lexems[i])) {
				st.push(lexems[i]);
			}
			else {
				while (!st.empty() && GFT.getPriority(st.lastElement()) >= GFT.getPriority(lexems[i])) {
					output += st.pop() + SPACE;
				}
				st.push(lexems[i]);
			}		
			prev = lexems[i];
		}
		while (!st.empty()) {
			output += st.pop() + SPACE;
		}
		//TODO: Rework method so we won't need to delete last SPACE...
		output = output.substring(0, output.length() - 1);
		Main.log("OPZ form: " + output);
		return output;
	}
}