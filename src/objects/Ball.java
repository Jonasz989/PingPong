package objects;

import map.Field;


public class Ball extends MovingObj {

    int previousX;
    int ballId;
    int speed;
    public boolean isActive;
    final private String emptyPool = "empty";
    final private String ballPool = "ball";

    public Ball(int positionY, int columns, Field field, int id, int direction, int speed) {
        super((columns - 1) / 2, positionY, direction, field);
        currentThread().setPriority(NORM_PRIORITY);
        currentThread().setName(ballPool);
        this.ballId = id;
        this.previousX = positionX;
        this.speed = speed;
        this.isActive = true;
    }

    @Override
    public void run() {
        while (field.gamePanel.isRunning) {
            synchronized (this) {
                moveBall();
            }
            try {
                sleep(555 / speed + (int) (Math.random() * 10));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void moveBall() {

        checkIfBounce();
        if ((this.positionX + direction) < (field.getColumns() - 1) && (this.positionX + direction) > 0 && isActive) { //going to the left/right side but bouncing
            this.positionX += direction;
            field.Field[positionX - direction][positionY] = emptyPool;
            field.Field[positionX][positionY] = ballPool;
            previousX = positionX - direction;
        } else if (this.positionX + direction == (field.getColumns() - 1) && isActive) { //being on the right side in the end point
            this.positionX += direction;
            field.Field[positionX - direction][positionY] = emptyPool;
            field.Field[positionX][positionY] = Integer.toString(Integer.parseInt(field.Field[positionX][positionY]) + 1);
            isActive = false;
            respawnBall();
        } else if (this.positionX + direction == 0 && isActive) { //being on the left side in the end point
            this.positionX += direction;
            field.Field[positionX - direction][positionY] = emptyPool;
            field.Field[positionX][positionY] = Integer.toString(Integer.parseInt(field.Field[positionX][positionY]) + 1);
            isActive = false;
            respawnBall();

        }
    }


    public void checkIfBounce() {

        if (positionX + direction == 1) {
            for (int i = 0; i < field.getNumberOfAlly(); i++) {
                if (positionY == field.getAlly(i).getPositionY()) { //synchronized
                    direction *= -1;
                }
            }
        } else if (positionX + direction == field.getColumns() - 2) {
            for (int i = 0; i < field.getNumberOfOpponents(); i++) {
                if (positionY == field.getOpponent(i).getPositionY()) { //synchronized
                    direction *= -1;
                }
            }
        }

    }

    public void respawnBall() {
        field.wakeGenerator();
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getBallId() {
        return ballId;
    }
}
