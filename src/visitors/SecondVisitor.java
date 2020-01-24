package visitors;

import java.util.ArrayList;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.SyntaxNode;
import nodes.UnaryOpNode;

public class SecondVisitor implements Visitor {
	
	// Evtl. HashMap
	private ArrayList<FollowposTableEntry> table;
	
	public SecondVisitor(Visitable root, int positions) {
		DepthFirstIterator.traverse(root, this);
		table = new ArrayList<FollowposTableEntry>();
	}
	
	@Override
	public void visit(OperandNode node) {
		
	}

	@Override
	public void visit(BinOpNode node) {
		switch (node.operator) {
		case "*":
			for (int nodepos : node.lastpos) {
				
			}
			break;
		case "+":
			
			break;

		default:
			throw new RuntimeException("Wrong Unary Symbol");
	}
	}

	@Override
	public void visit(UnaryOpNode node) {
		// SyntaxNode subNode = (SyntaxNode)node.subNode;
		
		switch (node.operator) {
			case "*":
				
				break;
			case "+":
				
				break;

			default:
				throw new RuntimeException("Wrong Unary Symbol");
		}
	}

}
