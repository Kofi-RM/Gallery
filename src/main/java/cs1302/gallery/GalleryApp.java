package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

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

        VBox icons = new VBox();
        HBox i = new HBox();
        i.setAlignment(Pos.CENTER);
        i.setMinWidth(50);

        HBox j = new HBox();
        j.setAlignment(Pos.CENTER);


        HBox k = new HBox();
        k.setAlignment(Pos.CENTER);

        HBox l = new HBox();
        l.setAlignment(Pos.CENTER);


        HBox m = new HBox();
        m.setAlignment(Pos.CENTER);


            for (int loop = 0; loop < 5; loop++) {
                m.getChildren().add(new Thumbnail());
            }

            for (int loop = 0; loop < 5; loop++) {
                 i.getChildren().add(new Thumbnail());
             }

            for (int loop = 0; loop < 5; loop++) {
                 j.getChildren().add(new Thumbnail());
             }

            for (int loop = 0; loop < 5; loop++) {
                 k.getChildren().add(new Thumbnail());
             }

            for (int loop = 0; loop < 5; loop++) {
                 l.getChildren().add(new Thumbnail());
             }


            icons.getChildren().addAll(i,j,k,l,m);


        HBox mun = new HBox(menu);
        VBox pane = new VBox(2, mun, new SearchBar(), icons, new Progress());
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

} // GalleryApp
