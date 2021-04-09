package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class SearchBar extends HBox {

    Text text = new Text("Search Query:");
    Button pause = new Button("Pause");
    Button update = new Button("Update Images");

    public SearchBar() {
        super(10);

        getChildren.addAll(pause, text, update);
    }
}
