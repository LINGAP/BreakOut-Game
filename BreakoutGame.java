package edu.macalester.comp124.breakout;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.graphics.GLabel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.*;

/**
 * Main program for the breakout game.
 * Created by Ling Ma Spring 2018.
 *
 */
public class BreakoutGame extends GraphicsProgram implements MouseMotionListener {

    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 1000;
    private static BrickResponder bricks;
    private static Ball ball;
    private Random rdm=new Random();
    private int dX=5; //the step movement of the ball
    private int dY=7;
    private int stdX=dX;
    private Paddle paddle;
    private int LIFE = 10;
    private int normSpeed=10;

    /**
     * constructor and manages the game. The game will run when LIFE>0, whenever the palyer dies, LIFE--,
     * if there are no bricks left and the player has not died, then shows "Congrats!!". When the game is
     * finished, print "END".
     */
    public void init()
   // BreakoutGame()
        {
        //super("Breakout!", CANVAS_WIDTH, CANVAS_HEIGHT);
        setSize(CANVAS_WIDTH,CANVAS_HEIGHT);
        addMouseListeners();
        run();
        while (LIFE>0){
            createBall();
            oneLife(ball);
            if (playerDies()) {
                LIFE--;
                remove(ball);
            }
            else {
                break;
            }
        }
        System.out.println("END");
    }

    /**
     * one round. While player is alive, if there are still bricks, then continue the game;
     * if no bricks left, then you win.
     * @param ball
     */
    private void oneLife(Ball ball){
        while (!playerDies()) {
            if (bricks.brickStillExist()) {
                movePosition(ball);
                pause(15);
                //addRandomBrick();
            }
            else {
                giveCongrats();
                break;
            }
        }
    }

    /***
     * print "congrats"
     */
    private void giveCongrats(){
        GLabel text=new GLabel("Congrats!!!",200,500);
        text.setFont(new Font("Papyrus", Font.BOLD, 70));
        this.add(text);
        pause(500);
    }

    /**
     * create bricks and paddle
     */
    public void run(){
        createBricks();
        createPaddle();
    }


    /**
     * create a ball
     */
    private void createBall(){
        ball=new Ball();
        add(ball);
        pause(700);
}

    /**
     * create a graphics group called bricks
     */
    private void createBricks(){
        bricks=new BrickResponder();
        this.add(bricks);
    }


    private void createPaddle(){
        paddle=new Paddle(100);
        this.add(paddle);
        }

    /**
     * let the paddle follow the mouse's x coordiate
     * @param e
     */
    public void mouseMoved(MouseEvent e){
        if ((ball.getY()+2*Ball.RADIUS)>Paddle.UPPER_Y
                && (ball.getX()+2*Ball.RADIUS)>=paddle.getX()
                && ball.getX()<=(paddle.getX()+Paddle.PADDLE_WIDTH)
                && ball.getY()+2*Ball.RADIUS<=Paddle.UPPER_Y+Paddle.PADDLE_HEIGHT
                &&(e.getX()-paddle.paddleCenterX())*(dX)<0
                ){
                dX=(e.getX()-paddle.paddleCenterX())>0? Math.abs(dX):-Math.abs(dX);
                acceBall(e.getX()-paddle.paddleCenterX());
               // System.out.println(e.getX()-paddle.paddleCenterX());
        }
        //lastPaddlePos = e.getX();
        paddle.setLocation(e.getX()-paddle.getWidth()/2,Paddle.UPPER_Y);
    }

    private void acceBall(double distance){
        if (distance>normSpeed){
            dX*=Math.abs(distance*0.2)+1;
            //dX*=2;
        }
    }


    public void mouseDragged(MouseEvent e){}

    /**
     * if the ball hits something, decide the bound direction of the ball
     * @param ball created in the class
     */
    private void decideBoundDirection(Ball ball){
            if (bricks.hitSomething(ball.getX(), ball.getY())){
                if ( bricks.hitSomething(ball.getX()+2*Ball.RADIUS,ball.getY())){
                    dY*=-1;
                }
                else if (bricks.hitSomething(ball.getX(),ball.getY()+2*Ball.RADIUS)){
                    dX*=-1;
                }
                else { oneCornerHit();}
            }
            else if (bricks.hitSomething(ball.getX() + 2 * Ball.RADIUS,ball.getY() + 2 * Ball.RADIUS)){
                if (bricks.hitSomething(ball.getX()+2*Ball.RADIUS,ball.getY())){
                    dX*=-1;
                }
                else if (bricks.hitSomething(ball.getX(),ball.getY()+2*Ball.RADIUS)){
                    dY*=-1;
                }
                else {oneCornerHit();}
            }

            else if (bricks.hitSomething(ball.getX()+2*Ball.RADIUS,ball.getY())
                    ||bricks.hitSomething(ball.getX(),ball.getY()+2*Ball.RADIUS)){
                 oneCornerHit();
            }
    }




    /**
     * update the ball's position, if hits anything, bound, else, continue moving.
     */
    private void movePosition(Ball ball) {

        decideBoundDirection(ball);
        hitEdge();
        bricks.removeBrick();
        hitPaddle(ball);
        ball.move(dX,dY);
        if (dX>stdX){
            dX-=1;
        }
    }

    /**
     * determine the relect direction if the ball only hits the object at one corner
     * @return a string as bounding direction instruction
     */
    private void oneCornerHit(){
           dX*=-1;
           dY*=-1;
    }

    /**
     * Examine if hits edge or not, if hits, return a string instruction, if not, return null.
     *
     */
    private void hitEdge() {
        if (ball.getX() < 0){
            dX*=-1;
           // ball.setPosition(1,ball.getY());
        }
        else if (ball.getX() + 2 * Ball.RADIUS > CANVAS_WIDTH ){
            dX*=-1;
           // ball.setPosition(CANVAS_WIDTH-2*Ball.RADIUS-1,ball.getY());
        }
        else if (ball.getY() < 0){
            dY*=-1;
            //ball.setPosition(ball.getX(),1);
        }

    }

    /**
     * if the ball is out of the canvas
     * @return false
     */
    private boolean playerDies(){
        return ball.getY()>CANVAS_HEIGHT;
    }


    /**
     * if the ball  hits the Paddle, set the ball outside of the paddle
     * if the has already fallen under the paddle, then do nothing
     * @param ball
     */
    private void hitPaddle(Ball ball){
        if (ball.getY()+2*Ball.RADIUS>Paddle.UPPER_Y
                && (ball.getX()+2*Ball.RADIUS)>=paddle.getX()
                && ball.getX()<=(paddle.getX()+Paddle.PADDLE_WIDTH)
                && ball.getY()+2*Ball.RADIUS<=Paddle.UPPER_Y+Paddle.PADDLE_HEIGHT){
                    dY *= -1;
                    ball.setLocation(ball.getX(), Paddle.UPPER_Y - 2 * Ball.RADIUS);
        }
    }

    /**
     * add random Brick before one round ends
     */
//    private void addRandomBrick(){
//        Random random=new Random();
//        int x=random.nextInt(BreakoutGame.CANVAS_WIDTH-Brick.BRICK_WIDTH);
//        int y=random.nextInt(BreakoutGame.CANVAS_HEIGHT/2);
//        if (getElementAt(x,y)==null){
//            Brick brick=new Brick(x,y);
//            brick.setFillColor(new Color(170,200,178));
//            brick.setFilled(true);
//            bricks.add(brick);
//        }
//    }

//    public static void main(String[] args){
//        BreakoutGame prog = new BreakoutGame(); }

}

