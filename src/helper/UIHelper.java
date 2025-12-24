package helper;

import javax.swing.*;
import java.awt.*;

public class UIHelper {

    private UIHelper() {
        // Mencegah instansiasi
    }

    public static void styleButton(JButton btn, Color bg, int fontSize) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, fontSize));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
    }

    public static void labelTitle(JLabel lblTitle, int size, int alignment, int top, int left, int bottom, int right){
        lblTitle.setFont(new Font("Arial", Font.BOLD, size));
        lblTitle.setHorizontalAlignment(alignment);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }
}
