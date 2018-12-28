package logic.position;/*
 * @author:Wu Gang
 * @create: 2018-10-07 19:46
 */

import javafx.scene.image.Image;
import logic.creatures.Creature;

public class Position <T extends Creature>{
    private int x;
    private int y;
    private T creature;

    public Position(int x, int y, T creature) {
        this.x = x;
        this.y = y;
        this.creature = creature;
    }

    public void printPosition(){
        if(this.creature != null){
            creature.sayName();
        }
        else
            System.out.print("----");
    }

    public void setCreature(T creature) {
        this.creature = creature;
    }

    public Image getMyImage() {
        if(this.creature!=null)
            return  this.creature.getMyImage();
        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public T getCreature() {
        return creature;
    }

    public boolean isEmpty(){
        return creature == null;
    }

    public void removeCreature(){
        this.creature = null;
    }
}
