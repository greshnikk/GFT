package translator;

import java.util.Stack;

public class Translator {
	public static float calculate(String formula, float[] variables)
			throws Exception {
		Stack<Float> st = new Stack<>();
		Stack<Integer> functions = new Stack<>();
		String[] lexems = formula.split(" ");
		float answer = 0;

		for (int i = 0; i < lexems.length; ++i) {
			Main.log("Working with " + i + " lexeme");
			if (lexems[i].startsWith("N") || lexems[i].startsWith("C")) {
				st.push(LexicalAnalysis.lexemeToFloat(lexems[i]));
			}
			else if (lexems[i].startsWith("I")) {
				st.push(variables[Integer.parseInt(lexems[i].substring(1))]);
			}
			else if (lexems[i].startsWith("O")) {
				int ID = Integer.parseInt(lexems[i].substring(1));
				float b = st.pop();
				float a = st.pop();
				switch (ID) {
				case 1: // "+"
					st.push(a + b);
					break;
				case 2: // "-"
					st.push(a - b);
					break;
				case 3: // "*"
					st.push(a * b);
					break;
				case 4: // "/"
					st.push(a / b);
					break;
				case 5: // "^"
					st.push((float) Math.pow(a, b));
					break;
				}
			}
			else if (lexems[i].startsWith("Fn")) {
				int count = Integer.parseInt(lexems[i].substring(2));
				int total = count;
				float[] operands = new float[total];
				
				while (count > 1) {
					operands[count-- - 2] = st.pop();
				}
				int functionID = functions.pop();
				switch (functionID) {
				case 1: //ABS
					st.push(Math.abs(operands[0]));
					break;
				case 2: //COS
					st.push((float) Math.cos(operands[0]));
					break;
				case 3: //EXP
					st.push((float) Math.exp(operands[0]));
					break;
				case 4: //ln
					st.push((float) Math.log(operands[0]));
					break;
				case 5: //lg
					st.push((float) Math.log10(operands[0]));
					break;
				case 6: //MAX
					st.push(Math.max(operands[0], operands[1]));
					break;
				case 7: //MIN
					st.push(Math.min(operands[0], operands[1]));
					break;
				case 8: // TODO Add function N realization.
					break;
				case 9: //POWER
					st.push((float) Math.pow(operands[0], operands[1]));
					break;
				case 10: // TODO Add function R realization.
					break;
				case 11: //TODO add function ROOT realization.
					break;
				case 12: //TODO Remove unnecessary operation SIGN.
					break;
				case 13: //SIN
					st.push((float) Math.sin(operands[0]));
					break;
				case 14: //TODO Add function SQR realization.
					break;
				case 15: //SQRT
					st.push((float) Math.sqrt(operands[0]));
					break;
				case 16: //TAN
					st.push((float) Math.tan(operands[0]));
					break;
				}
			}
			else if (lexems[i].startsWith("F")) {
				functions.push(Integer.parseInt(lexems[i].substring(1)));
			}			
		}
		if (st.size() != 1) throw new Exception ("Something went wrong during calculating formula.");
		answer = st.pop();
		return answer;
	}
}
