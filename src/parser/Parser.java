package parser;

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
		
		position++;
	}
	
	public void Start() {
		switch(eingabe.charAt(position)) {
		case '#': 
			match('#');
			assertEndOfInput();
			break;
		case '(': 
			match('(');
			RegExp();
			match(')');
			match('#');
			assertEndOfInput();
			break;
		default:
			throw new RuntimeException("Syntax error !");
		}
	}
	
	public void RegExp() {
		Term();
		RE_();
	}
	
	private void RE_() {
		switch(eingabe.charAt(position)) {
		case '|': 
			match('|');
			Term();
			RE_();
			break;
		}
	}

	private void Term() {
		FactorTerm();
	}
	
	private void FactorTerm() {
		Elem();
		HOp();
	}
	
	private void HOp() {
		switch(eingabe.charAt(position)) {
		case '*': 
			match('*');
			break;
		case '+': 
			match('+');
			break;
		case '?': 
			match('?');
			break;
		}
	}
	
	private void Elem() {
		if (eingabe.charAt(position) == '(') {
			match('(');
			RegExp();
			match(')');
		} else {
			Alphanum();
		}
	}
	
	private void Alphanum() {
		char check = eingabe.charAt(position);
		if (Character.isLetterOrDigit(check)) {
			match(check);
		} else {
			throw new RuntimeException("Syntax error !");
		}
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
