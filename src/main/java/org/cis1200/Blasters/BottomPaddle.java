package org.cis1200.Blasters;

import java.awt.*;

public class BottomPaddle extends GameObj{

    public static final int HEIGHT = 10;
    public static final int WIDTH = 90;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 570;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

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
        g.fillRect(this.getPx(), this.getPy(), (int) (this.getWidth() * 2 / 3), this.getHeight());
    }
}
