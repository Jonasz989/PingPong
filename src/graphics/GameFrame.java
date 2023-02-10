/**
 * Main frame with all settings
 * MenuPanel on the left and GamePanel on the right
 */

package graphics;

import map.Field;

import javax.swing.*;

public class GameFrame extends JFrame {

    private final Menu menu;
    private final GamePanel gamePanel;
    int numberOfColumns, numberOfRows, numberOfPlayers, numberOfEnemies, numberOfBalls, speedOfBalls;

    public GameFrame() {
        //settings for main frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("lab05_pop");
        //creating panels
        menu = new Menu();
        gamePanel = new GamePanel();
        //adding panels
        this.add(gamePanel);
        this.add(menu);
        //setting visible
        this.setVisible(true);
    }


    /**
     * Preparing game and checking if start conditions are fulfilled
     */
    public void prepareGame() {

        //listener for start button
        menu.startButton.addActionListener(e -> {
            try {
                //getting values from spinners
                numberOfColumns = (Integer) menu.columnsSpinner.getValue();
                numberOfRows = (Integer) menu.rowsSpinner.getValue();
                numberOfPlayers = (Integer) menu.playersSpinner.getValue();
                numberOfEnemies = (Integer) menu.enemiesSpinner.getValue();
                numberOfBalls = (Integer) menu.ballsSpinner.getValue();
                speedOfBalls = (Integer) menu.speedSpinner.getValue();

                if (canGameStart()) {
                    JOptionPane.showMessageDialog(null, "Error - too less rows", "Wrong parameters!", JOptionPane.WARNING_MESSAGE);
                    throw new NumberFormatException();
                } else {
                    gamePanel.isRunning = true;
                    gamePanel.field = new Field(numberOfColumns, numberOfRows, numberOfPlayers, numberOfEnemies, numberOfBalls, gamePanel, speedOfBalls);
                    menu.startButton.setEnabled(false);
                    menu.stopButton.setEnabled(true);
                    menu.resetButton.setEnabled(true);
                    new Thread(gamePanel::startGame).start();
                }

            } catch (NumberFormatException error) {
                System.out.println(error.getMessage());
            }
        });

        //listener for stop button
        menu.stopButton.addActionListener(e -> {
            gamePanel.isRunning = false;
            menu.startButton.setEnabled(true);
            menu.stopButton.setEnabled(false);

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            int[] points = gamePanel.field.points();
            JOptionPane.showMessageDialog(null, "Score: " + points[0] + ":" + points[1], "End of the match", JOptionPane.INFORMATION_MESSAGE);

        });

        //listener for reset button
        menu.resetButton.addActionListener(e -> {
            gamePanel.isRunning = false;
            menu.startButton.setEnabled(true);
            menu.stopButton.setEnabled(false);
            menu.startButton.doClick();
        });

    }

    /**
     * Checking if given parameters are proper
     */
    private boolean canGameStart() {
        return numberOfBalls > numberOfRows || numberOfPlayers > numberOfRows || numberOfEnemies > numberOfRows;
    }

}
