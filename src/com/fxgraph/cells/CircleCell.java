package com.fxgraph.cells;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import com.fxgraph.graph.Cell;

public class CircleCell extends Cell {

    public CircleCell( String id) {
        super( id);

        /*Circle view = new Circle(30);

        view.setStroke(Color.DODGERBLUE);
        view.setFill(Color.DODGERBLUE);
		*/
        final Circle circle = new Circle(30);
        circle.setStroke(Color.DODGERBLUE);
        circle.setFill(Color.DODGERBLUE);
        final Text text = new Text (id);
        text.setFont(Font.font ("Verdana", 25));
        text.setFill(Color.CADETBLUE);
        final StackPane view = new StackPane();
        view.getChildren().addAll(circle, text);
        setView( view);

    }

}