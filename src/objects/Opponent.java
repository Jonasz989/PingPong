package objects;


import map.Field;

public class Opponent extends Player {


    public int opponentId;
    int previousY;


    public Opponent(int positionY, Field field, int id, int columns) {
        super(columns - 2, positionY, 1, field);
        currentThread().setPriority(3);
        currentThread().setName("opponent");
        this.opponentId = id;
        this.previousY = positionY;
    }

    @Override
    public void run() {
        while (field.gamePanel.isRunning) {
            synchronized (this) {
                move();
            }

            try {
                sleep(258 + (int)(Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isDirectionBlocked() {
        for (int i = 0; i < field.getNumberOfOpponents(); i++) {
            if (this.positionY + this.direction == field.getOpponent(i).positionY)
                return true;
        }
        for (int i = 0; i < field.getNumberOfBalls(); i++) {
            if (field.getBall(i).getPositionX() == field.getColumns() - 2 && field.getBall(i).getPositionY() == this.positionY + this.direction)
                return true;
        }
        return false;
    }

    public synchronized void move() {

        if (this.positionY == 0 || this.positionY == field.getRows() - 1 || isDirectionBlocked()) //conditions for not moving
            this.direction *= -1; //changing direction
        if (!isDirectionBlocked() && this.positionY + direction < field.getRows() && (this.positionY + direction) >= 0) {
            this.positionY += direction; //changing position
            String clear = "empty";
            field.Field[field.getColumns() - 2][positionY - direction] = clear; //clearing current place
            String opponent = "opponent";
            field.Field[field.getColumns() - 2][positionY] = opponent; //assigning next position
            previousY = positionY - direction;
        } else
            previousY = positionY;
    }


}