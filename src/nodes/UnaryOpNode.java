package nodes;

import visitors.Visitable;
import visitors.Visitor;

public class UnaryOpNode extends SyntaxNode implements Visitable {
	
	public String operator;
	public Visitable subNode;
	
	public UnaryOpNode(String operator, Visitable subNode) {
		this.operator = operator;
		this.subNode = subNode;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
}
