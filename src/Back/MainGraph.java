package Back;

import java.util.ArrayList;

public class MainGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node zero = new Node("0");
		Node one = new Node("1");
		Node two = new Node("2");
		Node three = new Node("3");
		Node four = new Node("4");
		Node five = new Node("5");
		Node six = new Node("6");
		//Node end = new Node(99);
		
		new Edge(zero,one,1);
		new Edge(one,two,1);
		new Edge(two,three,1);
		new Edge(three,five,1);
		new Edge(four,five,1);
		new Edge(five,six,1);
		new Edge(one,four,1);
		new Edge(three,two,-1);
		new Edge(five,three,-1);
		new Edge(four,four,-1);
		new Edge(five,one,-1);
		
		ArrayList<Node> nodes= new ArrayList<Node>();
		nodes.add(zero);
		nodes.add(one);
		nodes.add(two);
		nodes.add(three);
		nodes.add(four);
		nodes.add(five);
		nodes.add(six);
		//nodes.add(end);
		
		GraphSolver solver = new GraphSolver(nodes);
		solver.getForwardPaths(six);
		solver.getLoops();
		solver.getUntouchedLoops();
		System.out.println("Transfer Function = "+solver.transferFunction());
	}

}
/*test 1
Node zero = new Node(0);
Node one = new Node(1);
Node two = new Node(2);
Node three = new Node(3);
Node four = new Node(4);
Node five = new Node(5);

new Edge(zero,one,1);
new Edge(one,two,1);
new Edge(two,three,1);
new Edge(three,four,1);
new Edge(three,three,-1);
new Edge(three,two,-1);
new Edge(two,one,-1);
new Edge(one,three,1);
new Edge(one,four,1);
new Edge(four,five,1);

ArrayList<Node> nodes= new ArrayList<Node>();
nodes.add(zero);
nodes.add(one);
nodes.add(two);
nodes.add(three);
nodes.add(four);
nodes.add(five);*/

/* test 2
Node zero = new Node(0);
Node one = new Node(1);
Node two = new Node(2);
Node three = new Node(3);
Node four = new Node(4);

new Edge(zero,one,1);
new Edge(one,two,1);
new Edge(two,three,1);
new Edge(three,four,1);
new Edge(zero,two,1);
new Edge(three,two,-1);
new Edge(three,one,-1);


ArrayList<Node> nodes= new ArrayList<Node>();
nodes.add(zero);
nodes.add(one);
nodes.add(two);
nodes.add(three);
nodes.add(four);
*/

/* test 3
Node zero = new Node(0);
Node one = new Node(1);
Node two = new Node(2);
Node three = new Node(3);
Node four = new Node(4);
Node five = new Node(5);

new Edge(zero,one,1);
new Edge(one,two,1);
new Edge(two,three,1);
new Edge(three,four,1);
new Edge(four,five,1);
new Edge(one,three,1);
new Edge(two,one,-1);
new Edge(four,three,-1);
new Edge(four,one,-1);

ArrayList<Node> nodes= new ArrayList<Node>();
nodes.add(zero);
nodes.add(one);
nodes.add(two);
nodes.add(three);
nodes.add(four);
nodes.add(five);*/


/* test 4
Node zero = new Node(0);
Node one = new Node(1);
Node two = new Node(2);
Node three = new Node(3);
Node four = new Node(4);
Node five = new Node(5);
Node six = new Node(6);

new Edge(zero,one,1);
new Edge(one,two,1);
new Edge(two,three,1);
new Edge(three,five,1);
new Edge(four,five,1);
new Edge(five,six,1);
new Edge(one,four,1);
new Edge(three,two,-1);
new Edge(five,three,-1);
new Edge(four,four,-1);
new Edge(five,one,-1);

ArrayList<Node> nodes= new ArrayList<Node>();
nodes.add(zero);
nodes.add(one);
nodes.add(two);
nodes.add(three);
nodes.add(four);
nodes.add(five);
nodes.add(six);*/
