package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;

/**
 * Represents an iTunes GalleryApp!
 */
public class GalleryApp extends Application {

    /** {@inheritdoc} */
    @Override
    public void start(Stage stage) {
        TilePane tile = new TilePane();
        tile.setPrefColumns(5);

        for (int loop = 0; loop < 25; loop++) {
            tile.getChildren().add(new Thumbnail());
        }


        VBox pane = new VBox(2, new SearchBar(), tile);

        Scene scene = new Scene(pane);
        stage.setMaxWidth(640);
        stage.setMaxHeight(480);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    } // start

} // GalleryApp
