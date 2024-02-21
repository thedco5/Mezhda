package src;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.LayoutManager;

public class Panel extends JPanel {
    public Panel() {
        setBackground(Color.WHITE);
    }
    public Panel(LayoutManager lm) {
        this();
        setLayout(lm);
    }
}