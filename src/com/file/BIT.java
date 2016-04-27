package com.file;

import javax.swing.*;
import java.awt.*;

public class BIT extends JFrame{
    private int[] bits;
    private JLabel[] jLabels;

    public int[] getBits() {
        return bits;
    }

    public void setBits(int[] bits) {
        this.bits = bits;
    }

    public void repaint(){
        for (int i = 0; i < bits.length; i++) {
            jLabels[i].setText(Integer.toString(bits[i]));
        }
    }
    public BIT(int num){
        bits = new int[num * 8];
        jLabels = new JLabel[num * 8];
        for (int i = 0; i < jLabels.length; i++) {
            jLabels[i] = new JLabel(Integer.toString(bits[i]),JLabel.CENTER);
            jLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        this.setLayout(new GridLayout(bits.length/8,8));
        for(int j = 0;j<jLabels.length;j++){
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
