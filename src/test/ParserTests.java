package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nodes.SyntaxNode;
import parser.Parser;
import visitors.FirstVisitor;

class ParserTests {
	
	/*
	 * JUnit 5 in Eclipse
	 * SecondVisitorTests
	 * 
	 */

	@Test
	@DisplayName("Test SecondVisitorTests")
	void test() {
		
		//Parser parse = new Parser("((a*)b*)#");
		Parser parse = new Parser("((ab*a)|cd?)#");
		parse = new Parser("((a|b)+(b|c)**d)#");
		SyntaxNode node = parse.Start();
		node.firstpos.clear();
		new FirstVisitor(node);
		System.out.println("aaaaaaaa");
	}
	
}
