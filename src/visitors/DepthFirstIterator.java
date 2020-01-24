package visitors;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.UnaryOpNode;

public class DepthFirstIterator {
	
	public static void traverse(Visitable root, Visitor visitor) {
		if (root instanceof OperandNode) {
			root.accept(visitor);
			return;
		}
		
		if (root instanceof BinOpNode) {
			BinOpNode opNode = (BinOpNode) root;
			DepthFirstIterator.traverse(opNode.left, visitor);
			DepthFirstIterator.traverse(opNode.right, visitor);
			opNode.accept(visitor);
			return;
		}
		
		if (root instanceof UnaryOpNode) {
			UnaryOpNode opNode = (UnaryOpNode) root;
			DepthFirstIterator.traverse(opNode.subNode, visitor);
			opNode.accept(visitor);
			return;
		}
		
		throw new RuntimeException("Instance root has a bad type!");
	}
}
