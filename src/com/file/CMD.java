package com.file;

import com.OS.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;

public class CMD extends JFrame implements KeyListener {
    public static final int EMPTY_BLOCK = 0;
    public static final int LAST_BLOCK = -1;
    private FCB root = new FCB();
    private JTextArea jt1 = new JTextArea();
    private FAT fat;
    private BIT bit;
    private String path = new String("Administrator@С▒▒▒▒ MINGW64 ~");
    private FCB currentDirectory;
    int diskSize;
    int blockSize;

    public CMD(int diskSize, int blockSize) {
        this.diskSize = diskSize;
        this.blockSize = blockSize;
        int num = diskSize * 1024 / blockSize / 8;
        jt1.setForeground(Color.WHITE);
        jt1.setBackground(Color.BLACK);
        jt1.setText("\n" + path + "\n" + "$ ");
        jt1.addKeyListener(this);
        this.add(jt1);
        this.setSize(500, 600);
        this.setLocation(400, 80);
        this.setTitle("cmd.exe");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fat = new FAT(num);
        bit = new BIT(num);
        String name = "~";
        root.setName(name);
        root.setType(2);
        root.setDatetime(Util.getDate());
        root.setSize(1);
        root.setFirstBlock(0);
        root.setParent(null);
        currentDirectory = root;
        this.fat.getFatTable()[0] = "-1";
        this.fat.repaint();
        this.bit.getBits()[0] = 1;
        this.bit.repaint();
    }

    public static void main(String[] args) {
        new CMD(1, 8);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            String[] con = this.jt1.getText().split("\n");
            String content = con[con.length - 1].substring(2, con[con.length - 1].length());
            String[] ops = content.split(" ");
            String op = ops[0];
            if (op.equals("showBits")) {
                this.bit.setVisible(true);
            } else if (op.equals("showFAT")) {
                this.fat.setVisible(true);
            } else if (op.equals("MD") || op.equals("md")) {
                String name = ops[1];
                String wholeName = currentDirectory.getName() + "/" + name;
                if (currentDirectory.isAvailable(wholeName)) {
                    FCB fcb = new FCB();
                    int blockNum = 0;
                    for (int i = 0; i < bit.getBits().length; i++) {
                        if (bit.getBits()[i] == 0) {
                            blockNum = i;
                            this.fat.getFatTable()[i] = "-1";
                            this.fat.repaint();
                            this.bit.getBits()[i] = 1;
                            this.bit.repaint();
                            break;
                        }
                    }
                    if (blockNum != 0) {
                        fcb.setName(wholeName);
                        fcb.setType(2);
                        fcb.setDatetime(Util.getDate());
                        fcb.setSize(0);
                        fcb.setParent(currentDirectory);
                        fcb.setFirstBlock(blockNum);
                        currentDirectory.addChildren(fcb);
                    } else {
                        System.out.println("磁盘空间已满！");
                    }
                } else {
                    String s = this.jt1.getText();
                    s+="Directory is already exist！" + "\n";
                    this.jt1.setText(s);
                    //System.out.println("Directory is already exist！");
                }
            } else if (op.equals("cd") || op.equals("CD")) {
                String partPath = ops[1];
                String wholePath = currentDirectory.getName() + "/" + partPath;
                if (!currentDirectory.isAvailable(wholePath)&&currentDirectory.findByName(wholePath).getType()!=1) {
                    path = "Administrator@С▒▒▒▒ MINGW64 " + wholePath;
                    currentDirectory = currentDirectory.findByName(wholePath);
                } else {
                    String s = this.jt1.getText();
                    s+="No such file or directory！" + "\n";
                    this.jt1.setText(s);
                   // System.out.println("No such file or directory！");
                }
            } else if (op.equals("rd") || op.equals("RD")) {
                if(ops.length!=2){
                    String s = this.jt1.getText();
                    s+="输入格式错误！" + "\n";
                    this.jt1.setText(s);
                }else {
                    String partPath = ops[1];
                    String wholePath = currentDirectory.getName() + "/" + partPath;
                    if (!currentDirectory.isAvailable(wholePath)) {
                        int i = this.currentDirectory.findByName(wholePath).getFirstBlock();
                        this.fat.getFatTable()[i] = "";
                        this.fat.repaint();
                        this.bit.getBits()[i] = 0;
                        this.bit.repaint();
                        this.currentDirectory.findByName(wholePath).deleteFile();
                    } else {
                        String s = this.jt1.getText();
                        s += "No such file or directory！" + "\n";
                        this.jt1.setText(s);
                        // System.out.println("No such file or directory！");
                    }
                }
            } else if (op.equals("dir") || op.equals("DIR")) {
                String s = this.jt1.getText();
                s+=currentDirectory.showAllChildren();
                this.jt1.setText(s);
               // System.out.println(currentDirectory.showAllChildren());
            } else if (op.
                    equals("mk") || op.equals("MK")) {
                if(ops.length!=3){
                    String s = this.jt1.getText();
                    s+="输入格式错误！" + "\n";
                    this.jt1.setText(s);
                }else {
                    String partName = ops[1];
                    int leftSize = 0;
                    if (currentDirectory.isAvailable(currentDirectory.getName() + "/" + ops[1])) {
                        for (int i = 0; i < this.bit.getBits().length; i++) {
                            if (this.bit.getBits()[i] == 0) {
                                leftSize += blockSize;
                            }
                        }
                        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                        if (pattern.matcher(ops[2]).matches()) {
                            int size = Integer.parseInt(ops[2]);
                            if (size > leftSize) {
                                System.out.println("磁盘容量不足！");
                            } else {
                                int nums = 0;
                                if (size % blockSize == 0) {
                                    nums = size / blockSize;
                                } else {
                                    nums = size / blockSize + 1;
                                }
                                int first = 0;
                                FCB fcb = new FCB();
                                fcb.setDatetime(Util.getDate());
                                fcb.setParent(currentDirectory);
                                currentDirectory.addChildren(fcb);
                                for (int i = 0; i < this.bit.getBits().length; i++) {
                                    if (this.bit.getBits()[i] == 0) {
                                        first = i;
                                        break;
                                    }
                                }
                                fcb.setFirstBlock(first);
                                fcb.setType(1);
                                fcb.setName(currentDirectory.getName() + "/" + partName);
                                fcb.setSize(size);
                                int[] ff = new int[nums];
                                for (int i = 0; i < nums; i++) {
                                    for (int j = 0; j < this.bit.getBits().length; j++) {
                                        if (this.bit.getBits()[j] == 0) {
                                            ff[i] = j;
                                            this.bit.getBits()[j] = 1;
                                            System.out.println("ff[i]:" + j);
                                            break;
                                        }
                                    }
                                }

                                for (int i = 0; i < ff.length - 1; i++) {
                                    this.bit.getBits()[ff[i]] = 1;
                                    this.fat.getFatTable()[ff[i]] = Integer.toString(ff[i + 1]);
                                }
                                this.bit.getBits()[ff[nums - 1]] = 1;
                                this.fat.getFatTable()[ff[nums - 1]] = "-1";
                                this.fat.repaint();
                                this.bit.repaint();
                            }
                        } else {
                            String s = this.jt1.getText();
                            s += "格式错误！" + "\n";
                            this.jt1.setText(s);
                            // System.out.println("格式错误！");
                        }
                    } else {
                        String s = this.jt1.getText();
                        s += "File is already exist！" + "\n";
                        this.jt1.setText(s);
                        // System.out.println("File is already exist！");
                    }
                }
            }else if(op.equals("del")||op.equals("DEL")) {
                if (ops.length != 2) {
                    String s = this.jt1.getText();
                    s += "输入格式错误！" + "\n";
                    this.jt1.setText(s);
                } else {
                    String partName = ops[1];
                    FCB fcb = currentDirectory.findByName(currentDirectory.getName() + "/" + partName);
                    int firstBlock = fcb.getFirstBlock();
                    int temp = firstBlock;
                    while (!this.fat.getFatTable()[temp].equals("-1")) {
                        int tt = Integer.parseInt(this.fat.getFatTable()[temp]);
                        this.fat.getFatTable()[temp] = "";
                        this.bit.getBits()[temp] = 0;
                        temp = tt;
                    }
                    this.fat.getFatTable()[temp] = "";
                    this.bit.getBits()[temp] = 0;
                    this.fat.repaint();
                    this.bit.repaint();
                    fcb.deleteFile();
                }
            }else if(op.equals("cd..")||op.equals("CD..")){
                currentDirectory = currentDirectory.getParent();
                path = "Administrator@С▒▒▒▒ MINGW64 " + currentDirectory.getName();
            }else if(op.equals("cd/")||op.equals("CD/")){
                currentDirectory = root;
                path = "Administrator@С▒▒▒▒ MINGW64 " + root.getName();
            }

            String s = this.jt1.getText();
            s = s + "\n" + path + "\n" + "$ ";
            jt1.setText(s);
        }
    }
}