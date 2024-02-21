package comps.scrolls;

import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JScrollBar;

import java.awt.Component;

public class ScrollPane extends JScrollPane {
    public ScrollPane(Component viewport, JScrollBar scroll_bar) {
        super(viewport);
        
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setBorder(BorderFactory.createEmptyBorder());

        // setViewportView(viewport);
        setVerticalScrollBar(scroll_bar);

        // setPreferredSize(getPreferredSize());
    }
}