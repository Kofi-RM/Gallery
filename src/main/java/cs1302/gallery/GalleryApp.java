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
import javafx.event.*;
import javafx.animation.*;
import java.time.LocalTime;
import javafx.util.Duration;

/**
 * Represents an iTunes GalleryApp.
 */
public class GalleryApp extends Application {

    public ImageView[] array = new ImageView[25];
    TilePane pane = new TilePane();
    MenuBar menu = new MenuBar();
    Menu file = new Menu("File");
    MenuItem exit = new MenuItem("Exit");
    Progress progress = new Progress();
    Boolean play = true;
    SearchBar search = new SearchBar(this ,progress);

    @Override
    /**
     * Start the app.
     *
     *{@inheritdoc}
     */

    public void start(Stage stage) {
        menu.setMinWidth(920);
        file.getItems().add(exit);

        exit.setOnAction(e -> System.exit(0));
        file.setOnAction( e ->  Platform.runLater(() -> file.show()));

        menu.getMenus().add(file);

        //border.setBottom(menu);

        Thread task = new Thread(() -> {
            System.out.println("start");
            //pane = new TilePane();
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


        VBox glass = new VBox(2, menu, search, pane, progress);
        pane.setMinWidth(300);
        pane.setMaxWidth(900);

        Scene scene = new Scene(glass);

        //stage.setAlwaysOnTop(true);
        stage.setMaxWidth(800);
        stage.setMaxHeight(600);
        stage.setMinWidth(300);
        stage.setMinHeight(500);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        //stage.sizeToScene();
        stage.setWidth(550);
        stage.setHeight(585);
        //stage.setMaximized(true);
        stage.show();
    }

    /**
     * Changes play/pause mode. Photos will not automatically change when in pause mode.
     */

    public void changeMode() {
        play = !play;
    }

} // GalleryApp
