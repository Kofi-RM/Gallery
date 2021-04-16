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

public class SearchBar extends HBox {

    Text text = new Text("Search Query:");
    TextField url = new TextField();
    Button pause = new Button("Pause");
    Button update = new Button("Update Images");
    String apple1 = "https://itunes.apple.com/search?term=";
    String apple2 = "&limit=35&media=music";
    GalleryApp appl;
    String[] query = new String[35];
    URL search;
    InputStreamReader reader;
    JsonElement jetson;
    JsonObject root;
    JsonArray results;

    public SearchBar(GalleryApp app) {
        super(5);

        appl = app;

        SearchBar.setMargin(pause, new Insets(5));
        SearchBar.setMargin(text, new Insets(8, 0, 5, 0));
        SearchBar.setMargin(url, new Insets(5, 0, 5, 0));
        SearchBar.setMargin(update, new Insets(5, 5, 5, 0));

        update.setOnAction(e -> search());

        getChildren().addAll(pause, text, url, update);

        defaultSearch();
    }

    public void search() {



        String address = apple1 + urlMaker(url.getText())  + apple2;
        try {

            search = new URL(address);

            reader = new InputStreamReader(search.openStream());
            jetson = JsonParser.parseReader(reader);



            root = jetson.getAsJsonObject();
            results = root.getAsJsonArray("results");
            int numImages = results.size();

            System.out.println("images " + numImages);

            if (numImages == 0) {
                return;
            }


            uploadImages(0, 20, results);

             } catch (IOException ex) {
            System.out.println("no image");
        } // try
        System.out.println(address);
        System.out.println(apple1 + "aries+welcome+home" + apple2);
    } // search

    public void defaultSearch()  {

        String address = apple1 + "aries" + apple2;
        String adress = apple1 + "post+malone" + apple2;
        String dress = apple1 + "jack+harlow" + apple2;
        String ress = apple1 + "herro" + apple2;
        String ess = apple1 + "halsey" + apple2;
//        try {
            uploadImages(0, 6, results(address));
            uploadImages(6, 8, results(adress));
            uploadImages(8, 10, results(dress));
            uploadImages(10, 18, results(ress));
            uploadImages(18, 20, results(ess));

            //      } catch (IOException ex) {

            // } // try
    } // Default()


    public JsonArray results(String address) {
        try {
            search  = new URL(address);

            reader = new InputStreamReader(search.openStream());
            jetson = JsonParser.parseReader(reader);

            root = jetson.getAsJsonObject();
            results = root.getAsJsonArray("results");


        } catch (IOException ex){
            System.out.println("YES HARDER DADDY");
        }

        return results;
    }
    public void uploadImages(int start, int stop, JsonArray array) {
        for (int loop = start; loop < stop; loop++) {
            String pi = urlTrim(array.get(loop).getAsJsonObject().get("artworkUrl100").toString());

            Image pic = new Image(pi);
            appl.array[loop].setImage(pic);
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

}
