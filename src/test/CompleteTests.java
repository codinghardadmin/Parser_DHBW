package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.SortedMap;
import java.util.TreeMap;

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
	 * CompleteTests
	 * 
	 */

	@Test
	@DisplayName("Test: Complete Test")
	void test() {
		// ((a|b)*abb)#
		
		// Parser und AST erstellen
		Parser parser;
		parser = new Parser("((a|b)*abb)#");
		Visitable root = parser.Start();
		
		// FirstVisitor durchwandert AST
		FirstVisitor visitor1 = new FirstVisitor();
		visitor1.startVisitor(root);
		
		// SecondVisitor erstellt FollowPosTabelle
		SecondVisitor visitor2 = new SecondVisitor();
		SortedMap<Integer, FollowposTableEntry> followPosTableEntries = visitor2.startVisitor(root);
		
		// Erwartete Tabelle erstellen
		SortedMap<Integer, FollowposTableEntry> expected = new TreeMap<Integer, FollowposTableEntry>();
		
		FollowposTableEntry entry1 = new FollowposTableEntry(1, "a");
		entry1.followpos.add(1);
		entry1.followpos.add(2);
		entry1.followpos.add(3);
		
		FollowposTableEntry entry2 = new FollowposTableEntry(2, "b");
		entry2.followpos.add(1);
		entry2.followpos.add(2);
		entry2.followpos.add(3);
		
		FollowposTableEntry entry3 = new FollowposTableEntry(3, "a");
		entry3.followpos.add(4);
		
		FollowposTableEntry entry4 = new FollowposTableEntry(4, "b");
		entry4.followpos.add(5);
		
		FollowposTableEntry entry5 = new FollowposTableEntry(5, "b");
		entry5.followpos.add(6);
		
		FollowposTableEntry entry6 = new FollowposTableEntry(6, "#");
		
		expected.put(1, entry1);
		expected.put(2, entry2);
		expected.put(3, entry3);
		expected.put(4, entry4);
		expected.put(5, entry5);
		expected.put(6, entry6);
		
		
		// Erwartete und erzeugt Tabelle vergleichen. Wenn gleich, ist der Test erfolgreich
		assertEquals(expected, followPosTableEntries);
	}
	
}
