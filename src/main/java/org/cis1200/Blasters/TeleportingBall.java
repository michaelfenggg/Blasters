package org.cis1200.Blasters;

import java.awt.*;
import java.util.Random;

public class TeleportingBall extends Ball{

    public static Random rand = new Random();

    public TeleportingBall(int courtWidth, int courtHeight, Color color) {
        super(courtWidth, courtHeight, color);
    }

    @Override
    public void update() {
        if (this.getPy() > 280 && this.getPy() < 320) {
            this.setPx(this.getPx() + rand.nextInt(50) - 25);
            if (this.getPx() < 0) {
                this.setPx(0);
            } else if (this.getPx() > 300) {
                this.setPx(300);
            }
        }
    }
}
