package nodes;
import java.util.HashSet;
import java.util.Set;

import visitors.Visitable;

public abstract class SyntaxNode implements Visitable {
	
	public Boolean nullable;
	public final Set<Integer> firstpos = new HashSet<>();
	public final Set<Integer> lastpos = new HashSet<>();
	
}
