package test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.UnaryOpNode;
import parser.Parser;
import visitors.Visitable;

class ParserTests {
	
	/*
	 * JUnit 5 in Eclipse
	 * ParserTests
	 * 
	 */

	@Test
	@DisplayName("Test Parser correct")
	void testCorrect() {
		// ((a|b)*)#
		String regex = "((a|b)*)#";
		Parser parser = new Parser(regex);
		Visitable tree1 = parser.Start();
		
		
		Visitable tree2 = new BinOpNode("°", 
							new UnaryOpNode("*", 
									new BinOpNode("|", 
											new OperandNode("a"), 
											new OperandNode("b"))), 
						new OperandNode("#"));
		
		assertTrue(Parser.equals(tree1, tree2));
	}
	
	@Test
	@DisplayName("Test Parser fail")
	void testFail() {
		boolean f = false;
		
		String regex = "((a|b)**)#";
		Parser parser = new Parser(regex);
		
		try {
			parser.Start();
		} catch (RuntimeException ex) {
			f = true;
		}
		
		assertTrue(f);
	}
	
}
