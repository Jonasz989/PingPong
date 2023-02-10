/**
 * Class for Menu Panel
 */

package graphics;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    public JButton startButton, resetButton, stopButton;
    int xPositionForSpinners = 30;
    public MenuSpinner columnsSpinner, rowsSpinner, playersSpinner, enemiesSpinner, ballsSpinner, speedSpinner;

    public Menu() {

        ///////////////////////////////////////////////////////////////////////////////////////
        MyLabel title = new MyLabel("PING PONG", 15, 90);
        this.setSize(250, 900);
        this.setLocation(0, 0);
        this.setBackground(Color.getHSBColor(0.083f, 0.8f, 0.99f));
        this.setLayout(null);
        ///////////////////////////////////////////////////////////////////////////////////////
        //MODELS
        ///////////////////////////////////////////////////////////////////////////////////////
        SpinnerNumberModel modelColumns = new SpinnerNumberModel(9, 9, 17, 2);
        SpinnerNumberModel modelRows = new SpinnerNumberModel(6, 6, 16, 1);
        SpinnerNumberModel modelPlayers = new SpinnerNumberModel(2, 2, 16, 1);
        SpinnerNumberModel modelEnemies = new SpinnerNumberModel(2, 2, 16, 1);
        SpinnerNumberModel modelBalls = new SpinnerNumberModel(3, 3, 16, 1);
        SpinnerNumberModel modelSpeed = new SpinnerNumberModel(1, 1, 5, 1);
        ///////////////////////////////////////////////////////////////////////////////////////
        //LABELS
        ///////////////////////////////////////////////////////////////////////////////////////
        MyLabel numberOfColumns = new MyLabel("Columns:", 50, xPositionForSpinners);
        MyLabel numberOfRows = new MyLabel("Rows:", 150, xPositionForSpinners);
        MyLabel numberOfPlayers = new MyLabel("Allies:", 250, xPositionForSpinners);
        MyLabel numberOfEnemies = new MyLabel("Opponents:", 350, xPositionForSpinners);
        MyLabel numberOfBalls = new MyLabel("Balls:", 450, xPositionForSpinners);
        MyLabel speedOfBall = new MyLabel("Speed:", 550, xPositionForSpinners);
        ///////////////////////////////////////////////////////////////////////////////////////
        //SPINNERS
        ///////////////////////////////////////////////////////////////////////////////////////
        columnsSpinner = new MenuSpinner(modelColumns, 80, xPositionForSpinners);
        rowsSpinner = new MenuSpinner(modelRows, 180, xPositionForSpinners);
        playersSpinner = new MenuSpinner(modelPlayers, 280, xPositionForSpinners);
        enemiesSpinner = new MenuSpinner(modelEnemies, 380, xPositionForSpinners);
        ballsSpinner = new MenuSpinner(modelBalls, 480, xPositionForSpinners);
        speedSpinner = new MenuSpinner(modelSpeed, 580, xPositionForSpinners);
        ///////////////////////////////////////////////////////////////////////////////////////
        //BUTTONS
        ///////////////////////////////////////////////////////////////////////////////////////
        startButton = new JButton("Start");
        startButton.setSize(100, 40);
        startButton.setLayout(null);
        startButton.setBackground(Color.getHSBColor(0.33f, 0.8f, 1));
        startButton.setLocation(30, 670);

        stopButton = new JButton("Stop");
        stopButton.setSize(100, 40);
        stopButton.setLayout(null);
        stopButton.setBackground(Color.getHSBColor(0f, 0.8f, 0.8f));
        stopButton.setLocation(30, 715);
        stopButton.setEnabled(false);

        resetButton = new JButton("Reset");
        resetButton.setSize(95, 85);
        resetButton.setLayout(null);
        resetButton.setLocation(135, 670);
        resetButton.setEnabled(false);
        ///////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////
        //adding
        ///////////////////////////////////////////////////////////////////////////////////////
        this.add(startButton);
        this.add(stopButton);
        this.add(resetButton);

        this.add(title);
        this.add(numberOfColumns);
        this.add(numberOfRows);
        this.add(numberOfPlayers);
        this.add(numberOfEnemies);
        this.add(numberOfBalls);
        this.add(speedOfBall);

        this.add(columnsSpinner);
        this.add(rowsSpinner);
        this.add(playersSpinner);
        this.add(enemiesSpinner);
        this.add(ballsSpinner);
        this.add(speedSpinner);
    }
}
