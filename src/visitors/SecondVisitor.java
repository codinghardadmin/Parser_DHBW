package visitors;

import java.util.SortedMap;
import java.util.TreeMap;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.SyntaxNode;
import nodes.UnaryOpNode;

public class SecondVisitor implements Visitor {
	
	public SortedMap<Integer, FollowposTableEntry> followposTableEntries = new TreeMap<>();
	
	public SecondVisitor(Visitable root, int positions) {
		DepthFirstIterator.traverse(root, this);
	}
	
	@Override
	public void visit(OperandNode node) {
		followposTableEntries.put(node.position, new FollowposTableEntry(node.position, node.symbol));
	}

	@Override
	public void visit(BinOpNode node) {
		SyntaxNode leftNode = (SyntaxNode)node.left;
		SyntaxNode rightNode = (SyntaxNode)node.right;
		System.out.println(node.operator);
		switch (node.operator) {
		case "°":
			for (int nodepos : leftNode.lastpos) {
				followposTableEntries.get(nodepos).followpos.addAll(rightNode.firstpos);
			}
			break;
		case "+":
			
			break;

		default:
			//throw new RuntimeException("Wrong Unary Symbol");
	}
	}

	@Override
	public void visit(UnaryOpNode node) {
		SyntaxNode subNode = (SyntaxNode)node.subNode;
		
		switch (node.operator) {
			case "*":
				for (int nodepos : subNode.lastpos) {
					followposTableEntries.get(nodepos).followpos.addAll(subNode.firstpos);
				}
				break;
			case "+":
				for (int nodepos : subNode.lastpos) {
					followposTableEntries.get(nodepos).followpos.addAll(subNode.firstpos);
				}
				break;

			default:
				throw new RuntimeException("Wrong Unary Symbol");
		}
	}

}
