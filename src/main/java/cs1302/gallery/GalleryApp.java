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


        HBox h = new HBox();
        h.setAlignment(Pos.CENTER);

        Thread task = new Thread(() -> {
            System.out.println("start");
            hbox(h);
            System.out.println("h");
            hbox(i);
            hbox(j);
            hbox(k);
            hbox(l);
            System.out.println("end");
        });
        task.start();

        System.out.println("yo");

/*        Thread task2 = new Thread(() -> {
              for (int loop = 0; loop < 5; loop++) {
                  Thumbnail icon = new Thumbnail();
                  i.getChildren().add(icon);
                  array[loop + 5] = icon;
                  // icons.getChildren().add(i);
              }
        });
        task2.start();

        Thread task3 = new Thread(() -> {
            for (int loop = 0; loop < 5; loop++) {
                Thumbnail icon = new Thumbnail();
                j.getChildren().add(icon);
                array[loop + 10] = icon;
                //icons.getChildren().add(j);
            }
        });
        task3.start();

        Thread task4 = new Thread(() -> {
            for (int loop = 0; loop < 5; loop++) {
                Thumbnail icon = new Thumbnail();
                k.getChildren().add(icon);
                array[loop + 15] = icon;
                //icons.getChildren().add(k);
            }
        });
        task4.start();

        Thread task5 = new Thread(() -> {
        for (int loop = 0; loop < 5; loop++) {
            Thumbnail icon = new Thumbnail();
            l.getChildren().add(icon);
            array[loop + 20] = icon;
            //icons.getChildren().add(l);
             }
        });
        task5.start();*/

            icons.getChildren().addAll(h,i,j,k,l);
            System.out.println("jk");

        HBox mun = new HBox(menu);
        VBox pane = new VBox(2, mun, new SearchBar(this), icons, new Progress());
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
