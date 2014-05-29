/**
 * LexicalAnalysis is a class for taking lexems from input string
 * and passing them to a syntax analyzer.
 */
package translator;

import java.io.IOException;

/**
 * @author Greshnikk
 * @version 0.01
 * @since 2014-05-28
 */
public final class LexicalAnalysis {
	private HashtableExt<Integer, String> charConstants = new HashtableExt<>();
	private HashtableExt<Integer, Float> numberConstants = new HashtableExt<>();
	private HashtableExt<Integer, String> operations = new HashtableExt<>();
	private HashtableExt<Integer, Character> separators = new HashtableExt<>();
	private HashtableExt<Integer, String> identifiers = new HashtableExt<>();
	private HashtableExt<Integer, String> functions = new HashtableExt<>();

	private final static String EMPTY_STRING = "";
	private final static String SPACE = " ";

	public LexicalAnalysis() {
		initTables();
	}

	/**
	 * Clears all necessary tables.
	 */
	public void clearTables() {
		numberConstants.clear();
	}

	/**
	 * Taking lexems from the input string organizing them into output string.
	 * 
	 * @param input
	 *            String to be converted.
	 * @return Converted result as a string.
	 */
	public String convert(String input) throws IOException {
		String result = EMPTY_STRING;
		String currentLexeme = EMPTY_STRING;
		char currentChar;
		int length = input.length();

		if (input.isEmpty())
			return EMPTY_STRING;

		currentChar = input.charAt(0);
		for (int i = 0; i < length;) {
			if (Character.isDigit(currentChar)) {
				do {
					currentLexeme += currentChar;
					if (++i >= length) {
						result += addNumber(currentLexeme);
						Main.log(result);
						return result;
					}
					currentChar = input.charAt(i);
				} while (Character.isDigit(currentChar)
						|| currentChar == '.'
						|| currentChar == 'E'
						|| ((currentChar == '+' || currentChar == '-') && input
								.charAt(i - 1) == 'E'));
				if (isOperation(currentChar) || isSeparator(currentChar)) {
					result += addNumber(currentLexeme);
					currentLexeme = EMPTY_STRING;
				} else if (Character.isLetter(currentChar)) {
					result += addNumber(currentLexeme);
					result += addMultiplyLexeme();
					currentLexeme = EMPTY_STRING;
				}
				continue;
			}
			if (isOperation(currentChar)) {
				result += addOperation(String.valueOf(currentChar));
				if (++i >= length)
					throw new IOException(
							"Check string format (should not end with operation)");
				else
					currentChar = input.charAt(i);
				continue;
			}
			if (isSeparator(currentChar)) {
				result += addSeparator(currentChar);
				if (++i >= length) {
					Main.log(result);
					return result;
				}
				currentChar = input.charAt(i);
				continue;
			}
			if (currentChar == '<' || currentChar == '>') {
				currentLexeme += currentChar;
				if (++i >= length)
					throw new IOException(
							"Check string format (should not end with '>' or '<'");
				else
					currentChar = input.charAt(i);
				if (isOperation(currentChar)) {
					currentLexeme += currentChar;
					if (operations.searchKey(currentLexeme) == null)
						throw new IOException("There is no such operation: "
								+ currentLexeme);
					result += addOperation(currentLexeme);
					if (++i >= length) {
						Main.log(result);
						return result;
					}
				} else
					result += addOperation(currentLexeme);
				currentLexeme = EMPTY_STRING;
				continue;
			}
			if (currentChar == 'X') {
				currentLexeme += currentChar;
				if (++i >= length)
					throw new IOException(
							"Check string format (should not end with 'X')");
				else
					currentChar = input.charAt(i);
				if (!Character.isDigit(currentChar))
					throw new IOException(
							"Check string format (X variable should have index)");
				else
					do {
						currentLexeme += currentChar;
						if (++i >= length) {
							result += addIdentifier(currentLexeme);
							Main.log(result);
							return result;
						}
						currentChar = input.charAt(i);
					} while (Character.isDigit(currentChar));
				result += addIdentifier(currentLexeme);
				currentLexeme = EMPTY_STRING;
				if (Character.isLetter(currentChar))
					result += addMultiplyLexeme();
				continue;
			}
			if (Character.isLetter(currentChar)) {				
				do {
					currentLexeme += currentChar;
					if (++i >= length) {
						addConstant(currentLexeme);
					}
					currentChar = input.charAt(i);
				} while (Character.isLetter(currentChar));
				if (Character.isDigit(currentChar)) {
					result += addConstant(currentLexeme);
					result += addMultiplyLexeme();
					currentLexeme = EMPTY_STRING;
				} else if (isOperation(currentChar) || isSeparator(currentChar)) {
					if (functions.searchKey(currentLexeme.toUpperCase()) != null) {
						result += addFunction(currentLexeme);
						currentLexeme = EMPTY_STRING;
					} else {
						result += addConstant(currentLexeme);
						currentLexeme = EMPTY_STRING;
					}
				}
			}
		}
		Main.log(result);
		return result;
	}

	/**
	 * Returns input function lexeme.
	 * 
	 * @param input Function to be converted.
	 * @return F + function code or null if there is no such function in table.
	 */
	private String addFunction(String input) {
		return "F" + functions.searchKey(input.toUpperCase()) + SPACE;
	}

	/**
	 * Searches table for a character constant and returns it's represent.
	 * 
	 * @param input Character constant to be converted.
	 * @return C + constant code.
	 * @throws IOException Throws an exception, if no constants were found.
	 */
	private String addConstant(String input) throws IOException {
		if (!charConstants.contains(input))
			throw new IOException("There is no such constant as: " + input);
		return "C" + charConstants.searchKey(input) + SPACE;
	}

	/**
	 * Adds input identifier to identifier table if not already added and then
	 * returns it's represent.
	 * 
	 * @param input Identifier to be added/searched for.
	 * @return I + identifier's represent.
	 */
	private String addIdentifier(String input) {
		if (!identifiers.contains(input)) {
			int index = identifiers.size();
			identifiers.put(index, input);
		}
		return "I" + identifiers.searchKey(input) + SPACE;
	}

	/**
	 * Returns input separator lexeme.
	 * 
	 * @param input Separator to be converted.
	 * @return S + separator code or null if there is no such separator in table.
	 */
	private String addSeparator(char input) {
		return "S" + separators.searchKey(input) + SPACE;
	}

	/**
	 * Adds number constant to the table and returns it's lexeme.
	 * 
	 * @param number Input number to be added.
	 * @return N + number code.
	 */
	private String addNumber(String number) {
		int index = numberConstants.size();
		numberConstants.put(index, Float.parseFloat(number));
		return "N" + index + SPACE;
	}

	/**
	 * Searches for multiply lexeme and returns it.
	 * 
	 * @return Multiply operation lexeme.
	 */
	private String addMultiplyLexeme() {
		return addOperation("*");
	}

	/**
	 * Returns input operation lexeme.
	 * 
	 * @param input Operation to be converted.
	 * @return O + operation code or null if there is no such operation in table.
	 */
	private String addOperation(String input) {
		return "O" + operations.searchKey(input) + SPACE;
	}

	/**
	 * Checks whether input character is an operation or not.
	 * 
	 * @param input Character to be checked.
	 * @return True if input character is an operation. Otherwise false.
	 */
	private boolean isOperation(char input) {
		return (operations.containsValue(Character.toString(input)));
	}

	/**
	 * Checks whether input character is a separator or not.
	 * 
	 * @param input Character to be checked.
	 * @return True if input character is a separator. Otherwise false.
	 */
	private boolean isSeparator(char input) {
		return (separators.containsValue(input));
	}

	/**
	 * Initiates tables with start values.
	 * TODO: Add SQL.
	 */
	private void initTables() {
		int i = 0;

		// Init operation table
		operations.put(i++, "+");
		operations.put(i++, "-");
		operations.put(i++, "*");
		operations.put(i++, "/");
		operations.put(i++, "^");
		operations.put(i++, "=");
		operations.put(i++, "<");
		operations.put(i++, ">");
		operations.put(i++, "<>");
		operations.put(i++, "<=");
		operations.put(i++, ">=");

		// Init separator table
		i = 0;
		separators.put(i++, '(');
		separators.put(i++, ')');
		separators.put(i++, '|');
		separators.put(i++, ' ');

		// Init string constants table
		i = 0;
		charConstants.put(i++, "PI");
		charConstants.put(i++, "e");
		
		i = 0;
		functions.put(i++, "ABS");
		functions.put(i++, "COS");
		functions.put(i++, "EXP");
		functions.put(i++, "LN");
		functions.put(i++, "LG");
		functions.put(i++, "MAX");
		functions.put(i++, "MIN");
		functions.put(i++, "N");
		functions.put(i++, "POWER");
		functions.put(i++, "R");
		functions.put(i++, "ROOT");
		functions.put(i++, "SIGN");
		functions.put(i++, "SIN");
		functions.put(i++, "SQR");
		functions.put(i++, "SQRT");
		functions.put(i++, "TAN");
	}
}