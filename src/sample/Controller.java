package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


public class Controller {


    public TextArea fxtArea;
    public Label fxlabel;
    public TextArea fxtArea2;
    public Button bid;
    private  int counter=60;
    private  boolean counterisRunning= true; // checks if the threads that's counting is running
    private  boolean isClicked =true; // if the start button is clicked while the threads are running do nothing

    public void startRace(ActionEvent actionEvent) throws Exception {

        if (isClicked) {
            isClicked= false;
            String original = setUp();
            fxtArea.setText(original);

            Thread keyboard = new Thread(()  -> {
                while (counterisRunning) {
                    String input = fxtArea2.getText().toString();
                    String subOriginal = original.substring(0, input.length());

                    if (input.equals(original)&& counter>=45) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Pane pane = FXMLLoader.load(getClass().getResource("CopyPasteError.fxml"));
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(pane,508,148));
                                    stage.show();
                                    stopRace(null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if(input.equals(original)&& counter<=45){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Pane pane = new Pane();
                                Label label = new Label("Done in "+counter+"s");
                                label.setLayoutX(190);
                                label.setLayoutY(50);
                                label.setStyle("-fx-font: 24 arial;");
                                pane.getChildren().add(label);
                                Stage stage =  new Stage();
                                stage.setScene(new Scene(pane,508,148));
                                stage.setResizable(false);
                                stage.show();
                                stopRace(null);
                            }
                        });


                    } else {
                        if (input.equals(subOriginal)) {
                            Platform.runLater(() -> {
                                fxlabel.setTextFill(Color.GREEN);
                            });
                        } else {
                            Platform.runLater(() -> {
                                fxlabel.setTextFill(Color.RED);
                            });
                        }
                    }


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fxtArea2.setEditable(false);
                    }
                });

            });


            new Thread(() -> {

                Thread th = new Thread(() -> {
                    int i = 0;
                    while (i < 3 && counterisRunning ) { // counterisRunning is used to stop the thread if the Stop button was clicked
                        if (i == 0) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    fxlabel.setText("Ready!");
                                }
                            });
                            i++;
                        } else if (i == 1) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    fxlabel.setText("Set!");
                                }
                            });
                            i++;
                        } else if (i == 2) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    fxlabel.setText("Go!");
                                    fxtArea2.setEditable(true);
                                }
                            });
                            i++;
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                th.start();
                try {
                    th.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                while (counter > 0) {
                    synchronized (new Object()) {
                        counter--;
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            fxlabel.setText(String.valueOf(counter));
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


                synchronized (new Object()) {
                    counterisRunning = false;
                }
                isClicked=true;
            }).start();
            keyboard.start();

        }

        else{
            return;
        }

    }


    public  void stopRace(ActionEvent actionEvent) {
        counterisRunning=false;
        counter=0;
        isClicked= true;
    }


    public String setUp() throws Exception{
        counterisRunning=true;
        counter=60;
        fxtArea2.setText("");
        fxtArea2.requestFocus();
        String result="";
        FileReader fileReader;
        Random random =  new Random();
        int ran= random.nextInt(3);

        if (ran==0){
            fileReader= new FileReader(new File("/Users/isaacwassouf/Desktop/TypingSpeed/sourceText/Shaw"));
            result =getSource(fileReader);
            fileReader.close();
        }
        else if (ran==1){
            fileReader= new FileReader(new File("/Users/isaacwassouf/Desktop/TypingSpeed/sourceText/RedDialog"));
            result =getSource(fileReader);
            fileReader.close();
        }
        else if (ran==2){
            fileReader= new FileReader(new File("/Users/isaacwassouf/Desktop/TypingSpeed/sourceText/RedDialog2"));
            result =getSource(fileReader);
            fileReader.close();
        }

        return result;
    }

    
    private String getSource(FileReader fileReader) throws Exception{

        String result="";
        int letter= fileReader.read();
        while(letter!=-1){
            result=result+(char) letter;
            letter= fileReader.read();
        }

        return result;
    }



}


