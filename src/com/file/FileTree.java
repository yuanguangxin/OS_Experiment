package com.file;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.ArrayList;

public class FileTree extends JFrame implements TreeSelectionListener {
    public JTree tree = null;
    public FCB fcb = null;
    public DefaultMutableTreeNode top = null;

    public void addDirectory(FCB fcb, DefaultMutableTreeNode node) {
        ArrayList<FCB> list = fcb.getChildren();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == 2) {
                DefaultMutableTreeNode n = new DefaultMutableTreeNode(list.get(i));
                n.add(new DefaultMutableTreeNode("."));
                n.add(new DefaultMutableTreeNode(".."));
                node.add(n);
                this.addDirectory(list.get(i), n);
            } else if (list.get(i).getType() == 1) {
                node.add(new DefaultMutableTreeNode(list.get(i)));
            }
        }
    }

    public FileTree(FCB fcb) {
        this.fcb = fcb;

        top = new DefaultMutableTreeNode("根目录(~)");
        this.addDirectory(fcb, top);

        tree = new JTree(top);
        tree.addTreeSelectionListener(this);
        this.add(tree);
        this.setLocation(400, 80);
        this.setSize(450, 500);
        this.setVisible(true);
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                .getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        try{
            FCB object = (FCB) node.getUserObject();
            if (node.isLeaf()) {
                String s = "";
                String name = "文件名：" + object.toString();
                String times = "创建时间：" + object.getDatetime();
                String size = "文件大小：" + object.getSize()+"kb";
                s = s + name + "\n" + times + "\n" + size;
                JOptionPane.showMessageDialog(null, s, " 文件信息", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch (Exception e1){

        }
    }
}

