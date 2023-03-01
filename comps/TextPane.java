package comps;

import javax.swing.JTextPane;
import java.awt.Dimension;

import src.*;

public class TextPane extends JTextPane {

    public TextPane() {
        setFont(Chatter.font);
        setPreferredSize(getPreferredSize());
        setEditable(false);
    }

    @Override
    public Dimension getMinimumSize() {
        int height = (int) getSize().getHeight();
        int width = (int) (Chatter.chat_frame.getSize().getWidth());
        width -= Chatter.scroll_pane.getPreferredSize().getWidth();
        if (!Chatter.full_screen)
            width -= Chatter.scroll_bar.getWidth();
        width -= Chatter.split_pane.getDividerSize();
        if (width < Chatter.scroll_pane.getPreferredSize().getWidth())
            width = (int) Chatter.scroll_pane.getPreferredSize().getWidth();
        return new Dimension(width, height);
    }
}