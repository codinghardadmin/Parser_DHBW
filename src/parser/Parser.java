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
			Visitable regexpparam = null;
			Visitable regexpreturn = RegExp(regexpparam);
			Visitable root = new BinOpNode("°", regexpreturn , leaf);
			match(')');
			match('#');
			assertEndOfInput();
			return root;
		default:
			throw new RuntimeException("Syntax error !");
		}
	}
	
	public Visitable RegExp(Visitable p) {
		Visitable termpa = null;
		Visitable ret = Term(termpa);
		return RE_(ret);
	}
	
	private Visitable RE_(Visitable p) {
		switch(eingabe.charAt(position)) {
		case '|': 
			match('|');
			Visitable termparam = null;
			Visitable termret = Term(termparam);
			Visitable root = new BinOpNode("|", p, termret);
			return RE_(root);
			//break;
		}
		
		return p;
	}

	private Visitable Term(Visitable pa) {
		if (eingabe.charAt(position) == '|' || eingabe.charAt(position) == ')') {
			return pa;
		} else {
			Visitable factorparam = null;
			Visitable factorret = Factor(factorparam);
			Visitable paramforterm;
			if (pa != null) {
				Visitable root = new BinOpNode("°", pa, factorret);
				paramforterm = root;
			} else {
				paramforterm = factorret;
			}
			return Term(paramforterm);
		}
	}
	
	private Visitable Factor(Visitable pa) {
		Visitable p = null;
		Visitable ret = Elem(p);
		return HOp(ret);
	}
	
	private Visitable HOp(Visitable p) {
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
	
	private Visitable Elem(Visitable pa) {
		if (eingabe.charAt(position) == '(') {
			match('(');
			Visitable p = null;
			Visitable ret = RegExp(p);
			match(')');
			return ret;
		} else {
			Visitable p = null;
			return Alphanum(p);
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
