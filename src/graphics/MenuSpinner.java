/**
 * Class with Spinner input field for getting parameters
 */

package graphics;

import javax.swing.*;
import java.awt.*;

public class MenuSpinner extends JSpinner {


    public MenuSpinner(SpinnerNumberModel model, int positionY, int positionX) {

        this.setSize(new Dimension(200, 38));
        this.setBackground(Color.getHSBColor(0.083f, 0.8f, 0.99f));
        this.setModel(model);
        this.setLocation(positionX, positionY);
    }

}
