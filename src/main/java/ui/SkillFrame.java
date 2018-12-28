package ui;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.creatures.CalabashBrother;

/*
 * @class name:SkillFrame
 * @author:Wu Gang
 * @create: 2018-12-27 21:36
 * @description:
 */
public class SkillFrame extends Pane {
    ImageView imageView = new ImageView();
    Text text = new Text();

    public SkillFrame(Image image, int i) {
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setImage(image);
        text.setFont(new Font(20));
//        text.setEffect(new Lighting());
        text.setFill(Color.WHITE);
        text.setLayoutX(9);
        text.setLayoutY(28);
        getChildren().addAll(imageView, text);
        setLayoutX(i * 40);
        setLayoutY(560);
    }

    public void setSkillTime(int time){
        if(time!=0)
            text.setText(String.valueOf(time));
        else
            text.setText("");
    }

    public void setSkillTime(CalabashBrother calabashBrother){
        if(!calabashBrother.isAlive()){
            text.setText("死");
        }else {
            setSkillTime(calabashBrother.getCooldown());
        }
    }
}
