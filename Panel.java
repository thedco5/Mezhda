import javax.swing.JPanel;
import java.awt.LayoutManager;
import java.awt.Color;

public class Panel extends JPanel {
    Panel() {
        setBackground(Color.WHITE);
    }
    Panel(LayoutManager lm) {
        this();
        setLayout(lm);
    }
}