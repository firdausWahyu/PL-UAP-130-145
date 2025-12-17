package gui.history;

import javax.swing.*;

public class History {
    JFrame frame;

    public History() {
        frame = new JFrame("Screen 4 (Dummy)");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.add(new JLabel("Holla, 4", SwingConstants.CENTER));
        frame.setVisible(true);
    }
}
