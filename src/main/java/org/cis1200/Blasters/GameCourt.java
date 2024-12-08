package org.cis1200.Blasters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
public class GameCourt extends JPanel {

    // the state of the game logic
    private BottomPaddle bottomPaddle; // the Black Square, keyboard control
    private Circle snitch; // the Golden Snitch, bounces
    private TopPaddle topPaddle; // the top paddle, it auto moves
    private Square square;
    private Poison poison;

    private boolean playing = false; // whether the game is running
    private final JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 600;
    public static final int SQUARE_VELOCITY = 8;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bottomPaddle.setVx(-SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bottomPaddle.setVx(SQUARE_VELOCITY);
                }
            }

            public void keyReleased(KeyEvent e) {
                square.setVx(0);
                square.setVy(0);
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        bottomPaddle = new BottomPaddle(COURT_WIDTH, COURT_HEIGHT);
        topPaddle = new TopPaddle(COURT_WIDTH, COURT_HEIGHT);
        //poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
        snitch = new Circle(COURT_WIDTH, COURT_HEIGHT, Color.WHITE);

        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
            bottomPaddle.move();
            topPaddle.move();
            snitch.move();

            // make the snitch bounce off walls...
            snitch.bounce(snitch.hitWall());
            // ...and the paddles
            snitch.bounce(snitch.hitObj(topPaddle));
            snitch.bounce(snitch.hitObj(bottomPaddle));

            // check for the game end conditions
            /*if (square.intersects(poison)) {
                playing = false;
                status.setText("You lose!");
            } else if (square.intersects(snitch)) {
                playing = false;
                status.setText("You win!");
            }*/

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        topPaddle.draw(g);
        bottomPaddle.draw(g);
        snitch.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}