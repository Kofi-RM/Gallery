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
    double bar = 0.05;
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
        System.out.println(query1.size() + " size");
    } // SearchBar() constructor

    public void search() {

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

                //if (results.size() > 300) {
                    setQuery(query1, results, 0, 300);
                    //} else {
                    //setQuery(query1, results, 0, results.size());
                    //}
                //for (int loop = 0; loop < 300; loop++) {
                //  System.out.println(query1.get(loop));
                //}

                deleteRepeats(query1);
                for (int loop = 0; loop < query1.size(); loop++) {
                    //System.out.println(query1.size());;
                    //System.out.println(query1.get(loop));
                 }
                if (query1.size() < 20 ) {
                    alert();
                } else
                // setQuery(query2, results, 20, 60);
                // deleteRepeats(query2);
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
        String ress = apple1 + "herro" + apple2;
        String ess = apple1 + "halsey" + apple2;
        bar = 0;

        results(address);
        setQuery(query1, results, 0, 150);
        getImages(0, 6, query1, 6);
        //getImages(6, 8, results(adress) , 20);
        //getImages(8, 10, results(dress), 20);
        // getImages(10, 18, results(ress), 20);
        // getImages(18, 20, results(ess), 20);;*/

    } // defaultSearch()

    public void defaultHelp(int start, int stop) {
        setQuery(query1, results, start, stop);

    }

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

        images.clear();
        if (bar > 1) {
            bar = 0;
            System.out.println("yooo");
        }

        System.out.println("fs:" + stop);

        for (start = start; start < stop; start++) {
            bar += up;
            uploadImages(start, results, bar);
        }

        for (start = originalStart; start < 20; start++) {
            setImages(start);
        }

    } // getImages()

    public void setImages(int loop) {
        Platform.runLater(() -> {
            app.array[loop].setImage(images.get(loop));
        });
    }

//    public void setAll(int start, int stop) {


    //  }
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
        System.out.println("query size " + query.size());

        try {
        for (loop1 = 0; loop1 < query.size(); loop1++) {
            for (loop2 = 0; loop2 < query.size(); loop2++) {

                if (loop1 == 0 && loop2 == 0) {
                    loop2++;
                } else if (loop1 == loop2) { // && loop1) //< query.size() - 1) {
                    loop2++;
                }

                string1 = query.get(loop1);
                string2 = query.get(loop2);


                if (string1.equals(string2)) {
                 query.remove(loop2);
                 System.out.println("remove it " + string2);
             }

                //System.out.println("loop1 " + loop1 + " string1 " + string1);
                //System.out.println("loop2 " + loop2 + " string2 " + string2);
            }
        }
        }catch (IndexOutOfBoundsException io) {
            //System.out.println("catch");
            return;
        }

    }    // deleteRepeats()

} //SearchBar
