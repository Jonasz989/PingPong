package objects;

import map.Field;

public abstract class MovingObj extends Thread {

    public final Field field;
    int positionY;
    int positionX;
    int direction;
    boolean isRunning;

    public MovingObj(int positionX, int positionY, int direction, Field field) {
        this.positionY = positionY;
        this.positionX = positionX;
        this.direction = direction;
        this.isRunning = true;
        this.field = field;

    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setOccupiedPosition(int[] positions) {
        int[] occupiedPositions = new int[positions.length - 1];
        for (int i = 0, g = 0; i < positions.length; i++) {
            if (positions[i] != positionY) {
                occupiedPositions[g] = positions[i];
                g++;
            }
        }
    }

}
