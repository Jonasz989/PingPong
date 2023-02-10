package generator;

import map.Field;

public class BallGenerator extends Thread {

    private final Field field;

    public BallGenerator(Field field) {
        currentThread().setPriority(MIN_PRIORITY);
        currentThread().setName("Generate");
        this.field = field;
    }

    @Override
    public void run() {
        while (field.gamePanel.isRunning) {
            generateBalls(); //while the game is running we are generating balls
        }
    }

    public void generateBalls() {

        synchronized (this) { //synchronizing generating new ball so there wont be more balls generated in the same position or more than maxballs
            if (field.getActiveBalls() == field.getMaxBalls()) { //comparing balls on the field vs maximum number of balls
                try {
                    wait();
                    sleep(553 + (int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                for (int i = 0; i < field.getMaxBalls(); i++) {
                    if ((field.getBall(i).getPositionX() == 0 || field.getBall(i).getPositionX() == field.getColumns() - 1) && !field.getBall(i).isActive) {
                        field.initializeNewBall(field.getBall(i));
                    }
                }
            }
        }
    }
}
