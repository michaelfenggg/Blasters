package org.cis1200.Blasters;

import java.awt.*;

public class BottomPaddle extends GameObj{

    public static final int HEIGHT = 10;
    public static final int WIDTH = 60;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 570;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    private static int ticksSince = 10;

    public BottomPaddle(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, WIDTH, HEIGHT, courtWidth, courtHeight);
    }
    /**
     * Default draw method that provides how the object should be drawn in the
     * GUI. This method does not draw anything. Subclass should override this
     * method based on how their object should appear.
     *
     * @param g The <code>Graphics</code> context used for drawing the object.
     *          Remember graphics contexts that we used in OCaml, it gives the
     *          context in which the object should be drawn (a canvas, a frame,
     *          etc.)
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(this.getPx(), this.getPy(), (int) (this.getWidth()), this.getHeight());
    }

    public Direction hitBottomPaddle(GameObj that) {
        //if it does hit the bottom paddle, you should return should up and down otherwise
        //should only check when the ball is at a specific y coordinate (or range since it might never hit that exact y coordinate)
        /*if ((that.getPy() + that.getHeight() - 565) > 0 && (that.getPy() + that.getHeight() - 565) <= 2) {
            if (that.getPx() >= this.getPx() - 30 && that.getPx() <= this.getPx() + 30) {
                return Direction.DOWN;
            }
        }*/
        if (this.intersects(that)) {
            // Ensure the ball is hitting the top of the paddle
            //if (that.getPy() + that.getHeight() <= this.getPy() + 2) {
                return Direction.DOWN;
            //}
        }
        return null;
    }
}
