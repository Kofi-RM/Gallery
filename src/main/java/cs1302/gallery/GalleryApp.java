package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * Represents an iTunes GalleryApp!
 */
public class GalleryApp extends Application {

    /** {@inheritdoc} */
    @Override
    public void start(Stage stage) {
        MenuBar menu = new MenuBar();
        Menu music = new Menu("Find Music");
        menu.setMinWidth(640);

        menu.getMenus().add(music);
        TilePane tile = new TilePane();
        tile.setPrefColumns(5);
        tile.setMinWidth(300);
        tile.setMaxWidth(640);
        tile.setMinHeight(300);

        for (int loop = 0; loop < 25; loop++) {
            tile.getChildren().add(new Thumbnail());
        }

        HBox mun = new HBox(menu);
        VBox pane = new VBox(2, mun, new SearchBar(), tile, new Progress());

        Scene scene = new Scene(pane);
        stage.setMaxWidth(620);
        stage.setMaxHeight(600);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();
        //stage.setMaximized(true);
        stage.show();
    } // start

} // GalleryApp
