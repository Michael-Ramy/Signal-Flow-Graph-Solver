package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



public class Table {
	private SimpleIntegerProperty G;
	private SimpleStringProperty LP;
	
	public Table(String lp, int g) {
		this.LP = new SimpleStringProperty(lp);
		this.G = new SimpleIntegerProperty(g);
	}
	
	public Integer getG() {
		return this.G.get();
	}
	
	public String getLP() {
		return this.LP.get();
	}
	
	public void setG(int t) {
		this.G.set(t);;
	}
	
	public void setLP(String str) {
		this.LP.set(str);
	}
}
