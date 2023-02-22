import javax.swing.JPanel;

import java.awt.Color;
import java.awt.LayoutManager;

public class Panel extends JPanel {
    Panel() {
        setBackground(Color.WHITE);
    }
    Panel(LayoutManager lm) {
        this();
        setLayout(lm);
    }
}