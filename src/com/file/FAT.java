package com.file;

import javax.swing.*;
import java.awt.*;

public class FAT extends JFrame {
    private String[] fatTable;
    private JLabel[] jLabels;

    public String[] getFatTable() {
        return fatTable;
    }

    public void setFatTable(String[] fatTable) {
        this.fatTable = fatTable;
    }

    public void repaint() {
        for (int i = 0; i < jLabels.length; i++) {
            jLabels[i].setText(fatTable[i]);
        }
    }

    public FAT(int num) {
        fatTable = new String[num * 8];
        jLabels = new JLabel[num * 8];
        for (int i = 0; i < jLabels.length; i++) {
            jLabels[i] = new JLabel(fatTable[i], JLabel.CENTER);
            jLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        this.setLayout(new GridLayout(num, 8));
        for (int j = 0; j < jLabels.length; j++) {
            this.add(jLabels[j]);
        }
        this.setLocation(400, 80);
        this.setSize(500, 540);
        this.setVisible(false);
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
