package edu.macalester.comp124.breakout;

import acm.graphics.GCompound;
import acm.graphics.GRect;
/**
 * Created by Ling Ma Spring 2018.
 */
public class Brick extends GRect {
    public static final int BRICK_HEIGHT=15;
    public static final int BRICK_WIDTH=60;

    public Brick(int posX, int posY){
        super(posX,posY,BRICK_WIDTH,BRICK_HEIGHT);
    }
}
