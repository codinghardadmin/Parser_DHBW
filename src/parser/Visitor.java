package parser;

public interface Visitor {
	
	public void visit(OperandNode node);
	public void visit(BinOpNode node);
	public void visit(UnaryOpNode node);
	
}
