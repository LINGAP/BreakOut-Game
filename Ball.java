package edu.macalester.comp124.breakout;

import acm.graphics.GDimension;
import acm.graphics.GOval;

import java.awt.*;
/**
 * Created by Ling Ma Spring 2018.
 */
public class Ball extends GOval {
    private static int STARTX = BreakoutGame.CANVAS_WIDTH / 2;
    private static int STARTY = BreakoutGame.CANVAS_HEIGHT / 2;
    public static final int RADIUS = 15;

    public Ball()
    {
        super(STARTX, STARTY, 2 * RADIUS, 2 * RADIUS);
        this.setFillColor(new Color(0,0,0));
        this.setFilled(true);
    }

}