package Back;

public class Edge {



	public Edge(Node from, Node to, int gain) {
		from.addTo(to,gain);
	}

}
