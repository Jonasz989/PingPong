/**
 * Whole class for Game with variables and settings needed for simulation
 */

package map;

import graphics.GamePanel;
import objects.Ball;
import generator.BallGenerator;
import objects.Opponent;
import objects.Ally;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;


public class Field {

    int fieldSpacing = 50;
    int fieldSize = 40;
    public String[][] Field;
    Ally[] ally;
    int numberOfAlly;
    Opponent[] opponent;
    int numberOfOpponents;
    Ball[] ball;
    int[] allyPositions;
    int[] opponentPositions;
    int[] ballsPositions;
    int columns;
    int rows;
    Random random = new Random();
    int activeBalls;
    int maxBalls;
    final BallGenerator ballGenerator;
    int ballSpeed;
    public final GamePanel gamePanel;
    int fixedY;
    int fixedX;
    final private String opponentName = "opponent";
    final private String ballName = "ball";
    final private String allyName = "ally";
    final private String emptyPool = "empty";


    public Field(int columns, int rows, int numberOfAlly, int numberOfOpponents, int maxBalls, GamePanel gamePanel, int ballSpeed) {
        this.fixedX = (950 - (columns * fieldSpacing)) / 2;
        this.fixedY = (800 - (rows * fieldSpacing)) / 2;
        this.gamePanel = gamePanel;
        this.columns = columns;
        this.rows = rows;
        this.numberOfOpponents = numberOfOpponents;
        this.numberOfAlly = numberOfAlly;
        this.activeBalls = maxBalls;
        this.maxBalls = maxBalls;
        this.ballSpeed = ballSpeed;
        this.ballGenerator = new BallGenerator(this);
        this.Field = new String[columns][rows];
        for (int i = 0; i < columns; i++) {
            if (i == 0 || i == columns - 1) {
                Arrays.fill(Field[i], "0");
            } else {
                Arrays.fill(Field[i], emptyPool);
            }

        }


        this.ball = new Ball[maxBalls];
        this.ally = new Ally[numberOfAlly];
        this.opponent = new Opponent[numberOfOpponents];
        initializeAllies(numberOfAlly);
        initializeOpponents(numberOfOpponents);
        initializeStartingBalls();
    }


    public void initializeAllies(int numberOfAllies) {
        int randomPosition;
        randomPosition = random.nextInt(this.rows);
        this.allyPositions = new int[numberOfAllies];
        this.allyPositions[0] = randomPosition;
        this.ally[0] = new Ally(randomPosition, this, 0);
        this.Field[1][randomPosition] = allyName;

        if (numberOfAllies > 1) {
            for (int i = 1; i < numberOfAllies; i++) {
                randomPosition = random.nextInt(this.rows);
                if (!Objects.equals(this.Field[1][randomPosition], allyName)) {
                    this.ally[i] = new Ally(randomPosition, this, i);
                    this.allyPositions[i] = randomPosition;
                    this.Field[1][randomPosition] = allyName;
                } else {
                    i--;
                }
            }
        }

        for (int i = 0; i < numberOfAllies; i++) {
            this.ally[i].setOccupiedPosition(this.allyPositions);
        }
    }

    public void initializeOpponents(int numberOfOpponents) {
        int randomPosition;
        randomPosition = random.nextInt(this.rows);
        this.opponentPositions = new int[numberOfOpponents];
        this.opponentPositions[0] = randomPosition;
        this.opponent[0] = new Opponent(randomPosition, this, 0, this.columns);
        this.Field[columns - 2][randomPosition] = opponentName;

        if (numberOfOpponents > 1) {
            for (int i = 1; i < numberOfOpponents; i++) {
                randomPosition = random.nextInt(rows);
                if (!Objects.equals(this.Field[columns - 2][randomPosition], opponentName)) {
                    this.opponent[i] = new Opponent(randomPosition, this, i, this.columns);
                    this.opponentPositions[i] = randomPosition;
                    this.Field[columns - 2][randomPosition] = opponentName;
                } else {
                    i--;
                }
            }
        }

        for (int i = 0; i < numberOfOpponents; i++) {
            this.opponent[i].setOccupiedPosition(this.opponentPositions);
        }
    }

    public void initializeStartingBalls() {
        int randomPosition;
        int direction;
        this.ballsPositions = new int[this.maxBalls];

        for (int i = 0; i < maxBalls; i++) {
            direction = random.nextInt(2);
            if (direction == 0)
                direction = -1;

            randomPosition = random.nextInt(this.rows);
            if (!Objects.equals(this.Field[(columns - 1) / 2][randomPosition], ballName)) {
                this.ball[i] = new Ball(randomPosition, this.columns, this, i, direction, ballSpeed);
                this.ballsPositions[i] = randomPosition;
                this.Field[(columns - 1) / 2][randomPosition] = ballName;
            } else {
                i--;
            }
        }


        for (int i = 0; i < maxBalls; i++) {
            this.ball[i].setOccupiedPosition(this.ballsPositions);
        }
    }

    public void initializeNewBall(Ball ball) {
        int randomPosition = random.nextInt(rows); //generating position
        int direction = random.nextInt(2); //generating direction of ball
        boolean isEmpty = true;
        if (direction == 0)
            direction = -1;

        for (int i = 0; i < maxBalls; i++) {
            if (ball.getBallId() != this.ball[i].getBallId() && randomPosition == this.ball[i].getPositionY()) {
                isEmpty = false;
                break;
            }
        }

        if (isEmpty) {
            this.ball[ball.getBallId()].setPositionY(randomPosition);
            this.ball[ball.getBallId()].setPositionX((columns - 1) / 2);
            this.ball[ball.getBallId()].setDirection(direction);
            this.ball[ball.getBallId()].setActive(true);
            activeBalls++;
        }
    }

    public synchronized void startAllies() { //synchronized ally movement
        for (Ally var : ally) {
            var.start();
        }
    }

    public synchronized void startOpponents() { //synchronized opponent movement
        for (Opponent var : opponent) {
            var.start();
        }
    }

    public synchronized void startBalls() { //synchronized ball movement
        for (int i = 0; i < maxBalls; i++) {
            ball[i].start();
        }
    }

    public synchronized void startBallGenerator() { //synchronized generator
        ballGenerator.start();
    }

    public synchronized void drawMap(Graphics2D g2) {  //synchronized map to not draw values from changed pools

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (checkField(i, j).equals(emptyPool)) {
                    g2.setColor(Color.white);
                    g2.drawRect((i * fieldSpacing) + fixedX, (j * fieldSpacing) + fixedY, fieldSize, fieldSize);

                } else if (checkField(i, j).equals(opponentName)) {
                    g2.setColor(Color.RED);
                    g2.fillRect((i * fieldSpacing) + fixedX, (j * fieldSpacing) + fixedY, fieldSize, fieldSize);
                    g2.setColor(Color.black);
                } else if (checkField(i, j).equals(allyName)) {
                    g2.setColor(Color.GREEN);
                    g2.fillRect((i * fieldSpacing) + fixedX, (j * fieldSpacing) + fixedY, fieldSize, fieldSize);
                } else if (checkField(i, j).equals(ballName)) {
                    g2.fillOval((i * fieldSpacing) + fixedX, (j * fieldSpacing) + fixedY, fieldSize, fieldSize);
                } else {
                    g2.setColor(Color.white);
                    g2.drawRect((i * fieldSpacing) + fixedX, (j * fieldSpacing) + fixedY, fieldSize, fieldSize);
                    g2.drawString(checkField(i, j), i * fieldSpacing + 20 + fixedX, j * fieldSpacing + 20 + fixedY);
                }
            }
        }
    }

    public String checkField(int positionX, int positionY) {
        return this.Field[positionX][positionY];
    }

    public void wakeGenerator() {
        synchronized (this.ballGenerator) { //synchronized to not wake generator more than needed

            activeBalls--; //decrementing active balls
            this.ballGenerator.notify(); //notifying generator
        }
    }

    public int[] points() {
        int[] es = new int[2];
        for (int i = 0; i < this.getRows() ; i++) {
            es[0] += Integer.parseInt(this.Field[0][i]);
            es[1] += Integer.parseInt(this.Field[this.getColumns() - 1][i]);
        }
        return es;
    }

    //////////////////////////////////////////////////////////////////////////
    //GETTERS AND SETTERS
    //////////////////////////////////////////////////////////////////////////
    public synchronized Ally getAlly(int a) {
        return ally[a];
    }

    public synchronized Opponent getOpponent(int a) {
        return opponent[a];
    }

    public Ball getBall(int a) {
        return ball[a];
    }

    public int getActiveBalls() {
        return activeBalls;
    }

    public int getMaxBalls() {
        return maxBalls;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getNumberOfAlly() {
        return numberOfAlly;
    }

    public int getNumberOfOpponents() {
        return numberOfOpponents;
    }

    public int getNumberOfBalls() {
        return maxBalls;
    }

}
