package Problems;

import java.util.*;

public class DirectedGraphNode {
	int label;
	List<DirectedGraphNode> neighbors;
	DirectedGraphNode(int x) {
	label = x;
	neighbors = new ArrayList<DirectedGraphNode>();
	}
}
