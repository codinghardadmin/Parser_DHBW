package parser;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.UnaryOpNode;
import visitors.Visitable;

public class Parser {

	private int position;
	private final String eingabe;
	
	public Parser(String eingabe) {
		this.eingabe = eingabe;
		this.position = 0;
	}
	
	private void match(char symbol) {
		if ((eingabe == null) || ("".equals(eingabe))) {
			throw new RuntimeException("Syntax error !");
		}
		
		if (position >= eingabe.length()) {
			throw new RuntimeException("End of input reached !");
		}
		
		if (eingabe.charAt(position) != symbol) {
			throw new RuntimeException("Syntax error !");
		}
		
		System.out.println("Matched: " + symbol);
		
		position++;
	}
	
	public Visitable Start() {
		switch(eingabe.charAt(position)) {
		case '#': 
			match('#');
			assertEndOfInput();
			return new OperandNode("#");
		case '(': 
			match('(');
			Visitable leaf = new OperandNode("#");
			Visitable ret = RegExp(null);
			Visitable root = new BinOpNode("°", ret , leaf);
			match(')');
			match('#');
			assertEndOfInput();
			return root;
		default:
			throw new RuntimeException("Syntax error !");
		}
	}
	
	public Visitable RegExp(Visitable parameter) {
		Visitable ret = Term(null);
		return RE_(ret);
	}
	
	private Visitable RE_(Visitable parameter) {
		switch(eingabe.charAt(position)) {
		case '|': 
			match('|');
			Visitable ret = Term(null);
			Visitable root = new BinOpNode("|", parameter, ret);
			return RE_(root);
		case ')':
			return parameter;
		default:
			throw new RuntimeException("Syntax error !");
		}
	}

	private Visitable Term(Visitable parameter) {
		if (Character.isLetterOrDigit(eingabe.charAt(position)) || eingabe.charAt(position) == '(') {
			Visitable ret = Factor(null);
			Visitable paramforterm = null;
			if (parameter != null) {
				paramforterm = new BinOpNode("°", parameter, ret);
			} else {
				paramforterm = ret;
			}
			return Term(paramforterm);
		} else if (eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')') {
			return parameter;
		} else {
			throw new RuntimeException("Syntax error !");
		}
	}
	
	private Visitable Factor(Visitable parameter) {
		if (Character.isLetterOrDigit(eingabe.charAt(position)) || eingabe.charAt(position) == '(') {
			Visitable ret = Elem(null);
			return HOp(ret);
		} else {
			throw new RuntimeException("Syntax error !");
		}
	}
	
	private Visitable HOp(Visitable parameter) {
		if (eingabe.charAt(position) == '*') {
			match('*');
			return new UnaryOpNode("*", parameter);
		} else if (eingabe.charAt(position) == '+') {
			match('+');
			return new UnaryOpNode("+", parameter);
		} else if (eingabe.charAt(position) == '?') {
			match('?');
			return new UnaryOpNode("?", parameter);
		} else if (Character.isLetterOrDigit(eingabe.charAt(position)) || 
				eingabe.charAt(position) == '(' || 
				eingabe.charAt(position) == '|' || 
				eingabe.charAt(position) == ')') {
			return parameter;
		} else {
			throw new RuntimeException("Syntax error !");
		}
	}
	
	private Visitable Elem(Visitable parameter) {
		if (eingabe.charAt(position) == '(') {
			match('(');
			Visitable ret = RegExp(null);
			match(')');
			return ret;
		} else if (Character.isLetterOrDigit(eingabe.charAt(position))) {
			return Alphanum(null);
		} else {
			throw new RuntimeException("Syntax error !");
		}
	}
	
	private Visitable Alphanum(Visitable parameter) {
		char check = eingabe.charAt(position);
		//System.out.println(check);
		if (Character.isLetterOrDigit(check)) {
			match(check);
		} else {
			throw new RuntimeException("Syntax error !");
		}
		
		return new OperandNode(String.valueOf(check));
	}
	
	//------------------------------------------------------------------
	// 1. wird benoetigt bei der Regel Start -> '(' RegExp ')''#'
	// 2. wird benoetigt bei der Regel Start -> '#'
	// 3. wird sonst bei keiner anderen Regel benoetigt
	//------------------------------------------------------------------

	private void assertEndOfInput() {
		if (position < eingabe.length()) {
			throw new RuntimeException(" No end of input reached !");
		}
	}
	
	public static boolean equals(Visitable v1, Visitable v2) {
		if (v1 == v2)
			return true;
		
		if (v1 == null)
			return false;
		
		if (v2 == null)
			return false;
		
		if (v1.getClass() != v2.getClass())
			return false;
		
		if (v1.getClass() == OperandNode.class) {
			OperandNode op1 = (OperandNode) v1;
			OperandNode op2 = (OperandNode) v2;
			return op1.position == op2.position && op1.symbol.equals(op2.symbol);
		}
		
		if (v1.getClass() == UnaryOpNode.class) {
			UnaryOpNode op1 = (UnaryOpNode) v1;
			UnaryOpNode op2 = (UnaryOpNode) v2;
			return op1.operator.equals(op2.operator) && equals(op1.subNode, op2.subNode);
		}
		
		if (v1.getClass() == BinOpNode.class) {
			BinOpNode op1 = (BinOpNode) v1;
			BinOpNode op2 = (BinOpNode) v2;
			return op1.operator.equals(op2.operator) &&
			equals(op1.left, op2.left) &&
			equals(op1.right, op2.right);
		}
		
		throw new IllegalStateException("Ungueltiger Knotentyp");
	}
}
