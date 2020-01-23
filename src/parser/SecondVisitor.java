package parser;

public class SecondVisitor implements Visitor {
	
	public SecondVisitor(Visitable root) {
		DepthFirstIterator.traverse(root, this);
	}
	
	@Override
	public void visit(OperandNode node) {
		
	}

	@Override
	public void visit(BinOpNode node) {
		
	}

	@Override
	public void visit(UnaryOpNode node) {
		SyntaxNode subNode = (SyntaxNode)node.subNode;
		
		switch (node.operator) {
			case "*":
				node.nullable = true;
				
				node.firstpos.addAll(subNode.firstpos);
				
				node.lastpos.addAll(subNode.lastpos);
				break;
			case "+":
				node.nullable = subNode.nullable;
				
				node.firstpos.addAll(subNode.firstpos);
				
				node.lastpos.addAll(subNode.lastpos);
				break;
			case "?":
				node.nullable = true;
				
				node.firstpos.addAll(subNode.firstpos);
				
				node.lastpos.addAll(subNode.lastpos);
				break;
			default:
				throw new RuntimeException("Wrong Unary Symbol");
		}
	}

}
