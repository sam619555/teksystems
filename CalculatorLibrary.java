package test.java.tetksystems.expression.evaluator;

import java.util.Scanner;
import java.util.Stack;

/**
 * Class to evaluate expressions.
 * 
 */
public class CalculatorLibrary {
	
	/**
	 * Operators provided in the reverse order of precedence
	 */
	private static final String acceptableOperators = "-+/*";
	private static final String acceptableOperands = "0123456789";
	

	/**
	 * Method to return the precedence of an operator
	 * 
	 * @param operator
	 * @return
	 * @throws IllegalArgumentException
	 */
	private int getPrecedence(char operator) throws IllegalArgumentException {
		int precedence = 0;
		if (operator == '-' || operator == '+') {
			precedence = 1;
		} else if (operator == '*' || operator == '/') {
			precedence = 2;
		}
		return precedence;
	}

	/**
	 * Method to provide precedence between operators
	 * 
	 * @param firstOperand
	 * @param secondOperand
	 * @return
	 */
	private boolean operatorPrecedence(char firstOperand, char secondOperand) {
		return getPrecedence(firstOperand) >= getPrecedence(secondOperand);
	}

	/**
	 * Method to provide if operator is an acceptable operator
	 * 
	 * @param operator
	 * @return
	 */
	private boolean isAcceptableOperator(char operator) {
		return acceptableOperators.indexOf(operator) >= 0;
	}

	/**
	 * Method to provide if operand is an acceptable operand
	 * 
	 * @param operand
	 * @return
	 */
	private boolean isAcceptableOperand(char operand) {
		return acceptableOperands.indexOf(operand) >= 0;
	}

	
	public static void main(String[] args) {
		Scanner input = null;
		try{
			input = new Scanner(System.in);
			
			System.out.println("Expression to Evaluate: ");
			String userInput = input.nextLine();
			
			CalculatorLibrary calc = new CalculatorLibrary();
			System.out.println(calc.evaluateExpression(userInput));
		}
		finally {
		    if(input!=null)
		        input.close();
		}
	}

	/**
	 * Evaluate provided expression and provide the computed result
	 * 
	 * @param infix
	 * @return
	 */
	public int evaluateExpression(String infix) {
		return evaluatePostfix(convertExpressionToPostfix(infix));
	}

	/**
	 * Convert given expression to PostFix Expression
	 * 
	 * @param givenExpression
	 * @return
	 */
	private String convertExpressionToPostfix(String givenExpression) {
		char[] expressionCharArray = givenExpression.toCharArray();
		StringBuilder postFixExpression = new StringBuilder(givenExpression.length());
		Stack<Character> stack = new Stack<Character>();

		for (char character : expressionCharArray) {
			if (isAcceptableOperator(character)) {
				while (!stack.isEmpty() && stack.peek() != '(') {
					if (!operatorPrecedence(stack.peek(), character)) {
						break;
					} else {
						postFixExpression.append(stack.pop());
					}
				}
				stack.push(character);
			} else if (character == '(') {
				stack.push(character);
			} else if (character == ')') {
				while (!stack.isEmpty() && stack.peek() != '(') {
					postFixExpression.append(stack.pop());
				}
				if (!stack.isEmpty()) {
					stack.pop();
				}
			} else if (isAcceptableOperand(character)) {
				postFixExpression.append(character);
			}else if (character == ' ') {
				postFixExpression.append(character);
			}else{
				System.err.println("operator Not Supported");
				throw new IllegalArgumentException("operator Not Supported");
			}
		}
		while (!stack.empty()) {
			postFixExpression.append(stack.pop());
		}
		return postFixExpression.toString();
	}

	/**
	 * Evaluate the postFix expression and provide the computation result
	 * 
	 * @param postFixExpr
	 * @return
	 */
	private int evaluatePostfix(String postFixExpr) {
		Stack<Integer> stack = new Stack<Integer>();
		try{
			char[] postFixExpression = postFixExpr.toCharArray();
			for (char character : postFixExpression) {
				if (isAcceptableOperand(character)) {
					stack.push(character - '0'); // convert char to int val
				} else if (isAcceptableOperator(character)) {
					int firstOperand = stack.pop();
					int secondOperand = stack.pop();
					int result;
					switch (character) {
					case '*':
						result = firstOperand * secondOperand;
						stack.push(result);
						break;
					case '/':
						result = secondOperand / firstOperand;
						stack.push(result);
						break;
					case '+':
						result = firstOperand + secondOperand;
						stack.push(result);
						break;
					case '-':
						result = secondOperand - firstOperand;
						stack.push(result);
						break;
					}
				}
			}
		}catch(Exception exception){
			System.err.println("Provided Expression is not Supported");
			throw new IllegalArgumentException("Provided Expression is not Supported");
		}
		return stack.pop();
	}

}