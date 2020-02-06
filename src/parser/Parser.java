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
		}
		return parameter;
	}

	private Visitable Term(Visitable parameter) {
		if (eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')') {
			return parameter;
		} else {
			Visitable ret = Factor(null);
			Visitable paramforterm = null;
			if (parameter != null) {
				paramforterm = new BinOpNode("°", parameter, ret);
			} else {
				paramforterm = ret;
			}
			return Term(paramforterm);
		}
	}
	
	private Visitable Factor(Visitable parameter) {
		Visitable ret = Elem(null);
		return HOp(ret);
	}
	
	private Visitable HOp(Visitable parameter) {
		switch(eingabe.charAt(position)) {
		case '*': 
			match('*');
			return new UnaryOpNode("*", parameter);
		case '+': 
			match('+');
			return new UnaryOpNode("+", parameter);
		case '?': 
			match('?');
			return new UnaryOpNode("?", parameter);
		}
		return parameter;
	}
	
	private Visitable Elem(Visitable parameter) {
		if (eingabe.charAt(position) == '(') {
			match('(');
			Visitable ret = RegExp(null);
			match(')');
			return ret;
		} else {
			return Alphanum(null);
		}
	}
	
	private Visitable Alphanum(Visitable parameter) {
		char check = eingabe.charAt(position);
		System.out.println(check);
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
}
