package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.geometry.Pos;

/**
 * Class that represents a ProgressBar.
 */
public class Progress extends HBox {

    Text text = new Text("Images provided courtesy of Itunes");
    ProgressBar bar = new ProgressBar(0.0);

    /**
     * Progress constructor.
     */

    public Progress() {
        super();
        getChildren().addAll(bar, text);
        setAlignment(Pos.BOTTOM_LEFT);
    }
}
