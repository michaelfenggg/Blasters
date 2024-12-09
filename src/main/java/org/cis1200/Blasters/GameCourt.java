package org.cis1200.Blasters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;

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
    private Ball snitch; // the Golden Snitch, bounces
    private TopPaddle topPaddle; // the top paddle, it auto moves
    private TopWall topWall;
    private BottomWall bottomWall;
    private Square square;
    private Poison poison;

    private boolean playing = false; // whether the game is running
    private final JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 600;
    public static final int SQUARE_VELOCITY = 8;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 100;

    //checking if it's the first time the game is being run, if so we load game state
    private int tickCalls = 0;

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
                bottomPaddle.setVx(0);
                bottomPaddle.setVy(0);
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        System.out.println("reset was called");
        bottomPaddle = new BottomPaddle(COURT_WIDTH, COURT_HEIGHT);
        topPaddle = new TopPaddle(COURT_WIDTH, COURT_HEIGHT);
        //poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
        snitch = new TeleportingBall(COURT_WIDTH, COURT_HEIGHT, Color.BLACK);
        topWall = new TopWall(COURT_WIDTH, COURT_HEIGHT);
        bottomWall = new BottomWall(COURT_WIDTH, COURT_HEIGHT);

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
            snitch.update();

            // advance the square and snitch in their current direction.


            snitch.move();
            if (topPaddle.getPx() < snitch.nextX()) {
                topPaddle.setVx(SQUARE_VELOCITY);
            }
            else if (topPaddle.getPx() > snitch.nextX()) {
                topPaddle.setVx(-SQUARE_VELOCITY);
            }
            else {
                topPaddle.setVx(0);
            }
            bottomPaddle.move();
            if (topPaddle.willIntersect(snitch)) {
                topPaddle.setVx(0);
            }
            topPaddle.move();

            // make the snitch bounce off walls...
            snitch.bounceWall(snitch.hitWall());
            // ...and the paddles
            snitch.bouncePaddle(snitch.hitObj(topPaddle));
            snitch.bouncePaddle(snitch.hitObj(bottomPaddle));

            // check for the game end conditions
            if (snitch.intersects(topWall)) {
                playing = false;
                status.setText("You win!");
            } else if (snitch.intersects(bottomWall)) {
                playing = false;
                status.setText("You lose!");
            }

            if (tickCalls == 0) {
                loadGameState("src/main/java/org/cis1200/Blasters/game_state.txt");
            }

            tickCalls++;
            // update the display
            repaint();
        }
    }

    public void saveGameState(String filename) {
        System.out.println("Saving game state to " + filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(snitch.getPx() + "," + snitch.getPy() + "," + snitch.getVx() + "," + snitch.getVy());
            writer.newLine();
            writer.write(topPaddle.getPx() + "," + topPaddle.getPy());
            writer.newLine();
            writer.write(bottomPaddle.getPx() + "," + bottomPaddle.getPy());
            writer.close();
        } catch (IOException e) {
            status.setText("Error saving game state!");
            e.printStackTrace();
        }
    }

    public void loadGameState(String filename) {
        reset();
        System.out.println("Loading game state from " + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            //System.out.println("Reading first line");
            String[] ballState = reader.readLine().split(",");
            //System.out.println("Read ball state: " + Arrays.toString(ballState));
            snitch.setPx(Integer.parseInt(ballState[0]));
            snitch.setPy(Integer.parseInt(ballState[1]));
            snitch.setVx(Integer.parseInt(ballState[2]));
            snitch.setVy(Integer.parseInt(ballState[3]));

            //System.out.println("reading second line");
            String[] topPaddleState = reader.readLine().split(",");
            topPaddle.setPx(Integer.parseInt(topPaddleState[0]));
            topPaddle.setPy(Integer.parseInt(topPaddleState[1]));
            //System.out.println("Loaded top paddle state: " + Arrays.toString(topPaddleState));

            //System.out.println("reading third line");
            String[] bottomPaddleState = reader.readLine().split(",");
            bottomPaddle.setPx(Integer.parseInt(bottomPaddleState[0]));
            bottomPaddle.setPy(Integer.parseInt(bottomPaddleState[1]));
            //System.out.println("Loaded bottom paddle state: " + Arrays.toString(bottomPaddleState));
            repaint();
        } catch (IOException e) {
            status.setText("Error loading game state!");
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        topPaddle.draw(g);
        bottomPaddle.draw(g);
        snitch.draw(g);
        topWall.draw(g);
        bottomWall.draw(g);
        System.out.println("snitch pos: " + snitch.getPx() + ", " + snitch.getPy() + " tickCalls: " + tickCalls);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}