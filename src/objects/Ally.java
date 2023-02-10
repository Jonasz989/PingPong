package objects;

import map.Field;

public class Ally extends Player {

    public int playerId;
    int previousY;

    public Ally(int positionY, Field field, int id) {
        super(1, positionY, 1, field);
        currentThread().setPriority(3);
        currentThread().setName("ally");
        this.playerId = id;
        this.previousY = positionY;
    }

    @Override
    public void run() {
        while (field.gamePanel.isRunning) {
            synchronized (this) {
                move();
            }

            try {
                sleep(237 + (int)(Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void move() {

        if (this.positionY == 0 || this.positionY == field.getRows() - 1 || isDirectionBlocked()) //conditions for not moving
            this.direction *= -1; //changing direction
        if (!isDirectionBlocked() && (this.positionY + direction) < field.getRows() && (this.positionY + direction) >= 0) { // checking if move is available
            this.positionY += direction; //changing position
            String clear = "empty";
            field.Field[1][positionY - direction] = clear; //clearing current place
            String ally = "ally";
            field.Field[1][positionY] = ally; //assigning next position
            previousY = positionY - direction;
        } else
            previousY = positionY;
    }


}
