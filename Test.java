import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {
    
    static JPanel customTitleBar;
    public static void main(String[] args) {
        customTitleBar = new JPanel();
        customTitleBar.setLayout(new FlowLayout());

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.add(customTitleBar, BorderLayout.NORTH);
        frame.pack();
    }
}