/**
 * 
 */
package translator;

/**
 * @author Greshnikk
 * @version 0.01
 * @since 2014-05-28
 */
public class LexicalAnalysis {
	private HashTableExt<Integer, String> operationTable = new HashTableExt<Integer, String>();
	private HashTableExt<Integer, Character> separatorTable = new HashTableExt<Integer, Character>();
	private HashTableExt<Integer, Integer> constantTable = new HashTableExt<Integer, Integer>();
	private HashTableExt<Integer, String> keywordTable = new HashTableExt<Integer, String>();
	
	public LexicalAnalysis() {
		initTables();
	}
	
	public void clearConstantTable() {
		constantTable.clear();
	}
	
	public String convert (String input) {
		String result = "";
		String temp = "";
		int tmp = 0;
		
		for (int i = 0; i < input.length(); ++i) {
			//TODO: Add float recognition
			char inputChar = input.charAt(i);
			if (Character.isDigit(inputChar)) {
				tmp = tmp * 10 + Character.getNumericValue(inputChar);
				if (i + 1 == input.length() || !Character.isDigit(input.charAt(i + 1))) {
					result += "N" + constantTable.size();
					constantTable.put(constantTable.size(), tmp);
					Main.log("Added number: " + tmp);
					tmp = 0;
				}
				continue;
			}
			
			if (isOperation(inputChar)) {
				temp += inputChar;
				if (i + 1 == input.length() || !isOperation(input.charAt(i + 1))) {
					result += "O" + operationTable.searchKey(temp);
					Main.log("Added operation: " + temp);
					temp = "";
				}
				continue;
			}
			
			if (isSeparator(inputChar)) {
				result += "S" + separatorTable.searchKey(inputChar);
				Main.log("Added separator: " + inputChar);
				continue;
			}
			
			if (Character.isLetter(inputChar)) {
				temp += inputChar;
				if (i + 1 == input.length() || !Character.isLetter(input.charAt(i + 1))) {
					Integer key = keywordTable.searchKey(temp);								
					if (key != null) {
						result += "K" + keywordTable.searchKey(temp);
						Main.log("Added keyword: " + temp);						
					}
					temp = "";
				}
			}
		}
		
		Main.log("Converted string is");
		Main.log(result);
		return result;
	}
	
	public void printTables () {
		Main.log("\nPrinting operation table");
		for (int i = 0; i < operationTable.size(); ++i) {
			Main.log(operationTable.get(i));
		}
		
		Main.log("\nPrinting separator table");
		for (int i = 0; i < separatorTable.size(); ++i) {
			Main.log(separatorTable.get(i));
		}
			
		Main.log("\nPrinting constant table");
		for (int i = 0; i < constantTable.size(); ++i) {
			Main.log(constantTable.get(i));
		}
	}
	
	private boolean isOperation (char input) {
		return (operationTable.containsValue(Character.toString(input)));		 
	}
	
	private boolean isSeparator (char input) {
		return (separatorTable.containsValue(input));
	}
	
	private void initTables () {
		int i = 0;
		
		//Init operation table
		operationTable.put(i++, "+");
		operationTable.put(i++, "-");
		operationTable.put(i++, "*");
		operationTable.put(i++, "/");
		operationTable.put(i++, "^");
		operationTable.put(i++, "=");
		operationTable.put(i++, "<");
		operationTable.put(i++, ">");
		operationTable.put(i++, "<>");
		operationTable.put(i++, "<=");
		operationTable.put(i++, ">=");
		
		//Init separator table
		i = 0;
		separatorTable.put(i++, 'E');
		separatorTable.put(i++, 'X');
		separatorTable.put(i++, '.');
		separatorTable.put(i++, '(');
		separatorTable.put(i++, ')');
		separatorTable.put(i++, '|');
		separatorTable.put(i++, ' ');
		
		i = 0;
		keywordTable.put(i++, "PI");
		keywordTable.put(i++, "e");
	}
}
