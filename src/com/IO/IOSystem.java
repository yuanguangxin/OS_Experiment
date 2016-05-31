package com.IO;

import java.util.LinkedList;
import java.util.List;

public class IOSystem {
    public static List<DCT> dcts = new LinkedList<>();
    public static List<COCT> cocts = new LinkedList<>();
    public static List<CHCT> chcts = new LinkedList<>();

    public IOSystem() {
        CHCT chct1 = new CHCT();
        CHCT chct2 = new CHCT();
        chct1.setName("通道1");
        chct2.setName("通道2");
        COCT coct1 = new COCT();
        COCT coct2 = new COCT();
        COCT coct3 = new COCT();
        coct1.setName("控制器1");
        coct1.setParent(chct1);
        coct2.setName("控制器2");
        coct2.setParent(chct1);
        coct3.setName("控制器3");
        coct3.setParent(chct2);
        DCT dct1 = new DCT();
        DCT dct2 = new DCT();
        DCT dct3 = new DCT();
        DCT dct4 = new DCT();
        dct1.setName("keyboard");
        dct1.setParent(coct1);
        dct2.setName("mouse");
        dct2.setParent(coct1);
        dct3.setName("printer");
        dct3.setParent(coct2);
        dct4.setName("monitor");
        dct4.setParent(coct2);
        chcts.add(chct1);
        chcts.add(chct2);
        cocts.add(coct1);
        cocts.add(coct2);
        cocts.add(coct3);
        dcts.add(dct1);
        dcts.add(dct2);
        dcts.add(dct3);
        dcts.add(dct4);
    }

    public void showIOSystem() {
        System.out.println("通道控制表：");
        System.out.println("名称\t\t占用进程\t\t等待队列");
        for (int i = 0; i < chcts.size(); i++) {
            if(chcts.get(i).getProcess()!=null) {
                System.out.println(chcts.get(i).getName() + "\t\t" + chcts.get(i).getProcess().getName()
                        + "\t\t" + chcts.get(i).showWaitingList());
            }
        }

        System.out.println("控制器控制表：");
        System.out.println("名称\t\t占用进程\t\t等待队列\t\t父节点名称");
        for (int i = 0; i < cocts.size(); i++) {
            if(cocts.get(i).getProcess()!=null) {
                System.out.println(cocts.get(i).getName() + "\t\t" + cocts.get(i).getProcess().getName()
                        + "\t\t" + cocts.get(i).showWaitingList() +
                        "\t\t" + cocts.get(i).getParent().getName());
            }
        }

        System.out.println("设备控制表：");
        System.out.println("名称\t\t占用进程\t\t等待队列\t\t父节点名称");
        for (int i = 0; i < dcts.size(); i++) {
            if(dcts.get(i).getProcess()!=null) {
                System.out.println(dcts.get(i).getName() + "\t\t" + dcts.get(i).getProcess().getName()
                        + "\t\t" + dcts.get(i).showWaitingList() +
                        "\t\t" + dcts.get(i).getParent().getName());
            }
        }
    }
}
