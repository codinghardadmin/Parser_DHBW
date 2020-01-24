package parser;

public interface Visitable {
	
	void accept(Visitor visitor);
	
}
