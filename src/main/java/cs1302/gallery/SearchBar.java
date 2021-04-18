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


public class SearchBar extends HBox {

    Text text = new Text("Search Query:");
    TextField url = new TextField();
    Button pause = new Button("Pause");
    Button update = new Button("Update Images");
    String apple1 = "https://itunes.apple.com/search?term=";
    String apple2 = "&limit=60&media=music";
    GalleryApp app;
    URL search;
    InputStreamReader reader;
    JsonElement jetson;
    JsonObject root;
    JsonArray results;
    Progress progress;
    double bar = 0.05;
    ArrayList<String> query = new ArrayList(60);

    public SearchBar(GalleryApp app, Progress bar) {
        super(5);

        this.app = app;
        progress = bar;
        SearchBar.setMargin(pause, new Insets(5));
        SearchBar.setMargin(text, new Insets(8, 0, 5, 0));
        SearchBar.setMargin(url, new Insets(5, 0, 5, 0));
        SearchBar.setMargin(update, new Insets(5, 5, 5, 0));

        update.setOnAction(e ->  {

            Thread task = new Thread (() -> search());
            task.setDaemon(true);
            task.start();
        });

        pause.setOnAction(e -> {
            System.out.println(app.play);
            app.changeMode();
            if (!app.play) {
                pause.setText("Play");
            } else {
                pause.setText("Pause");
            }
            System.out.println(app.play);

        });
        getChildren().addAll(pause, text, url, update);

        defaultSearch();
    }

    public void search() {

        if (urlMaker(url.getText()).equals("exit")) {
            app.exit.fire();
        } else if (urlMaker(url.getText()).equals("show")) {
            Platform.runLater(() -> app.file.show());
        }

        Platform.runLater(() -> progress.bar.setProgress(0.0));

        String address = apple1 + urlMaker(url.getText())  + apple2;
        int numImages = results(address).size();

        if (numImages == 0) {
            System.out.println("images " + numImages);
            return;
        } else if (numImages > 20) {
            for(int loop = 0; loop < numImages - 20; loop++) {
                //query[loop] = ;
            }
        }
        try {

            if (results.size() < 20) {
                throw new IndexOutOfBoundsException();
            }

            getImages(0, 20, results(address), 20);
        } catch (IndexOutOfBoundsException io) {
            System.out.println("images " + numImages);

            progress.bar.setProgress(0);
            bar = 0;
            getImages(0, numImages, results(address), numImages);
        }
    } // search

    public void defaultSearch()  {

        String address = apple1 + "aries" + apple2;
        String adress = apple1 + "post+malone" + apple2;
        String dress = apple1 + "jack+harlow" + apple2;
        String ress = apple1 + "herro" + apple2;
        String ess = apple1 + "halsey" + apple2;
        bar = 0;

        getImages(0, 6, results(address), 20);

        getImages(6, 8, results(adress) , 20);

        getImages(8, 10, results(dress), 20);

        getImages(10, 18, results(ress), 20);

        getImages(18, 20, results(ess), 20);;


    } // Default()


    public JsonArray results(String address) {
        try {
            search  = new URL(address);

            reader = new InputStreamReader(search.openStream());
            jetson = JsonParser.parseReader(reader);

            root = jetson.getAsJsonObject();
            results = root.getAsJsonArray("results");


        } catch (IOException ex){
            System.out.println("change this when you can");
        }

        return results;
    }
    public void uploadImages(int loop, JsonArray array, double bar) {
        String pi;
        Image pic;
        int percent;

        pi = urlTrim(array.get(loop).getAsJsonObject().get("artworkUrl100").toString());

        pic = new Image(pi);

        Platform.runLater(() -> {
            app.array[loop].setImage(pic);
            progress.bar.setProgress(bar);
        });
    }

    public void getImages(int start, int stop, JsonArray results, int total) {
        double up = 1.0F * (1.0 / total);

        //if (results.size() < 20) {
        //throw new IndexOutOfBoundsException();
        // }

        if (bar > 1) {
            bar = 0;
            System.out.println("yooo");
        }

        System.out.println("fs:" + stop);

        for (start = start; start < stop; start++) {
            bar += up;
            System.out.println(bar + "barfdsa " + up);
            uploadImages(start, results, bar);
        }
    }

    public String  urlTrim(String url) {
        String trim;
        trim = url.substring(1, url.length() - 1);
        return trim;
    }

    public String  urlMaker(String textBox) {
        Scanner input = new Scanner(url.getText());
        int number = 0;
        String text = textBox;

        while(input.hasNext() == true) {
            number++;
            input.next();
        }

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

    }

    public void setQuery(JsonArray results) {
        for (int loop = 0; loop < 60; loop++) {
            query.add(results.get(loop).getAsJsonObject().get("artworkUrl100").toString());
        }
    }

    public void deleteRepeats(ArrayList<String> query) {
        int loop1 = 0;
        int loop2 = 0;

        String string1 = query.get(loop1);
        String string2 = query.get(loop2);

        for (loop1 = 0; loop1 < 60; loop1++) {
            for (loop2 = 0; loop2 < 60; loop2++) {
                if (string1.equals(string2)) {
                    query.remove(loop2);
                } // if
            } // nested for
        } // for
    } // deleteRepeats

} //SearchBar
