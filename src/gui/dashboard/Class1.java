package gui.dashboard;

import gui.listdata.Class2;

import javax.swing.*;
import java.awt.*;

public class Class1 {

    JFrame frame;

    public Class1() {
        frame = new JFrame("Screen 1");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnToScreen2 = new JButton("Ke Screen 2");


        btnToScreen2.addActionListener(e -> {
            new Class2();
            frame.dispose();
        });

        frame.setLayout(new FlowLayout());

        frame.add(btnToScreen2);
        frame.setVisible(true);
    }
}
