package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.UnaryOpNode;
import parser.Parser;
import visitors.FirstVisitor;
import visitors.Visitable;

class FirstVisitorTests {
	
	/*
	 * JUnit 5 in Eclipse
	 * FirstVisitorTests
	 * 
	 */

	@Test
	@DisplayName("FirstVisitor: Vergleich zwischen vorgegebenen und erstelltem Baum")
	void compareAST() {
		OperandNode nodeA = new OperandNode("a");
		nodeA.nullable = false;
		nodeA.position = 1;
		nodeA.firstpos.add(1);
		nodeA.lastpos.add(1);
		
		OperandNode nodeB = new OperandNode("b");
		nodeB.nullable = false;
		nodeB.position = 2;
		nodeB.firstpos.add(2);
		nodeB.lastpos.add(2);
		
		OperandNode nodeEnd = new OperandNode("#");
		nodeEnd.nullable = false;
		nodeEnd.position = 3;
		nodeEnd.firstpos.add(3);
		nodeEnd.lastpos.add(3);
		
		BinOpNode binNode = new BinOpNode("|", nodeA, nodeB);
		binNode.nullable = false;
		binNode.firstpos.add(1);
		binNode.firstpos.add(2);
		binNode.lastpos.add(1);
		binNode.lastpos.add(2);
		
		UnaryOpNode unaryNode = new UnaryOpNode("*", binNode);
		unaryNode.nullable = true;
		unaryNode.firstpos.add(1);
		unaryNode.firstpos.add(2);
		unaryNode.lastpos.add(1);
		unaryNode.lastpos.add(2);
		
		BinOpNode root = new BinOpNode("°", unaryNode, nodeEnd);
		root.nullable = false;
		root.firstpos.add(1);
		root.firstpos.add(2);
		root.firstpos.add(3);
		root.lastpos.add(3);
		
		String regex = "((a|b)*)#";
		Parser parser = new Parser(regex);
		Visitable parserRoot = parser.Start();
		
		FirstVisitor visitor1 = new FirstVisitor();
		visitor1.startVisitor(parserRoot);
		
		boolean equals = equals(root, parserRoot);
		
		assertTrue(equals);
		
	}

	private boolean equals(Visitable expected, Visitable visited) {
		if (expected == null && visited == null)
			return true;
		if (expected == null || visited == null)
			return false;
		if (expected.getClass() != visited.getClass())
			return false;
		if (expected.getClass() == BinOpNode.class) {
			BinOpNode op1 = (BinOpNode) expected;
			BinOpNode op2 = (BinOpNode) visited;

			return op1.nullable.equals(op2.nullable) && op1.firstpos.equals(op2.firstpos)
					&& op1.lastpos.equals(op2.lastpos) && equals(op1.left, op2.left) && equals(op1.right, op2.right);
		}

		if (expected.getClass() == UnaryOpNode.class) {
			UnaryOpNode op1 = (UnaryOpNode) expected;
			UnaryOpNode op2 = (UnaryOpNode) visited;
			
			return op1.nullable.equals(op2.nullable) && op1.firstpos.equals(op2.firstpos)
					&& op1.lastpos.equals(op2.lastpos) && equals(op1.subNode, op2.subNode);
		}
		if (expected.getClass() == OperandNode.class) {
			OperandNode op1 = (OperandNode) expected;
			OperandNode op2 = (OperandNode) visited;
			
			return op1.nullable.equals(op2.nullable) && op1.firstpos.equals(op2.firstpos)
					&& op1.lastpos.equals(op2.lastpos);
		}
		throw new IllegalStateException(
				String.format("Beide Wurzelknoten sind Instanzen der Klasse %1$s !" + " Dies ist nicht erlaubt!",
						expected.getClass().getSimpleName()));
	}

}
