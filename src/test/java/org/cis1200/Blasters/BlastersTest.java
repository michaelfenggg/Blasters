package org.cis1200.Blasters;

import org.cis1200.mushroom.Poison;
import org.cis1200.mushroom.Square;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class BlastersTest {

    GameCourt court;

    @BeforeEach
    public void setUp() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Pong!");
        frame.setLocation(300, 600);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);

        // Add Save button
        final JButton save = new JButton("Save");
        save.addActionListener(e -> court.saveGameState("src/main/java/org/cis1200/Blasters/game_state.txt"));
        control_panel.add(save);

        //System.out.println("Loading game state...");
        court.loadGameState("src/main/java/org/cis1200/Blasters/game_state.txt");
    }

    @Test
    public void createBall() {
        Ball ball = new NormalBall(200, 200, Color.BLACK);

        // square should start at (170, 170)
        assertEquals(170, ball.getPx());
        assertEquals(170, ball.getPy());

        // square should not be moving at the start
        assertEquals(2, ball.getVx());
        assertEquals(3, ball.getVy());
    }

    @Test
    public void ballVelocityUpdatesPosition() {
        Ball ball = new NormalBall(200, 200, Color.BLACK);

        // update velocity to non-zero value
        ball.setVx(10);
        ball.setVy(20);
        assertEquals(10, ball.getVx());
        assertEquals(20, ball.getVy());

        // position should not have updated yet since we didn't call move()
        assertEquals(170, ball.getPx());
        assertEquals(170, ball.getPy());

        // move!
        ball.move();

        // square should've moved
        assertEquals(180, ball.getPx());
        assertEquals(190, ball.getPy());
    }

    @Test
    public void twoObjectIntersection() {
        // square should spawn at (0, 0)
        Ball ball = new NormalBall(200, 200, Color.BLACK);

        TopPaddle topPaddle = new TopPaddle(300, 600);
        assertEquals(0, topPaddle.getPx());
        assertEquals(15, topPaddle.getPy());

        // they're very far apart, so they should not be intersecting
        assertFalse(topPaddle.intersects(ball));

        // move square on top of mushroom
        ball.setPx(0);
        ball.setPy(15);

        assertEquals(0, ball.getPx());
        assertEquals(15, ball.getPy());

        // now, they're on top of one another! they should intersect
        assertTrue(ball.intersects(topPaddle));
    }

    @Test
    public void gameWin() {
        // square should spawn at (0, 0)
        Ball ball = new NormalBall(200, 200, Color.BLACK);

        TopWall topWall = new TopWall(200, 200);

        // move ball on top of wall
        ball.setPx(0);
        ball.setPy(0);

        assertEquals(0, ball.getPx());
        assertEquals(0, ball.getPy());

        // now, they're on top of one another! they should intersect
        assertTrue(ball.intersects(topWall));
    }

    @Test
    public void gameLose() {
        // square should spawn at (0, 0)
        Ball ball = new NormalBall(300, 600, Color.BLACK);

        BottomWall bottomWall = new BottomWall(300, 600);

        // move ball on bottom of wall
        ball.setPx(0);
        ball.setPy(590);

        assertEquals(0, ball.getPx());
        assertEquals(590, ball.getPy());

        // now, they're on top of one another! they should intersect
        assertTrue(ball.intersects(bottomWall));
    }

    @Test
    public void ticks() {
        assertEquals(0, court.getTickCalls());
    }

    @Test
    public void topPaddleMoves() {
        TopPaddle topPaddle = new TopPaddle(300, 600);
        topPaddle.setVx(10);
        topPaddle.move();
        assertEquals(10, topPaddle.getPx());
    }

    @Test
    public void bottomPaddleMoves() {
        BottomPaddle bottomPaddle = new BottomPaddle(300, 600);
        bottomPaddle.setVx(10);
        bottomPaddle.move();
        assertEquals(10, bottomPaddle.getPx());
    }

    @Test
    public void testBadLoadingFile() {
        court = new GameCourt(new JLabel("Running..."));
        court.loadGameState("src/test/java/org.cis1200/Blasters/corrupted_game_state.txt");
        assertTrue(court.isPlaying(), "Game should reset and continue playing when loading a corrupted game state");
    }

    @Test
    public void gameEndsEventually() {
        assertTrue(court.isPlaying());
        for (int i = 0; i < 100000; i++) {
            court.tick();
        }
        // Check if the ball bounces correctly
        assertFalse(court.isPlaying());
    }

}
