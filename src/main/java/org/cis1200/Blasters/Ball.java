package org.cis1200.Blasters;

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a circle of a specified color.
 */
public abstract class Ball extends GameObj {
    public static final int SIZE = 10;
    public static final int INIT_POS_X = 170;
    public static final int INIT_POS_Y = 170;
    public static final int INIT_VEL_X = 2;
    public static final int INIT_VEL_Y = 3;

    final private Color color;

    public Ball (int courtWidth, int courtHeight, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);

        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

    public int nextX() {
        return this.getPx() + this.getVx();
    }

    public void update(){};
}