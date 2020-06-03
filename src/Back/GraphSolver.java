package Back;

import java.util.ArrayList;

public class GraphSolver {

	private ArrayList<Node> nodes;
	private ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> loops = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> untouchedLoops = new ArrayList<ArrayList<Node>>();
	private ArrayList<Node> temp = new ArrayList<Node>();
	private ArrayList<Integer> pathGain = new ArrayList<Integer>();
	private ArrayList<Integer> loopGain = new ArrayList<Integer>();
	private ArrayList<Integer> untouchedLoopsGain = new ArrayList<Integer>();
	
	private ArrayList<ArrayList<String>> loopsName = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> UntouchedLoopsName = new ArrayList<ArrayList<String>>();

	private Node input;
	private Node output;
	private Node loopNode;

	public GraphSolver(ArrayList<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
		this.input = nodes.get(0);
	}

	private void dfsPaths(Node node, int gain) {
		temp.add(node);
		node.setVisited(true);
		if (node.equals(output)) {
			paths.add(new ArrayList<Node>(temp));
			node.setVisited(false);
			temp.remove(node);
			this.pathGain.add(new Integer(gain));
			return;
		}
		for (int i = 0; i < node.getTo().size(); i++) {
			if (!node.getTo().get(i).isVisited()) {
				gain *= node.getGain().get(i);
				dfsPaths(node.getTo().get(i), gain);
				gain /= node.getGain().get(i);
			}
		}
		node.setVisited(false);
		temp.remove(node);
	}

	public void getForwardPaths(Node output) {
		this.output = output;
		int gain = 1;
		this.dfsPaths(input, gain);
		//this.print(paths, pathGain, "paths");
	}

	public void getLoops() {
		for (int i = 0; i < nodes.size(); i++) {
			temp.clear();
			loopNode = nodes.get(i);
			this.dfsLoops(nodes.get(i), 1);
		}
		//this.print(loops, loopGain, "loops");
	}

	private void dfsLoops(Node node, int gain) {
		temp.add(node);
		node.setVisited(true);
		for (int i = 0; i < node.getTo().size(); i++) {
			if (!node.getTo().get(i).isVisited()) {
				gain *= node.getGain().get(i);
				dfsLoops(node.getTo().get(i), gain);
				gain /= node.getGain().get(i);
			} else if (node.getTo().get(i).equals(loopNode)) {
				boolean repeated = false;
				gain *= node.getGain().get(i);
				// temp.add(node.getTo().get(i));
				for (int j = 0; j < loops.size(); j++) {
					if (temp.containsAll(loops.get(j)) && (temp.size() == loops.get(j).size())) {
						repeated = true;
						break;
					}
				}
				if (!repeated) {
					loops.add(new ArrayList<Node>(temp));

					ArrayList<String> nametemp = new ArrayList<String>();
					for (int x = 0; x < temp.size(); x++) {
						nametemp.add(temp.get(x).getName());
					}
					loopsName.add(nametemp);

					node.setVisited(false);
					temp.remove(node);
					// temp.remove(temp.size()-1);
					this.loopGain.add(new Integer(gain));
					return;
				}
			}
		}
		node.setVisited(false);
		temp.remove(node);
	}

	public void getUntouchedLoops() {
		ArrayList<String> temp = new ArrayList<String>();
		boolean untouched;
		for (int i = 0; i < loopsName.size() - 1; i++) {
			for (int j = i + 1; j < loopsName.size(); j++) {
				// temp.clear();
				// temp = new ArrayList<Integer>(loopsName.get(i));
				untouched = true;
				for (int k = 0; k < loops.get(j).size(); k++) {
					// temp.add((loops.get(j).get(k).getName()));
					if (loopsName.get(i).contains(loops.get(j).get(k).getName())) {
						untouched = false;
						break;
					}
				}
				if (untouched) {
					this.temp = new ArrayList<Node>(loops.get(i));
					this.temp.addAll(new ArrayList<Node>(loops.get(j)));
					this.untouchedLoops.add(this.temp);
					temp.clear();
					temp = new ArrayList<String>(loopsName.get(i));
					temp.addAll(new ArrayList<String>(loopsName.get(j)));
					this.UntouchedLoopsName.add(new ArrayList<String>(temp));
					this.untouchedLoopsGain.add(loopGain.get(i) * loopGain.get(j));
				}
			}
		}
		int y=1;
		boolean flag;
		boolean multiple=true;
		while (multiple) {
			multiple=false;
			y*=-1;
			for (int i = 0; i < UntouchedLoopsName.size(); i++) {
				for (int j = 0; j < loopsName.size(); j++) {
					// temp.clear();
					// temp = new ArrayList<Integer>(UntouchedLoopsName.get(i));
					untouched = true;
					for (int k = 0; k < loops.get(j).size(); k++) {
						// temp.add((loops.get(j).get(k).getName()));
						if (UntouchedLoopsName.get(i).contains(loops.get(j).get(k).getName())) {
							untouched = false;
							break;
						}
					}
					flag = true;
					if (untouched) {
						temp.clear();
						temp = new ArrayList<String>(UntouchedLoopsName.get(i));
						temp.addAll(new ArrayList<String>(loopsName.get(j)));
						for (int x = 0; x < UntouchedLoopsName.size(); x++) {
							if (UntouchedLoopsName.get(x).containsAll(temp)) {
								flag = false;
							}
						}
						if (flag) {
							multiple=true;
							this.temp = new ArrayList<Node>(untouchedLoops.get(i));
							this.temp.addAll(new ArrayList<Node>(loops.get(j)));
							this.untouchedLoops.add(this.temp);

							this.UntouchedLoopsName.add(new ArrayList<String>(temp));
							this.untouchedLoopsGain.add(y * untouchedLoopsGain.get(i) * loopGain.get(j));
						}
					}
				}
			}
		}
		//print(untouchedLoops, untouchedLoopsGain, "untouched loops");
	}

	public int untouchedWithPath(ArrayList<Node> path) {
		int sum = 0;
		boolean flag;
		for (int i = 0; i < loops.size(); i++) {
			flag = true;
			for (int j = 0; j < path.size(); j++) {
				if (loops.get(i).contains(path.get(j))) {
					flag = false;
				}
			}
			if (flag) {
				sum += loopGain.get(i);
			}
		}
		return sum;
	}

	/*public void print(ArrayList<ArrayList<Node>> list, ArrayList<Integer> gain, String name) {
		System.out.println("There is " + list.size() + " " + name + ":");
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				System.out.print(list.get(i).get(j).getName() + " , ");

			}
			System.out.println("Gain = " + gain.get(i));
		}
		System.out.println();
	}*/

	public float transferFunction() {
		float gain = 0;
		for (int i = 0; i < paths.size(); i++) {
			gain += pathGain.get(i) * getDelta(paths.get(i));
		}
		gain /= getDelta(new ArrayList<Node>());
		return gain;
	}

	public int getDelta(ArrayList<Node> path) {
		int delta = 1;
		if (path.isEmpty()) {
			for (int i = 0; i < loopGain.size(); i++) {
				delta -= loopGain.get(i);
			}
			for (int i = 0; i < untouchedLoopsGain.size(); i++) {
				delta += untouchedLoopsGain.get(i);
			}
		} else {
			delta -= untouchedWithPath(path);
		}
		return delta;
	}
	
	public ArrayList<ArrayList<Node>> getALLloops () {
		return this.loops;
	}
	
	public ArrayList<ArrayList<Node>> getALLpaths () {
		return this.paths;
	}
	
	public ArrayList<Integer> getLoopGAins () {
		return this.loopGain;
	}
	
	public ArrayList<Integer> getpathGAins () {
		return this.pathGain;
	}
	
	public ArrayList<ArrayList<String>> getNonTouch(){
		return this.UntouchedLoopsName;
	}
	
	public int getDelta (int path) {
		return getDelta(paths.get(path));
	}
}
