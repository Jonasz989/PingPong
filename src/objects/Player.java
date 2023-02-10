/**
 * Class for creating allies and opponents
 */

package objects;

import map.Field;

public abstract class Player extends MovingObj {


    public Player(int positionX, int positionY, int direction, Field field) {
        super(positionX, positionY, direction, field);
        currentThread().setPriority(3);

    }

    public boolean isDirectionBlocked() {
        for (int i = 0; i < field.getNumberOfAlly(); i++) {
            if (this.positionY + this.direction == field.getAlly(i).positionY)
                return true;
        }
        for (int i = 0; i < field.getNumberOfBalls(); i++) {
            if (field.getBall(i).getPositionX() == 1 && field.getBall(i).getPositionY() == this.positionY + this.direction)
                return true;
        }
        return false;
    }
}
