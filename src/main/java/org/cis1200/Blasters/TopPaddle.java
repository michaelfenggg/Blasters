package org.cis1200.Blasters;

import java.awt.*;

public class TopPaddle extends GameObj{
    public static final int SIZE = 20;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    public TopPaddle(int vx, int vy, int px, int py, int width, int height, int courtwidth, int courtheight) {
        super(vx, vy, px, py, width, height, courtwidth, courtheight);
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
        int x = this.getPx() + this.getVx();
        int y = 270;
        g.setColor(Color.BLACK); // Set the color for the paddle
        g.fillRect(x, y, this.getWidth(), this.getHeight());
    }
}
