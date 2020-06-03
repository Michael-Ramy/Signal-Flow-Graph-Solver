package com.fxgraph.graph;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Edge extends Group {

	protected Cell source;
	protected Cell target;
	private int gain;

	QuadCurve curve;
	private Text text;
	private int ys;
	private int ts;
	Circle circle;

	public Edge(Cell source, Cell target, boolean test, int gain) {
		this.gain = gain;
		text = new Text(String.valueOf(gain));
		text.setFont(Font.font ("Verdana", 24));
        text.setFill(Color.BLACK);
        
        this.source = source;
        this.target = target;

        source.addCellChild(target);
        target.addCellParent(source);
        if (source.equals(target)) {
        	ys = source.NumOFRep(target) * 30;
        	circle = new Circle(ys);
        	circle.setCenterX((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)).doubleValue());
        	circle.setCenterY((source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0)).doubleValue() - ys);
        	circle.setFill(null);
        	circle.setStrokeWidth(3);
        	circle.setStroke(Color.BROWN);
        	text.setX((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)).doubleValue() - 5);
        	text.setY((source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0)).doubleValue() - ys);
        	
        	getChildren().add(circle);
        	getChildren().add(text);
        } else {
        	ys = source.NumOFRep(target) * 45;

        	curve = new QuadCurve();
        	double diff = Math.abs((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)).doubleValue() - (target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0)).doubleValue());
        	if ((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)).doubleValue() > (target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0)).doubleValue()) {
        		ys = (int) ((-1 * ys) - (diff / 4));
        		ts = ys + 15;
        		curve.setStroke(Color.BROWN);
        	} else {
        		ys = (int) (ys + (diff / 4));
        		ts = ys - 15;
        		if (test) {
        			ys = 0;
        			ts = 0;
        		}
        		curve.setStroke(Color.CHARTREUSE);
        	}
        
        	curve.setStrokeWidth(5);
        	curve.setFill(null);
        
        	curve.setStartX((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)).doubleValue());
        	curve.setStartY((source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0)).doubleValue());
        
        	curve.setControlX(((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)).doubleValue()+(target.layoutXProperty().add(target.getBoundsInParent().getWidth() / 2.0)).doubleValue())/2);
        	curve.setControlY((((source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0)).doubleValue()+(target.layoutYProperty().add(target.getBoundsInParent().getHeight() / 2.0)).doubleValue())/2) - ys);
        
        	curve.setEndX((target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0)).doubleValue());
        	curve.setEndY((target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0)).doubleValue());
        	
        	text.setX(((source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0)).doubleValue()+(target.layoutXProperty().add(target.getBoundsInParent().getWidth() / 2.0)).doubleValue())/2);
        	text.setY((((source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0)).doubleValue()+(target.layoutYProperty().add(target.getBoundsInParent().getHeight() / 2.0)).doubleValue())/2) - ts);
        	getChildren().add(curve);
        	getChildren().add(text);
        }
    }

	public Cell getSource() {
		return source;
	}

	public Cell getTarget() {
		return target;
	}

	public void RemoveEdge() {
		getChildren().remove(curve);
		getChildren().remove(circle);
		getChildren().remove(text);
	}
	
	public int getGain() {
		return this.gain;
	}

}