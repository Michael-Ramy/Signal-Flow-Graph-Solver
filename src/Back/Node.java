package Back;

import java.util.ArrayList;

public class Node {

	private ArrayList<Node> to = new ArrayList<Node>();
	private ArrayList<Integer> gain = new ArrayList<Integer>();
	private String name;
	private boolean visited = false;

	public Node(String name) {
		this.name = name;
	}

	public ArrayList<Node> getTo() {
		return to;
	}

	public ArrayList<Integer> getGain() {
		return gain;
	}

	public void addTo(Node to, int gain) {
		this.to.add(to);
		this.gain.add(gain);
	}

	public String getName() {
		return name;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
