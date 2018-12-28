package controllers;/*
 * @author:Wu Gang
 * @create: 2018-11-21 20:05
 */

import annotation.Description;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.battle.EvilSide;
import logic.battle.JustSide;
import logic.creatures.CalabashBrother;
import ui.SkillFrame;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class GameController implements Initializable {

    @FXML
    public AnchorPane myPane;
    @FXML
    public Canvas myCanvas;
    @FXML
    public Button testButton;

    private static AnchorPane pane;

    private static Pane playPane;

    private Image backgroundImage = new Image("map.jpg");
    private final double WIDTH = backgroundImage.getWidth();
    private final double HEIGHT = backgroundImage.getHeight();
    private GameModel gameModel;

    private static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        pane = myPane;
        this.gameModel = new GameModel(myCanvas);
        myCanvas.setWidth(this.WIDTH);
        myCanvas.setHeight(this.HEIGHT);
        myCanvas.setLayoutX(0);
        myCanvas.setLayoutY(0);
        createSkillFrame();
        Platform.runLater(()->{
            myCanvas.requestFocus();
        });
        playPane = new Pane();
        ImageView imageView = new ImageView(new Image("fengmian.jpg"));
        imageView.setFitWidth(1067);
        imageView.setFitHeight(600);
        playPane.getChildren().add(imageView);
        pane.getChildren().add(playPane);
    }

    private void createSkillFrame(){
        SkillFrame[] skillFrames = new SkillFrame[]{
                new SkillFrame(new Image("skill1.png"), 0),
                new SkillFrame(new Image("skill2.png"), 1),
                new SkillFrame(new Image("skill3.png"), 2),
                new SkillFrame(new Image("skill4.png"), 3),
                new SkillFrame(new Image("skill5.png"), 4),
                new SkillFrame(new Image("skill6.png"), 5),
                new SkillFrame(new Image("skill7.bmp"), 6),
        };
        for(SkillFrame skillFrame: skillFrames){
            myPane.getChildren().add(skillFrame);
        }
        new Thread(()->{
            while (true){
                for (int i = 0;i < 7;i++) {
                    skillFrames[i].setSkillTime(((CalabashBrother) (JustSide.getInstance().getCreatures().get(i))));
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void test(ActionEvent actionEvent) {
        JustSide.getInstance().getCreatures().get(6).releaseSkill();

    }

    public static void playBox(Image image){
        if(playPane!=null)
            return;
        playPane = new Pane();
        playPane.setStyle("-fx-background:transparent;");
        ImageView imageView = new ImageView(image);
        imageView.setStyle("-fx-background:transparent;");

        playPane.getChildren().addAll(imageView);

        GameController.pane.getChildren().add(playPane);
        playPane.setLayoutX(400);
        playPane.setLayoutY(200);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(2),playPane);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1.5);
        scale.setToY(1.5);
        scale.play();
    }

    @Description(todo = "播放框")
    public static void playBox(Image image, String content, long time){
        Text text = new Text(content);
        text.setFont(new Font(20));
        text.setFill(Color.WHITE);
        VBox box = new VBox();
        box.getChildren().add(text);
        box.setStyle("-fx-background:transparent;");

        Pane pane = new Pane();
        pane.setStyle("-fx-background:transparent;");
        ImageView imageView = new ImageView(image);
        imageView.setStyle("-fx-background:transparent;");
//        imageView.setFitWidth(300);
//        imageView.setFitHeight(300);

        pane.getChildren().addAll(imageView, box);
        pane.setLayoutY(150);
        GameController.pane.getChildren().add(pane);
        FadeTransition fade = new FadeTransition(Duration.seconds(time / 2.0), pane);
        fade.setFromValue(0);
        fade.setToValue(3);
        fade.setAutoReverse(true);
        fade.setCycleCount(2);
        fade.play();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()->{
            GameController.pane.getChildren().remove(pane);
            });
        }).start();
    }

    public void setStage(Stage stage) {
        GameController.stage = stage;
    }

    public void keyboardEvent(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
        switch (keyEvent.getText()){
            case "1":JustSide.getInstance().getCreatures().get(0).releaseSkill();break;
            case "2":JustSide.getInstance().getCreatures().get(1).releaseSkill();break;
            case "3":JustSide.getInstance().getCreatures().get(2).releaseSkill();break;
            case "4":JustSide.getInstance().getCreatures().get(3).releaseSkill();break;
            case "5":JustSide.getInstance().getCreatures().get(4).releaseSkill();break;
            case "6":JustSide.getInstance().getCreatures().get(5).releaseSkill();break;
            case "7":JustSide.getInstance().getCreatures().get(6).releaseSkill();break;
            case "f":JustSide.getInstance().allToDie();break;
            case "w":EvilSide.getInstance().allToDie();break;
            case " ":Platform.runLater(()-> {
                        if(playPane!=null) {
                            GameController.pane.getChildren().remove(playPane);
                            playPane = null;
                        }
                    });
                gameModel.restart();
                try {
                    new Thread(GameModel.getInstance()).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Platform.runLater(()-> {
                    if(playPane!=null) {
                        GameController.pane.getChildren().remove(playPane);
                        playPane = null;
                    }
                });break;
            default:break;
        }
    }
}
