package org.cis1200.Blasters;

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunBlasters implements Runnable {
    public void run() {
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
        final GameCourt court = new GameCourt(status);
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

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Show instructions popup
        String instructions = "<html>Welcome to Pong!<br><br>"
                + "Instructions:<br>"
                + "- Use the left and right arrow keys to move the bottom paddle.<br>"
                + "- The goal is to prevent the ball from hitting the bottom wall.<br>"
                + "- The game ends when the ball hits the top or bottom wall.<br><br>"
                + "Good luck!</html>";
        JOptionPane.showMessageDialog(frame, instructions, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);

        //System.out.println("Loading game state...");
        court.loadGameState("src/main/java/org/cis1200/Blasters/game_state.txt");
        court.reset();

    }
}