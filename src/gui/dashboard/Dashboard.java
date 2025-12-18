package gui.dashboard;

import gui.listdata.ListData;

import javax.swing.*;
import java.awt.*;

public class Dashboard {

    JFrame frame;

    public Dashboard() {
        frame = new JFrame("Screen 1");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new JLabel("Cek label", SwingConstants.CENTER));
        frame.setVisible(true);
    }
}
