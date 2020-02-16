package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nodes.BinOpNode;
import nodes.OperandNode;
import nodes.UnaryOpNode;
import visitors.FollowposTableEntry;
import visitors.SecondVisitor;

class SecondVisitorTests {
	
	/*
	 * JUnit 5 in Eclipse
	 * SecondVisitorTests
	 * 
	 */

	@Test
	@DisplayName("SecondVisitorTest: FollowPosEntry Tabelle Vergleich")
	void secondVisitorTest() {
		// ((a|b)*)#
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
		
		SecondVisitor visitor2 = new SecondVisitor();
		SortedMap<Integer, FollowposTableEntry> actual = visitor2.startVisitor(root);
		
		
		SortedMap<Integer, FollowposTableEntry> expected = new TreeMap<Integer, FollowposTableEntry>();
		
		FollowposTableEntry entry1 = new FollowposTableEntry(1, "a");
		entry1.followpos.add(1);
		entry1.followpos.add(2);
		entry1.followpos.add(3);
		
		FollowposTableEntry entry2 = new FollowposTableEntry(2, "b");
		entry2.followpos.add(1);
		entry2.followpos.add(2);
		entry2.followpos.add(3);
		
		FollowposTableEntry entry3 = new FollowposTableEntry(3, "#");
		
		expected.put(1, entry1);
		expected.put(2, entry2);
		expected.put(3, entry3);
		
		assertEquals(expected, actual);
	}
	
}
