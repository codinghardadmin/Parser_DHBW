package test;

import java.util.SortedMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import parser.Parser;
import visitors.FirstVisitor;
import visitors.FollowposTableEntry;
import visitors.SecondVisitor;
import visitors.Visitable;

class CompleteTests {
	
	/*
	 * JUnit 5 in Eclipse
	 * SecondVisitorTests
	 * 
	 */

	@Test
	@DisplayName("Test SecondVisitorTests")
	void test() {
		
		Parser parse = new Parser("((a*)b*)#");
		//Parser parse = new Parser("((ab*a)|cd?)#");
		//parse = new Parser("((a|b)+(b|c)*d)#");
		//parse = new Parser("((a|b)+(b|c)*d)#");
		
		parse = new Parser("((a|b)*abb)#");
		Visitable node = parse.Start();
		//node.firstpos.clear();
		FirstVisitor visitor1 = new FirstVisitor();
		visitor1.startVisitor(node);
		
		SecondVisitor visitor2 = new SecondVisitor();
		SortedMap<Integer, FollowposTableEntry> followPosTableEntries = visitor2.startVisitor(node);
		
		for (int index : followPosTableEntries.keySet()) {
			FollowposTableEntry entry = followPosTableEntries.get(index);
			
			String s = "{";
			for (int x : entry.followpos) {
				s += " " + x;
			}
			s += " }";
			
			System.out.printf("Symbol: %s  Position: %s, FollowPosSet: %s", entry.symbol, entry.position, s);
			System.out.println();
		}
	}
	
}
