package ui;

import annotation.Author;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import logic.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

/*
 * @class name:AbstractBattleCanvas
 * @author:Wu Gang
 * @create: 2018-12-10 21:39
 * @description:
 */

@Author(name = "吴刚")
public class AbstractBattleCanvas extends Canvas {
    private Image backgroundImage;
    List<Creature> creatures = new ArrayList<>();

    public AbstractBattleCanvas() {
        this.backgroundImage = new Image("/resources/map.jpg");
        getGraphicsContext2D().drawImage(this.backgroundImage,0,0,
                1000,1000);
    }
}
