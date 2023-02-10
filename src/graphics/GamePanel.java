/**
 * Class for Game Panel
 */

package graphics;

import map.Field;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    Field field;
    public boolean isRunning;

    public GamePanel() {
        isRunning = false;
        this.setSize(950, 900);
        this.setBackground(Color.getHSBColor(0.65f, 0.40f, 1));
        this.setLocation(250, 0);
        this.setLayout(null);
    }

    public void startGame() {

        field.startAllies();
        field.startOpponents();
        field.startBalls();
        field.startBallGenerator();

        while (isRunning) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.repaint();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (field != null) {
            field.drawMap(g2);
        }
        g2.dispose();
    }

}
