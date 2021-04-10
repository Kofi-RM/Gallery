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
        Menu ass = new Menu("Ass Cakes");
        menu.prefWidth(150);
        menu.minWidth(130);
        menu.setMinWidth(640);
        menu.maxWidth(200);
        System.out.println(menu.getWidth());
        System.out.println(menu.getMinWidth());
        //System.out.println(USE_PREF_SIZE);
        menu.getMenus().add(ass);
        TilePane tile = new TilePane();
        tile.setPrefColumns(5);

        for (int loop = 0; loop < 25; loop++) {
            tile.getChildren().add(new Thumbnail());
        }

        HBox mun = new HBox(menu);
        VBox pane = new VBox(2, mun, new SearchBar(), tile);

        Scene scene = new Scene(pane);
        stage.setMaxWidth(640);
        stage.setMaxHeight(480);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    } // start

} // GalleryApp
