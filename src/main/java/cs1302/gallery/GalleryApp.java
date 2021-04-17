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
import javafx.geometry.Insets;

/**
 * Represents an iTunes GalleryApp!
 */
public class GalleryApp extends Application {

    public ImageView[] array = new ImageView[25];
    TilePane pane = new TilePane();

    /** {@inheritdoc} */
    @Override
    public void start(Stage stage) {

        MenuBar menu = new MenuBar();
        Menu music = new Menu("Find Music");
        menu.setMinWidth(920);
        menu.setMaxWidth(1000);
        menu.getMenus().add(music);


        Thread task = new Thread(() -> {
            System.out.println("start");
            pane = new TilePane();
            pane.setPrefColumns(5);
            pane.setPadding(new Insets(20));

            System.out.println("end");
        });
        task.start();

        for (int loop = 0; loop < 20; loop++) {
            ImageView k = new Thumbnail();

            pane.getChildren().add(k);
            array[loop] = k;
        }


        System.out.println("yuo");


        HBox mun = new HBox(menu);

        VBox glass = new VBox(2, mun, new SearchBar(this), pane, new Progress());
        pane.setMinWidth(300);
        pane.setMaxWidth(900);

        Scene scene = new Scene(glass);



        stage.setMaxWidth(920);
        stage.setMaxHeight(660);
        stage.setMinWidth(50);
        stage.setMinHeight(400);



        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        //stage.sizeToScene();
        stage.setWidth(550);
        stage.setHeight(585);
        //stage.setMaximized(true);
        stage.show();
        //stage.setResizable(false);
         // start

    }

} // GalleryApp
