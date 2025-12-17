package gui.screen3;

import javax.swing.*;

public class Class3 {
    JFrame frame;

    public Class3() {
        frame = new JFrame("Screen 3 (Dummy)");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.add(new JLabel("Holla, 3", SwingConstants.CENTER));
        frame.setVisible(true);
    }
}



