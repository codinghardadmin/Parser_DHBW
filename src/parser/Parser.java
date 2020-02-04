package parser;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.SyntaxNode;
import nodes.UnaryOpNode;

public class Parser {

	private int position;
	private final String eingabe;
	private SyntaxNode x;
	
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
	
	public SyntaxNode Start() {
		switch(eingabe.charAt(position)) {
		case '#': 
			match('#');
			assertEndOfInput();
			
			return new OperandNode("#");
			
		case '(': 
			match('(');
			SyntaxNode leaf = new OperandNode("#");
			SyntaxNode regexpparam = null;
			SyntaxNode regexpreturn = RegExp(regexpparam);
			SyntaxNode root = new BinOpNode("°", regexpreturn , leaf);
			match(')');
			match('#');
			assertEndOfInput();
			x = root;
			return root;
		default:
			throw new RuntimeException("Syntax error !");
		}
	}
	
	public SyntaxNode RegExp(SyntaxNode p) {
		SyntaxNode termpa = null;
		SyntaxNode ret = Term(termpa);
		return RE_(ret);
	}
	
	private SyntaxNode RE_(SyntaxNode p) {
		switch(eingabe.charAt(position)) {
		case '|': 
			match('|');
			SyntaxNode termparam = null;
			SyntaxNode termret = Term(termparam);
			SyntaxNode root = new BinOpNode("|", p, termret);
			return RE_(root);
			//break;
		}
		
		return p;
	}

	private SyntaxNode Term(SyntaxNode pa) {
		if (eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')') {
			return pa;
		} else {
			SyntaxNode factorparam = null;
			SyntaxNode factorret = Factor(factorparam);
			SyntaxNode paramforterm;
			if (pa != null) {
				SyntaxNode root = new BinOpNode("°", pa, factorret);
				paramforterm = root;
			} else {
				paramforterm = factorret;
			}
			return Term(paramforterm);
		}
	}
	
	private SyntaxNode Factor(SyntaxNode pa) {
		SyntaxNode p = null;
		SyntaxNode ret = Elem(p);
		return HOp(ret);
	}
	
	private SyntaxNode HOp(SyntaxNode p) {
		switch(eingabe.charAt(position)) {
		case '*': 
			match('*');
			return new UnaryOpNode("*", p);
		case '+': 
			match('+');
			return new UnaryOpNode("+", p);
		case '?': 
			match('?');
			return new UnaryOpNode("?", p);
		}
		return p; // Unsicher
	}
	
	private SyntaxNode Elem(SyntaxNode pa) {
		if (eingabe.charAt(position) == '(') {
			match('(');
			SyntaxNode p = null;
			SyntaxNode ret = RegExp(p);
			match(')');
			return ret;
		} else {
			SyntaxNode p = null;
			return Alphanum(p);
		}
	}
	
	private SyntaxNode Alphanum(SyntaxNode parameter) {
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
