package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nodes.SyntaxNode;
import parser.Parser;
import visitors.FirstVisitor;
import visitors.FollowposTableEntry;
import visitors.SecondVisitor;
import visitors.Visitable;

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
		//parse = new Parser("((a|b)+(b|c)*d)#");
		parse = new Parser("((a|b)+(b|c)*d)#");
		
		parse = new Parser("((a|b)*abb)#");
		Visitable node = parse.Start();
		//node.firstpos.clear();
		FirstVisitor visitor1 = new FirstVisitor(node);
		
		SecondVisitor visitor2 = new SecondVisitor(node, 6);
		
		for (int index : visitor2.followposTableEntries.keySet()) {
			FollowposTableEntry entry = visitor2.followposTableEntries.get(index);
			
			String s = "{";
			for (int x : entry.followpos) {
				s += " " + x;
			}
			s += "}";
			
			System.out.printf("Symbol: %s  Position: %s, FollowPosSet: %s", entry.symbol, entry.position, s);
			System.out.println();
		}
		
		System.out.println("aaaaaaaa");
	}
	
}
