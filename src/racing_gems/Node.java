package racing_gems;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Node implements Comparable<Node> {
	private int value;
	private int maxValue;
	ArrayList<Node> children;
	Point2D.Double loc;

	public Node(int v, double x, double y) {
		value = v;
		maxValue = -1;
		loc = new Point2D.Double(x, y);
		children = new ArrayList<Node>();
	}

	public int getValue() {
		return value;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int v) {
		maxValue = v;
	}

	public Point2D.Double getLoc() {
		return loc;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void addChild(Node n) {
		children.add(n);
	}

	public void removeChild(int i) {
		children.remove(i);
	}

	public boolean canReach(Node goal, double r) {
		if (goal.loc.y > this.loc.y) {
			double distance = Math.abs(this.loc.x - goal.loc.x);
			double hSpeed = (goal.loc.y - this.loc.y) / r;
			if (distance < hSpeed)
				return true;
		}
		return false;
	}

	// gets child with max value, removes children of lower value (like me)
	public int maxChild() {
		int max = 0;
		if (children.isEmpty()) {
			return max;
		} else {
			int maxIndex = 0;
			max = children.get(0).getMaxValue();
			for (int i = 1; i < children.size(); i++) {
				if (children.get(i).getMaxValue() > max) {
					children.remove(maxIndex);
					i--;
					max = children.get(i).getMaxValue();
					maxIndex = i;
				} else {
					children.remove(i);
					i--;
				}
			}
		}
		return max;
	}

	// removes loops by checking if any of the children of this node
	// are also children of a child node
	public void removeLoops() {
		if (!children.isEmpty()) {
			for (int i = 0; i < children.size(); i++) {
				ArrayList<Node> grandchildren = children.get(i).getChildren();
				if (!grandchildren.isEmpty()) {
					for (int j = 0; j < children.size(); j++) {
						for (int k = 0; k < grandchildren.size(); k++) {
							if (grandchildren.get(k).equals(children.get(j))) {
								children.remove(j);
								k = grandchildren.size();
								j--;
							}
						}
					}
				}
			}
		}
	}

	public void print() {
		System.out.println(loc.x + "," + loc.y + "," + value + "," + maxValue);
	}

	public void printWithChildren() {
		System.out.println(loc.x + "," + loc.y + "," + value + "," + maxValue);
		for (Node child : children) {
			System.out.print("     ");
			child.print();
		}
		System.out.println();
	}

	@Override
	public int compareTo(Node n) {
		if (this.loc.y < n.loc.y)
			return -1;
		if (this.loc.y > n.loc.y)
			return 1;
		return 0;
	}

	public boolean equals(Node n) {
		return (this.loc.x == n.loc.x && this.loc.y == n.loc.y);
	}

}
