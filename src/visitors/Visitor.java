package visitors;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.UnaryOpNode;

public interface Visitor {
	
	public void visit(OperandNode node);
	public void visit(BinOpNode node);
	public void visit(UnaryOpNode node);
	
}
