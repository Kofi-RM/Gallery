package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;

/**
 * Represents an iTunes GalleryApp!
 */
public class GalleryApp extends Application {

    public ImageView[] array = new ImageView[25];

    /** {@inheritdoc} */
    @Override
    public void start(Stage stage) {

        MenuBar menu = new MenuBar();
        Menu music = new Menu("Find Music");
        menu.setMinWidth(640);
        menu.setMaxWidth(900);
        menu.getMenus().add(music);


        Thread task = new Thread(() -> {
            System.out.println("start");

            System.out.println("end");
        });
        task.start();

        System.out.println("yo");


        HBox mun = new HBox(menu);
        VBox pane = new VBox(2, mun, new SearchBar(this), tile, new Progress());
        pane.setMinWidth(300);
        pane.setMaxWidth(900);

        Scene scene = new Scene(pane);
        stage.setMaxWidth(920);
        stage.setMaxHeight(600);
        stage.setMinWidth(420);
        stage.setMinHeight(400);
        System.out.println(stage.getMinWidth());
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();

        System.out.println(icons.getHeight());
        //stage.setMaximized(true);
        stage.show();
        //stage.setResizable(false);
        } // start

    public void hbox(HBox box) {
        for (int loop = 0; loop < 5; loop++) {
            Thumbnail icon = new Thumbnail();
            box.getChildren().add(icon);
            array[loop + 15] = icon;
        }

    }

} // GalleryApp
