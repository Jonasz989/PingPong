/**
 * Class for MenuLabels
 */

package graphics;

import javax.swing.*;

public class MyLabel extends JLabel {

    MyLabel(String text, int positionY, int positionX) {
        super(text);
        this.setSize(200, 25);
        this.setLayout(null);
        this.setLocation(positionX, positionY);
    }
}
