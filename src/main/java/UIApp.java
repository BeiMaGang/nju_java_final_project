import controllers.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class UIApp extends Application {

    private Group root = new Group();

    GameController gameController;

    private Parent createContent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("board.fxml"));
        this.root.getChildren().add(
                loader.load(getClass().getResourceAsStream("board.fxml")));
        gameController = loader.getController();
        return this.root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.setX(0);
//        primaryStage.setY(0);
        primaryStage.setTitle("1~7每个葫芦娃的技能 空格开始或者重开 w键 f键 分别瞬间结束整局游戏");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("DaWa.png"));
        Scene scene = new Scene(createContent());
        gameController.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void replaceScene() throws IOException {
        this.root.getChildren().removeAll();
        this.root.getChildren().add(FXMLLoader.load(getClass().getResource("board.fxml")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

