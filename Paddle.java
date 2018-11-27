package edu.macalester.comp124.breakout;

import java.awt.*;
import acm.graphics.GRect;
/**
 * Created by Ling Ma Spring 2018.
 */
public class Paddle extends GRect {
    public static final int PADDLE_HEIGHT=20;
    public static final int PADDLE_WIDTH=200;
    public static final int UPPER_Y=BreakoutGame.CANVAS_HEIGHT-200;

    public Paddle(int padPosX){
        super(padPosX,UPPER_Y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.setFillColor(new Color(0,0,0));
        this.setFilled(true);
    }

    public double paddleCenterX(){
        return this.getX()+PADDLE_WIDTH/2;
    }


}
