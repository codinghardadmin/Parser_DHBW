package parser;

public class FirstVisitor implements Visitor {
	
	private int positionCounter;
	
	public FirstVisitor(Visitable root) {
		DepthFirstIterator.traverse(root, this);
		positionCounter = 1;
	}
	
	@Override
	public void visit(OperandNode node) {
		
	}

	@Override
	public void visit(BinOpNode node) {
		SyntaxNode leftNode = (SyntaxNode)node.left;
		SyntaxNode rightNode = (SyntaxNode)node.right;

		switch (node.operator) {
			case "|":
				break;
			case "*":
				break;
			default:
				throw new RuntimeException("Wrong Operator Symbol");
		}
	}

	@Override
	public void visit(UnaryOpNode node) {
		SyntaxNode subNode = (SyntaxNode)node.subNode;
		
		switch (node.operator) {
			case "*":
				break;
			case "+":
				break;
			case "?":
				break;
			default:
				throw new RuntimeException("Wrong Unary Symbol");
		}
	}

}
