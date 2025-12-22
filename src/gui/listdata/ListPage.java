package gui.listdata;

import javax.swing.*;

public class ListPage {
    JFrame frame;

    public ListPage() {
        frame = new JFrame("Screen 2 (Dummy)");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.add(new JLabel("Holla 2", SwingConstants.CENTER));
        frame.setVisible(true);
    }
}
