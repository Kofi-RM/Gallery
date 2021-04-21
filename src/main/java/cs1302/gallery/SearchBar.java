package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import java.util.Scanner;
import java.io.IOException;
import java.net.URL;
import java.io.InputStreamReader;
import com.google.gson.*;
import javafx.scene.image.*;
import java.net.MalformedURLException;
import java.io.IOException;
import javafx.application.*;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.*;
import java.time.LocalTime;
import javafx.util.Duration;
import javafx.event.*;

/**
 * SearchBar class contains all methods and elements pertaining to a search
 */

public class SearchBar extends HBox {

    Text text = new Text("Search Query:");
    TextField url = new TextField();
    Button pause = new Button("Pause");
    Button update = new Button("Update Images");
    String apple1 = "https://itunes.apple.com/search?term=";
    String apple2 = "&limit=150&media=music";
    GalleryApp app;
    URL search;
    InputStreamReader reader;
    JsonElement jetson;
    JsonObject root;
    JsonArray results;
    Progress progress;
    double bar = 0;
    boolean shouldPlay = true;
    ArrayList<String> query1 = new ArrayList<String>(150);
    ArrayList<String> flashImage = new ArrayList<String>(150);
    ArrayList<String> initial = new ArrayList<String>(20);
    ArrayList<Image> images = new ArrayList<Image>(150);
    EventHandler<ActionEvent> handler = event -> play();
    KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
    Timeline timeline = new Timeline();

    /**
     * SearchBar constuctor
     *
     * @param app App instance calling this method
     * @param bar Progress bar of the calling app
     */
    public SearchBar(GalleryApp app, Progress bar) {
        super(5);

        this.app = app; // loads instance of GalleryApp calling SearchBar into it
        progress = bar; // loads Progress of GalleryApp in
        SearchBar.setMargin(pause, new Insets(5));
        SearchBar.setMargin(text, new Insets(8, 0, 5, 0));
        SearchBar.setMargin(url, new Insets(5, 0, 5, 0));
        SearchBar.setMargin(update, new Insets(5, 5, 5, 0));

        update.setOnAction(e ->  {

            Thread task = new Thread (() -> search());
            task.setDaemon(true);
            task.start();

        }); // searches on a new thread on button press
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        pause.setOnAction(e -> {
            System.out.println(app.play);
            app.changeMode();
            if (!app.play) {
                pause.setText("Play");
                timeline.pause();
                System.out.println("here");
            } else {
                pause.setText("Pause");
                timeline.play();
            }
            System.out.println(app.play);

        }); // play/pauses on button press

        getChildren().addAll(pause, text, url, update);

        Thread startup = new Thread (() -> defaultSearch());
        startup.setDaemon(true);
        startup.start();

        timeline.play();
    } // SearchBar() constructor

    /**
     * Method handles rotating images when play mode is on.
     */

    public void play() {
        int rand1 = (int)Math.round(Math.random() * 19);
        int rand2 = (int)Math.round(Math.random() * (query1.size() - 21) + 20);
        if (query1.size() < 22) {
            rand2 = (int)Math.round(Math.random() * (query1.size() - 1) );
        }
        String rerun = apple1 + "charlie+puth" + apple2;

        setImages(rand1, rand2);
        query1.remove(rand2);
        if (query1.size() == 0) {
            setQuery(query1, results(rerun), 0, 150);
            deleteRepeats(query1);
            System.out.println("query size " + query1.size());
        }
        for (int loop = 0; loop < query1.size();  loop++) {
            uploadImages(loop, query1, 0);
        }

        //System.out.println(LocalTime.now());
        //System.out.println(app.play);
    }

    /**
     * Method searchs the text currently in the search bar.
     * If less than 20 distinct results will bring up an error alert.
     */

    public void search() {
        images.clear();
        //theDefault = false;
        if (urlMaker(url.getText()).equals("exit")) {
            app.exit.fire();
        } else if (urlMaker(url.getText()).equals("show")) {
            Platform.runLater(() -> app.file.show());
        } // can type commands

        Platform.runLater(() -> progress.bar.setProgress(0.0)); // resets progress bar on new search

        String address = apple1 + urlMaker(url.getText())  + apple2; // makes address
        int numImages = results(address).size(); // number of search results

        if (results.size() < 20) {
            alert();
            return;
        } else {
            setQuery(query1, results, 0, 150);
            deleteRepeats(query1);
        }

        if (query1.size() < 20 ) {
            alert();
            return;
        } else {
            getImages(0, query1.size() - 1, query1, query1.size() - 1);
        }
    }


    /**
     * Causes an alert pop-up.
     */

    public void alert() {
        Platform.runLater (() -> {
            Alert halt = new Alert(AlertType.ERROR);
            halt.setHeaderText("Invalid search");
            halt.setContentText("Less than 20 artworks found");
            halt.setResizable(true);
            halt.setHeight(200);
            halt.setWidth(200);
            halt.showAndWait();
        });

    }

    /**
     * Gathers and sets images that show on startup.
     */

    public void defaultSearch()  {
        String j = apple1 + "dua+lipa" + apple2;

        initial.add("https://is2-ssl.mzstatic.com/image/thumb/Music124/v4/90/eb/af/" +
            "90ebaf50-e564-58c6-5df0-2304e32268ce/source/100x100bb.jpg");
        initial.add("https://is3-ssl.mzstatic.com/image/thumb/Music124/v4/84/de/36/" +
            "84de36fd-192a-3f20-aa64-3ad475ebcd12/source/100x100bb.jpg");
        initial.add("https://is2-ssl.mzstatic.com/image/thumb/Music124/v4/36/d7/b7/" +
            "36d7b769-e37c-cdea-584b-b1a80be38e08/source/100x100bb.jpg");
        initial.add("https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/8e/45/f2/" +
            "8e45f291-f922-3a66-f8f4-8004af6f5514/source/100x100bb.jpg");
        initial.add("https://is2-ssl.mzstatic.com/image/thumb/Music124/v4/2c/60/97/" +
            "2c609768-3cb4-8980-751c-38ad54c864ce/source/100x100bb.jpg");
        initial.add("https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/4b/36/41/" +
            "4b364106-85f4-2ad4-85fa-a52676428af0/source/100x100bb.jpg");
        initial.add("https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/ce/06/50/" +
            "ce0650b2-cbf4-209d-5958-a77ae9959338/source/100x100bb.jpg");
        initial.add("https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/36/9b/a2/" +
            "369ba2e1-2dfc-6e2a-7065-4612af58fa0a/source/100x100bb.jpg");
        initial.add("https://is2-ssl.mzstatic.com/image/thumb/Music114/v4/50/8d/91/"
            + "508d9199-6e21-ee05-66ab-64db6c5ac0da/source/100x100bb.jpg");
        initial.add("https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/a9/23/82/" +
            "a92382be-06f7-1d8c-496e-a56182eb5980/source/100x100bb.jpg");
        initial.add("https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/5e/4f/1e/" +
            "5e4f1e69-20f7-e210-de36-0b6d89b36ca9/source/100x100bb.jpg");
        initial.add("https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/89/70/79/" +
            "89707908-0f16-817d-1d59-27dc803952ee/source/100x100bb.jpg");
        initial.add("https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/ca/84/5f/" +
            "ca845f3c-1776-a6c0-c5d2-3246c8f89777/source/100x100bb.jpg");
        initial.add("https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/70/7e/13/" +
            "707e13d5-6452-e874-8069-26e7a9864566/source/100x100bb.jpg");
        initial.add("https://is3-ssl.mzstatic.com/image/thumb/Music114/v4/de/e5/02/" +
            "dee50251-b193-df0e-255f-079787e50b4e/source/100x100bb.jpg");
        initial.add("https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/73/f0/0d/" +
            "73f00d56-8021-dd5e-f209-eb385ce1560b/source/100x100bb.jpg");
        initial.add("https://is3-ssl.mzstatic.com/image/thumb/Music124/v4/da/eb/51/" +
            "daeb5176-01f1-2850-3913-28bcb9864736/source/100x100bb.jpg");
        initial.add("https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/77/75/25/" +
            "777525b4-7579-0e68-6022-890360c967cc/source/100x100bb.jpg");
        initial.add("https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/a9/23/82/" +
            "a92382be-06f7-1d8c-496e-a56182eb5980/source/100x100bb.jpg");
        initial.add("https://is3-ssl.mzstatic.com/image/thumb/Music114/v4/63/9c/5d/" +
            "639c5d99-9cf3-4fd3-c7d7-c7cfb060bf9b/source/100x100bb.jpg");

        for (int loop = 0; loop < 20;  loop++) {
            setDefaultImages(loop, initial.get(loop));
        }
        setQuery(query1, results(j), 0, 150);
        deleteRepeats(query1);
        for (int loop = 0; loop < query1.size();  loop++) {
            System.out.println(query1.get(loop));
        }
        for (int loop = 0; loop < query1.size();  loop++) {
            uploadImages(loop, query1, 0);
        }

        System.out.println(query1.size());
        //timeline.play();
    } // defaultSearch()

    /**
     * Assigns startup images to the TilePane.
     *
     * @param tilepane index of tilepane
     * @param url image url
     */

    public void setDefaultImages(int tilepane, String url) {
        Image pic = new Image(url);
        app.array[tilepane].setImage(pic);
    }

    /**
     * Gathers JsonArray from Itunes search url.
     *
     * @param address URL for search term
     * @return results Results of search as a JsonArray
     */

    public JsonArray results(String address) {
        try {
            search  = new URL(address);

            reader = new InputStreamReader(search.openStream());
            jetson = JsonParser.parseReader(reader);

            root = jetson.getAsJsonObject();
            results = root.getAsJsonArray("results");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return results;
    } // results(String address)

    /**
     * Makes images and updates progress bar accordingly.
     *
     * @param loop index of String array
     * @param array string array being called
     * @param bar progress bars current value
     */

    public void uploadImages(int loop, ArrayList<String> array, double bar) {
        String pi;
        Image pic;
        int percent;

        pi = urlTrim(array.get(loop));

        pic = new Image(pi);
        images.add(pic);
        Platform.runLater(() -> {
            //app.array[loop].setImage(pic);
            progress.bar.setProgress(bar);
        }); // sets image and progress bar
    } // uploadImages()

    /**
     * Inclusive call to upload and set images.
     *
     * @param start index of both tilepane and string array
     * @param stop loops will stop executing when hitting this index
     * @param results string array of search results
     * @param total total number of images being uploaded; determines progress bar increments
     */

    public void getImages(int start, int stop, ArrayList<String> results, int total) {
        double up = 1.0F * (1.0 / total);
        int originalStart = start;
        int counter = 0;
        int diff = 0;

        images.clear();

        if (bar > 1) {
            bar = 0;
            System.out.println("yooo");
        }

        for (start = start; start < stop; start++) {
            bar += up;
            uploadImages(start, results, bar);
        }

        if (stop > 20) {
            stop = 20;
        }
        for (start = originalStart; start < stop; start++) {
            if (counter == 0) {
                diff = start;
            }
            int spot = start - diff;
            setImages(start, spot);
            counter++;
        }

    } // getImages()

    /**
     * Sets loaded images to TilePane.
     *
     * @param tilepane index of tilepane
     * @param imageIndex index of image array
     */

    public void setImages(int tilepane, int imageIndex) {
        Platform.runLater(() -> {
            app.array[tilepane].setImage(images.get(imageIndex));
        });
    }


    public String  urlTrim(String url) {
        String trim;
        trim = url.substring(1, url.length() - 1);
        return trim;
    } // urlTrim(String url)

    public String  urlMaker(String textBox) {
        Scanner input = new Scanner(url.getText());
        int number = 0;
        String text = textBox;

        while (input.hasNext() == true) {
            number++;
            input.next();
        } // while

        if (number == 1) {
            return text;
        } else if (number > 1) {

            Scanner input2 = new Scanner(url.getText());
            text = input2.next();

            for (int loop = 0; loop < number - 1; loop++) {
                text = text + "+" + input2.next();
            }
            return text;
        } else {
            return "";
        }

    } // urlMaker(String textBox)

    public void setQuery(ArrayList<String> query, JsonArray results, int start, int stop) {
        query.clear(); // clear query on each new search
        if (results.size() > 300) {
            for (int loop = start; loop < stop; loop++) {
                query.add(results.get(loop).getAsJsonObject().get("artworkUrl100").toString());
            }
        } else {
            for (int loop = start; loop < results.size(); loop++) {
                query.add(results.get(loop).getAsJsonObject().get("artworkUrl100").toString());
            }

        }
    } // setQuery()

    public void deleteRepeats(ArrayList<String> query) {
        int loop1 = 0;
        int loop2 = 0;

        String string1 = query.get(loop1);
        String string2 = query.get(loop2);

        try {
            for (loop1 = 0; loop1 < query.size(); loop1++) {
                for (loop2 = 0; loop2 < query.size(); loop2++) {

                    if (loop1 == 0 && loop2 == 0) {
                        loop2++;
                    } else if (loop1 == loop2) {
                        loop2++;
                    }

                    string1 = query.get(loop1);
                    string2 = query.get(loop2);


                    if (string1.equals(string2)) {
                        query.remove(loop2);

                        loop2--;
                    }
                }
            }
        } catch (IndexOutOfBoundsException io) {
            //System.out.println("catch");
            return;
        }

    }    // deleteRepeats()

} //SearchBar
