package visitors;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.SyntaxNode;
import nodes.UnaryOpNode;

public class FirstVisitor implements Visitor {
	
	private int positionCounter;
	
	public FirstVisitor() { }
	
	public void startVisitor(Visitable root) {
		positionCounter = 1;
		DepthFirstIterator.traverse(root, this);
	}
	
	@Override
	public void visit(OperandNode node) {
		
		switch (node.symbol) {
			case "epsilon":
				node.nullable = true;
				break;
			default:
				node.nullable = false;
				break;
		}
		
		node.position = positionCounter;
		positionCounter++;
		
		node.firstpos.add(node.position);
		
		node.lastpos.add(node.position);
	}

	@Override
	public void visit(BinOpNode node) {
		SyntaxNode leftNode = (SyntaxNode)node.left;
		SyntaxNode rightNode = (SyntaxNode)node.right;

		switch (node.operator) {
			case "|":
				node.nullable = leftNode.nullable || rightNode.nullable;
				
				node.firstpos.addAll(leftNode.firstpos);
				node.firstpos.addAll(rightNode.firstpos);
				
				node.lastpos.addAll(leftNode.lastpos);
				node.lastpos.addAll(rightNode.lastpos);
				break;
			case "°":
				node.nullable = leftNode.nullable && rightNode.nullable;
				
				if (leftNode.nullable) {
					node.firstpos.addAll(leftNode.firstpos);
					node.firstpos.addAll(rightNode.firstpos);
				} else {
					node.firstpos.addAll(leftNode.firstpos);
				}
				
				if (rightNode.nullable) {
					node.lastpos.addAll(leftNode.lastpos);
					node.lastpos.addAll(rightNode.lastpos);
				} else {
					node.lastpos.addAll(rightNode.lastpos);
				}
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
