package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import Back.*;

public class SampleController implements Initializable {

	@FXML
	private AnchorPane scene;

	@FXML
	private Button AddNode;

	@FXML
	private Button AddEdge;

	@FXML
	private BorderPane root;

	@FXML
	private TextField Name;

	@FXML
	private TextField Value;

	@FXML
	private Label XY;

	@FXML
	private Label NOde;

	@FXML
	private Label Tips;

	@FXML
	private Button Gain;

	@FXML
	private Label ORgain;

	@FXML
	private TableView<Table> loops;

	@FXML
	private TableView<Table> paths;

	@FXML
	private TableColumn<Table, String> loop;

	@FXML
	private TableColumn<Table, Integer> loopgain;

	@FXML
	private TableColumn<Table, String> path;

	@FXML
	private TableColumn<Table, Integer> pathgain;

	@FXML
	private TableView<Table> Non;

	@FXML
	private TableColumn<Table, String> NONTOUCHING;

	@FXML
	private TableView<Table> Delta;

	@FXML
	private TableColumn<Table, String> num;

	@FXML
	private TableColumn<Table, Integer> value;

	@FXML
	private Button clear;

	private Graph graph;
	private Model model;
	private boolean Node = false, Edge = false, DOGain = false;
	private ArrayList<String> ID = new ArrayList<>();
	private ArrayList<Node> NODES = new ArrayList<>();
	private ArrayList<Double> X = new ArrayList<>();
	private ArrayList<Double> Y = new ArrayList<>();
	private ArrayList<Integer> Values = new ArrayList<>();
	private int NumEdge = 0;
	private String N1 = "";

	private ObservableList<Table> ls = FXCollections.observableArrayList();
	private ObservableList<Table> ps = FXCollections.observableArrayList();
	private ObservableList<Table> non = FXCollections.observableArrayList();
	private ObservableList<Table> del = FXCollections.observableArrayList();

	private GraphSolver solver;
	private int iterate = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graph = new Graph();
		root.setCenter(graph.getScrollPane());
		model = graph.getModel();
		Tips.setText("");
		loop.setCellValueFactory(new PropertyValueFactory<Table, String>("LP"));
		loopgain.setCellValueFactory(new PropertyValueFactory<Table, Integer>("G"));
		path.setCellValueFactory(new PropertyValueFactory<Table, String>("LP"));
		pathgain.setCellValueFactory(new PropertyValueFactory<Table, Integer>("G"));
		NONTOUCHING.setCellValueFactory(new PropertyValueFactory<Table, String>("LP"));
		num.setCellValueFactory(new PropertyValueFactory<Table, String>("LP"));
		value.setCellValueFactory(new PropertyValueFactory<Table, Integer>("G"));
		loops.setItems(ls);
		paths.setItems(ps);
		Non.setItems(non);
		Delta.setItems(del);
	}

	@FXML
	void ADDEDGE(ActionEvent event) {
		edit();
		if (Value.getText().equals("")) {
			Value.setText("1");
		}
		Node = false;
		Edge = true;
		DOGain = false;
		Tips.setText("Chosse first Node");
	}

	@FXML
	void ADDNODE(ActionEvent event) {
		edit();
		if (Name.getText().equals("")) {
			Name.setText(String.valueOf(iterate));
			iterate++;
			Edge = false;
			DOGain = false;
			Tips.setText("");
			graph.beginUpdate();
			Node = true;
		} else if (ID.contains(Name.getText())) {
			Tips.setText("This Name already exsist");
		} else {
			Edge = false;
			DOGain = false;
			Tips.setText("");
			graph.beginUpdate();
			Node = true;
		}
	}

	@FXML
	void MOUSE(MouseEvent event) {
		XY.setText("X = " + event.getX() + " Y = " + event.getY());
		checkLocations(event);
	}

	@FXML
	void Press(MouseEvent event) {
		if (Node) {
			ID.add(Name.getText());
			NODES.add(new Node(Name.getText()));
			model.addCell(Name.getText());
			Name.setText("");
			graph.endUpdate();
			Node = false;
		} else if (Edge && NumEdge < 2) {
			if ((!(NOde.getText().equals(""))) && NumEdge == 0) {
				Tips.setText("Chosse Second Node");
				N1 = NOde.getText();
				NumEdge++;
			} else if ((!(NOde.getText().equals(""))) && NumEdge == 1) {
				Tips.setText("");
				NumEdge = 0;
				new Edge(getNode(N1), getNode(NOde.getText()), Integer.parseInt(Value.getText()));
				model.addEdge(N1, NOde.getText(), Integer.parseInt(Value.getText()));
				Values.add(Integer.parseInt(Value.getText()));
				Value.setText("");
				N1 = "";
				Edge = false;
				graph.endUpdate();
			}
		} else if (DOGain && (!(NOde.getText().equals("")))) {
			solver = new GraphSolver(NODES);
			solver.getForwardPaths(getNode(NOde.getText()));
			solver.getLoops();
			solver.getUntouchedLoops();
			for (int i = 0; i < solver.getALLloops().size(); i++) {
				ls.add(new Table(convert(solver.getALLloops().get(i)), solver.getLoopGAins().get(i)));
			}
			for (int i = 0; i < solver.getALLpaths().size(); i++) {
				ps.add(new Table(convert(solver.getALLpaths().get(i)), solver.getpathGAins().get(i)));
			}
			for (int i = 0; i < solver.getNonTouch().size(); i++) {
				non.add(new Table((Convert2(solver.getNonTouch().get(i))), 0));
			}
			for (int i = 0; i < solver.getALLpaths().size(); i++) {
				del.add(new Table(String.valueOf(i + 1), solver.getDelta(i)));
			}
			ORgain.setText(String.valueOf(solver.transferFunction()));
			DOGain = false;

			Tips.setText("");
		}

	}

	@FXML
	void Released(MouseEvent event) {
		for (int i = 0; i < model.getAllCells().size(); i++) {
			if (i < X.size()) {
				if (!(X.get(i) == model.getAllCells().get(i).layoutXProperty().doubleValue()
						&& Y.get(i) == model.getAllCells().get(i).layoutYProperty().doubleValue())) {
					X.remove(i);
					X.add(i, model.getAllCells().get(i).layoutXProperty().doubleValue());
					Y.add(i, model.getAllCells().get(i).layoutYProperty().doubleValue());
				}
			} else if (i == X.size()) {
				X.add(i, model.getAllCells().get(i).layoutXProperty().doubleValue());
				Y.add(i, model.getAllCells().get(i).layoutYProperty().doubleValue());
			}

		}
	}

	void checkLocations(MouseEvent event) {
		for (int i = 0; i < X.size(); i++) {
			if ((event.getX() >= X.get(i) * graph.getScale())
					&& (event.getX() <= X.get(i) * graph.getScale() + 60 * graph.getScale())) {
				if ((event.getY() >= Y.get(i) * graph.getScale())
						&& (event.getY() <= Y.get(i) * graph.getScale() + 60 * graph.getScale())) {
					NOde.setText(ID.get(i));
					scene.setCursor(Cursor.HAND);
					break;
				} else {
					NOde.setText("");
					scene.setCursor(Cursor.DEFAULT);
				}
			} else {
				NOde.setText("");
				scene.setCursor(Cursor.DEFAULT);
			}
		}
	}

	@FXML
	void GetGain(ActionEvent event) {
		edit();
		DOGain = true;
		Node = false;
		Edge = false;
		Tips.setText("Chosse end point");
	}

	@FXML
	void clear(ActionEvent event) {
		graph = new Graph();
		root.setCenter(graph.getScrollPane());
		model = graph.getModel();
		Tips.setText("");
		Node = false;
		Edge = false;
		DOGain = false;
		ID.clear();
		NODES.clear();
		X.clear();
		Y.clear();
		Values.clear();
		NumEdge = 0;
		N1 = "";
		ls.clear();
		ps.clear();
		non.clear();
		del.clear();
		Name.setText("");
		Value.setText("");
		ORgain.setText("");
		iterate = 0;
	}

	private void edit() {
		ls.clear();
		ps.clear();
		non.clear();
		del.clear();
	}

	private Node getNode(String str) {
		for (int i = 0; i < ID.size(); i++) {
			if (ID.get(i).equals(str)) {
				return NODES.get(i);
			}
		}
		return null;
	}

	private String convert(ArrayList<Node> test) {
		String res = "";
		for (int i = 0; i < test.size(); i++) {
			res = res + test.get(i).getName() + " ";
		}
		return res;
	}
	
	private String Convert2(ArrayList<String> test) {
		String res = "";
		for(int i = 0; i < test.size(); i++) {
			res = res + "(" + test.get(i) + ")";
		}
		return res;
	}
}
