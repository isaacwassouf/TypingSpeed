package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileReader;
import java.util.Random;


public class Controller {


    public TextArea fxtArea;
    public Label fxlabel;
    public TextArea fxtArea2;
    private int counter=60;
    private boolean counterisRunning= true;

    public void startRace(ActionEvent actionEvent) throws Exception {

        String original = setUp();
        
        fxtArea.setText(original);
        Thread keyboard = new Thread(()->{
            while(counterisRunning) {
                String input = fxtArea2.getText().toString();
                int size = input.length();
                String subOriginal = original.substring(0, size);
                if (input.equals(subOriginal)) {
                    Platform.runLater(() -> {
                        fxlabel.setTextFill(Color.GREEN);
                    });
                } else {
                    Platform.runLater(() -> {
                        fxlabel.setTextFill(Color.RED);
                    });
                }
                try {
                    Thread.sleep(500);
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

           Thread th= new Thread(()->{
              int i=0;
              while(i < 3){
                  if (i==0){
                      Platform.runLater(new Runnable() {
                          @Override
                          public void run() {
                              fxlabel.setText("Ready!");
                          }
                      });
                      i++;
                  }
                  else if (i==1){
                      Platform.runLater(new Runnable() {
                          @Override
                          public void run() {
                              fxlabel.setText("Set!");
                          }
                      });
                      i++;
                  }
                  else if (i==2){
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
                  }
                  catch(Exception e){
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
        }).start();

        keyboard.start();
    }


    public void stopRace(ActionEvent actionEvent) {
        counterisRunning=false;
        counter=0;


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
            fileReader= new FileReader(new File("/Users/isaacwassouf/Desktop/Shaw"));
            result =getSource(fileReader);
            fileReader.close();
        }
        else if (ran==1){
            fileReader= new FileReader(new File("/Users/isaacwassouf/Desktop/RedDialog"));
            result =getSource(fileReader);
            fileReader.close();
        }
        else if (ran==2){
            fileReader= new FileReader(new File("/Users/isaacwassouf/Desktop/RedDialog2"));
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


