package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.UnaryOpNode;
import visitors.Visitable;

class FirstVisitorTests {
	
	/*
	 * JUnit 5 in Eclipse
	 * FirstVisitorTests
	 * 
	 */

	@Test
	@DisplayName("Vergleich zwischen vorgegebenen und erstelltem Baum")
	void compareAST() {
		// Fester Baum ast1
		Visitable ast1 = null;
		
		// Von Visitor besuchter Baum
		Visitable ast2 = null;
		
		assertTrue(equals(ast1, ast2));
		
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
