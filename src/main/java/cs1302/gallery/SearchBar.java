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

public class SearchBar extends HBox {

    Text text = new Text("Search Query:");
    TextField url = new TextField();
    Button pause = new Button("Pause");
    Button update = new Button("Update Images");
    String apple1 = "https://itunes.apple.com/search?term=";
    String apple2 = "&limit=300&media=music";
    GalleryApp app;
    URL search;
    InputStreamReader reader;
    JsonElement jetson;
    JsonObject root;
    JsonArray results;
    Progress progress;
    double bar = 0;
    boolean theDefault = true;
    ArrayList<String> query1 = new ArrayList<String>(150);
    ArrayList<String> query2 = new ArrayList<String>(150);
    ArrayList<Image> images = new ArrayList<Image>(150);

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

        pause.setOnAction(e -> {
            System.out.println(app.play);
            app.changeMode();
            if (!app.play) {
                pause.setText("Play");
            } else {
                pause.setText("Pause");
            }
            System.out.println(app.play);

        }); // play/pauses on button press

        getChildren().addAll(pause, text, url, update);

        Thread startup = new Thread (() -> defaultSearch());
        startup.setDaemon(true);
        startup.start();

        EventHandler<ActionEvent> handler = event -> play();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    } // SearchBar() constructor

    public void play() {
        //int rand1 =  Math.round.(20);
        int rand1 = (int)Math.round(Math.random()*20);
        int rand2 = (int)Math.round(Math.random() * (query1.size() - 20) + 20);
        //if (theDefault) {
        //  rand2 = (int)Math.round(Math.random() * 20);
        //  setImages(rand1, rand2);
        //}
    }
    public void search() {
        images.clear();
        theDefault = false;
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
            } else {
                setQuery(query1, results, 0, 150);
                deleteRepeats(query1);
                 }
                if (query1.size() < 20 ) {
                    alert();
                } else {
                getImages(0, query1.size() - 1, query1, query1.size() - 1);
            }
    }


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

    public void defaultSearch()  {

        String address = apple1 + "aries" + apple2;
        String adress = apple1 + "post+malone" + apple2;
        String dress = apple1 + "jack+harlow" + apple2;
        String ress = apple1 + "kid+laroi" + apple2;
        String ess = apple1 + "alexander+hamilton" + apple2;
        String ss = apple1 + "ben+platt" + apple2;
        String s = apple1 + "harlow+confetti" + apple2;
        String j = apple1 + "juice+wrld" + apple2;
        bar = 0;

        defaultHelp(3, 0, 3, results(address)); //aries
        defaultHelp(1, 3, 7, results(address));
        defaultHelp(3, 4, 12, results(address));

        defaultHelp(1, 7, 1, results(adress)); // post
        defaultHelp(1, 8, 4, results(adress));
        defaultHelp(1, 9, 0, results(s));
        defaultHelp(1, 10, 6, results(adress));

        defaultHelp(3, 11, 14, results(dress)); // jack harlow
        defaultHelp(1, 14, 19, results(dress));

        defaultHelp(2, 15, 5, results(ress)); // kid laroi

        defaultHelp(1, 17, 1, results(ess)); // hammy


        defaultHelp(1, 18, 0, results(ss)); // ben platt
        defaultHelp(1, 19, 2, results(ss));
        // DefaultHelp loads the images into a query

        imgRngLooper(20, 0, 0); // sets the tilepane to the images
        for (int loop = 0; loop < 20; loop++) {
            System.out.println(query1.get(loop));
        }
        setQuery(query1, results(j), 0, 150);
    } // defaultSearch()

    public void setDefaultImages(int tilepane, String url) {
        Image pic = new Image(url);
        app.array[tilepane].setImage(pic);
    }

    public void defaultHelp(int cycles, int tileStart, int imageStart, JsonArray results) {
        setQuery(query1, results, 0, 150);
        deleteRepeats(query1);
        defaultGetImages(cycles, imageStart);
        }



    public JsonArray results(String address) {
        try {
            search  = new URL(address);

            reader = new InputStreamReader(search.openStream());
            jetson = JsonParser.parseReader(reader);

            root = jetson.getAsJsonObject();
            results = root.getAsJsonArray("results");

        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        return results;
    } // results(String address)

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



    public void defaultGetImages(int cycles, int imageStart) {
        //int originalStart = start;
        //images.clear();
        for (int loop = 0; loop < cycles; loop++) {
            uploadImages(imageStart, query1, 0);
            imageStart++;
         }

    }

    public void setImages(int tilepane, int imageIndex) {
        Platform.runLater(() -> {
            app.array[tilepane].setImage(images.get(imageIndex));
        });
    }
    public void imgRngLooper(int cycles, int loop, int start) {
        for (int loop1 = 0; loop1 < cycles; loop1++) {
            setImagesRange(loop, start);
            loop++;
            start++;
    }
    }
    public void setImagesRange(int loop, int start) {
        Platform.runLater(() -> {
            app.array[loop].setImage(images.get(start));
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

        while(input.hasNext() == true) {
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
