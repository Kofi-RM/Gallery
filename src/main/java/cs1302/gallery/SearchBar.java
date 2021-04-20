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
        String j = apple1 + "juice+wrld" + apple2;
        String one = "https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/a9/23/82/" +
            "a92382be-06f7-1d8c-496e-a56182eb5980/source/100x100bb.jpg";
        String two = "https://is3-ssl.mzstatic.com/image/thumb/Music114/v4/83/55/f7/" +
            "8355f738-f3a6-235b-0b21-84b42ba74fb7/source/100x100bb.jpg";
        String three = "https://is3-ssl.mzstatic.com/image/thumb/Music114/v4/63/9c/5d/" +
            "639c5d99-9cf3-4fd3-c7d7-c7cfb060bf9b/source/100x100bb.jpg";
        String four = "https://is1-ssl.mzstatic.com/image/thumb/Music118/v4/ff/c6/5c/" +
            "ffc65cfc-04dd-776c-de3e-047b26fe8458/source/100x100bb.jpg";
        String five = " https://is3-ssl.mzstatic.com/image/thumb/Music124/v4/63/ff/76/" +
            "63ff761d-0513-6565-28ec-1c2837dfb780/source/100x100bb.jpg";
            String six = "https://is3-ssl.mzstatic.com/image/thumb/Music123/v4/22/55/b4/" +
                "2255b442-0b47-b9cc-c874-a412a0b7265a/source/100x100bb.jpg";
            String seven = "https://is2-ssl.mzstatic.com/image/thumb/Music113/v4/c9/ab/17/" +
                "c9ab17fe-6ff5-44cb-a8b3-e6bea5ffecba/source/100x100bb.jpg";
            String eight = "https://is4-ssl.mzstatic.com/image/thumb/Music114/v4/88/11/7b/" +
                "88117bb6-ab0d-0134-c5be-9632d49ebc12/source/100x100bb.jpg";
            String nine = "https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/1d/b4/d7/" +
                "1db4d712-91d8-81e6-79f8-c2ae1458423c/source/100x100bb.jpg";
            String ten = " https://is1-ssl.mzstatic.com/image/thumb/Music118/v4/9c/39/91/" +
                "9c3991d8-3949-de12-a275-4163389faae5/source/100x100bb.jpg";
            String eleven = "https://is5-ssl.mzstatic.com/image/thumb/Music117/v4/38/42/73/" +
                "3842738c-3090-6950-4323-a16a8d78d204/source/100x100bb.jpg";
            String twelve = "https://is5-ssl.mzstatic.com/image/thumb/Music114/v4/c1/06/b4/" +
                "c106b47a-82b7-55d2-24ff-4401ab979ed5/source/100x100bb.jpg";
            String thirteen = "https://is5-ssl.mzstatic.com/image/thumb/Music117/v4/02/1f/39/" +
                "021f39c0-806d-4b30-8cb8-12b568f5869f/source/100x100bb.jpg";
            String fourteen = "https://is5-ssl.mzstatic.com/image/thumb/Music113/v4/2e/44/ff/" +
                "2e44ff4b-8130-dd1a-8e63-a5937c500f87/source/100x100bb.jpg";
            String fifteen = "https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/d7/ea/f1/" +
                "d7eaf14d-496c-f613-d5fc-f4f0c3830c71/source/100x100bb.jpg";
            String sixteen = "https://is1-ssl.mzstatic.com/image/thumb/Music113/v4/15/e1/5c/" +
                "15e15ccc-0c2f-bff3-e3e5-30bc53300d8f/source/100x100bb.jpg";
            String seventeen = "https://is2-ssl.mzstatic.com/image/thumb/Music124/v4/b4/19/64/" +
                "b419647b-ede5-1723-fbcf-91aa26715003/source/100x100bb.jpg";
            String eighteen = "https://is5-ssl.mzstatic.com/image/thumb/Music6/v4/82/29/60/" +
                "822960d6-68e2-55ff-b072-201aa8dc8052/source/100x100bb.jpg";
            String nineteen = "https://is1-ssl.mzstatic.com/image/thumb/Music123/v4/5d/f6/06/" +
                "5df60632-1c48-c507-a502-369096ff191b/source/100x100bb.jpg";
            String twenty = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/73/71/47/" +
                "73714787-c46f-7e69-cec7-03db3d491b90/source/100x100bb.jpg";
        setDefaultImages(0, one);
        setDefaultImages( 1,  two);
        setDefaultImages( 2,  three);
        setDefaultImages(3,  four);
        setDefaultImages( 4,  five);
        setDefaultImages( 5,  six);
        setDefaultImages( 6,  seven);
        setDefaultImages( 7,  eight);
        setDefaultImages( 8,  nine);
        setDefaultImages( 9, ten);
        setDefaultImages( 10, eleven);
        setDefaultImages( 11,  twelve);
        setDefaultImages( 12,  thirteen);
        setDefaultImages( 13, fourteen);
        setDefaultImages(14,  fifteen);
        setDefaultImages(15,  sixteen);
        setDefaultImages(16, seventeen);
        setDefaultImages( 17,  eighteen);
        setDefaultImages( 18, nineteen);
        setDefaultImages( 19,  twenty);
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
