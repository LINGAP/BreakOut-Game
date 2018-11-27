package edu.macalester.comp124.breakout;

import acm.graphics.GCompound;
import acm.graphics.GRect;
import acm.graphics.GObject;
import java.awt.*;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * Created by Ling Ma Spring 2018.
 */
public class BrickResponder extends GCompound {
    private final int MARGIN=10;
    private Deque<Brick> list=new ArrayDeque<>();
    private Random random=new Random();
    private int remainBrick=0;

    /**
     * build a wall of bricks and add those bricks to this GraphicGroup
     */
    public BrickResponder(){
        super();

        int posY=30,posX=30;
        Color curC=new Color(random.nextInt(20)+235,random.nextInt(20)+70,random.nextInt(20)+60);
        for(int i=0;i<10;i++){
            curC=makeColor(curC);
            for (int j=0;j<10;j++){
                Brick brick=new Brick(j*(Brick.BRICK_WIDTH+MARGIN)+posX,posY);
                brick.setFillColor(curC);
                brick.setFilled(true);
                this.add(brick);
                remainBrick++;
            }
        posY+=MARGIN+Brick.BRICK_HEIGHT;
        }
    }

    private Color makeColor(Color curC){
        int r=curC.getRed()-10>0 ? curC.getRed()-10:curC.getRed();
        int g=curC.getGreen()+40<255? curC.getGreen()+40:curC.getGreen();
        int b=curC.getBlue()+10<255?curC.getBlue()+10:curC.getBlue();
        return new Color(r,g,b);
    }

    /**
     * examine if x,y coordinate has a brick
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if hits a brick
     */
    public boolean hitSomething(double x, double y){
        GObject object=getElementAt(x,y);
        if (object!=null && object instanceof Brick){
                Brick brick = (Brick) object;
                 addBrick(brick);
            return true;
            }
        else
            return false;
    }


    /**
     * remove any bricks stored in the brick list
     */
    public void removeBrick(){
        while (!list.isEmpty()){
            remove(list.pop());
            remainBrick--;
        }
   }

    /**
     * if the brick hitted has never been added to the list, add it.
     * @param brick hitted
     */
   private void addBrick(Brick brick){
        if (!list.isEmpty()){
            if (list.peek()!=brick){
                list.push(brick);
            }
        }
        else {
           list.push(brick);
        }
   }

    /**
     * check if there are any bricks left
     * @return true if thre are bricks left
     */
   public boolean brickStillExist(){
       return remainBrick!=0;
   }

    }



