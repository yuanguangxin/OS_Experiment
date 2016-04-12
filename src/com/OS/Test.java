package com.OS;

import java.util.Random;
import java.util.Scanner;

public class Test {
    public static Memory memory = new Memory();
    static int meta_F = 0;
    static int meta_L = 0;
    static int total = 0;

    public static int menu() {
        Scanner in = new Scanner(System.in);
        Conversion conversion = new Conversion();
        int choose = 0;
        System.out.println("请输入数字控制进程");
        System.out.println("1.创建进程");
        System.out.println("2.时间片到");
        System.out.println("3.进程阻塞");
        System.out.println("4.唤醒进程");
        System.out.println("5.结束进程");
        System.out.println("6.显示进程状态");
        System.out.println("7.访问逻辑地址");
        System.out.println("8.显示内存位示图");
        System.out.println("0.退出");
        String processName;
        choose = in.nextInt();
        switch (choose) {
            case 1:
                System.out.println("输入进程名：");
                processName = in.next();
                Process process = new Process(processName);
                int processSize;
                System.out.println("请输入进程大小(单位:字节)");
                processSize = in.nextInt();
                if(processSize>memory.getTotalSize()*1024){
                    System.out.println("超出最大内存！");
                    break;
                }
                int pageSize = memory.getBlockSize() * 1024;
                int totalPage = processSize / pageSize;
                process.setTotalSize(processSize);
                if (processSize % pageSize == 0) {
                    process.setTotalPage(totalPage);
                } else {
                    process.setTotalPage(totalPage + 1);
                }
                PageList[] pageLists1 = new PageList[process.getTotalPage()];
                PageList[] pageLists2 = new PageList[process.getTotalPage()];
                for (int p = 0; p < pageLists1.length; p++) {
                    pageLists1[p] = new PageList();
                    pageLists1[p].pageNum = p;
                    pageLists1[p].blockNum = -1;
                    pageLists1[p].status = false;
                    pageLists1[p].visitNum = 0;
                    pageLists2[p] = new PageList();
                    pageLists2[p].pageNum = p;
                    pageLists2[p].blockNum = -1;
                    pageLists2[p].status = false;
                    pageLists2[p].visitNum = 0;
                }
                int n;
                System.out.println("请输入固定块大小");
                n = in.nextInt();
                if(n>process.getTotalPage()){
                    System.out.println("超出最大块大小！");
                    break;
                }
                process.setBlockSize(n);
                int k = 0;
                for (int j = 0; j < memory.getBitMap().length; j++) {
                    if (memory.getBitMap()[j] == 0) {
                        memory.getBitMap()[j] = 1;
                        pageLists1[k].blockNum = j;
                        pageLists1[k].status = true;
                        pageLists1[k].visitNum = 0;
                        pageLists1[k].modify = false;
                        pageLists2[k].blockNum = j;
                        pageLists2[k].status = true;
                        pageLists2[k].visitNum = 0;
                        pageLists2[k].modify = false;
                        k++;
                    }
                    if (k == n) break;
                }
                process.setPageList_1(pageLists1);
                process.setPageList_2(pageLists2);
                for (int l = 0; l < process.getPageList_1().length; l++) {
                    if (process.getPageList_1()[l].blockNum != -1) {
                        process.setPageList_1(Util.toFirst(l, process.getPageList_1()));
                    }
                }
                for (int l = 0; l < process.getPageList_2().length; l++) {
                    if (process.getPageList_2()[l].blockNum != -1) {
                        process.setPageList_2(Util.toFirst(l, process.getPageList_2()));
                    }
                }
                process.showPageList1();
                conversion.createProcess(process);
                break;
            case 2:
                conversion.timeSliceTo();
                conversion.printState();
                break;
            case 3:
                conversion.processBlock();
                conversion.printState();
                break;
            case 4:
                conversion.wakeUpProcess();
                conversion.printState();
                break;
            case 5:
                if(Conversion.getRunning()==null){
                    System.out.println("没有正在执行的进程！");
                    break;
                }
                for (int i = 0; i < Conversion.getRunning().getPageList_1().length; i++) {
                    if(Conversion.getRunning().getPageList_1()[i].blockNum!=-1) {
                        memory.getBitMap()[Conversion.getRunning().getPageList_1()[i].blockNum] = 0;
                    }
                }
                conversion.endProcess();
                conversion.printState();
                break;
            case 6:
                conversion.printState();
                break;
            case 7:
                if(Conversion.getRunning()==null){
                    System.out.println("还没有执行的进程！");
                    break;
                }
                System.out.println("当前正在执行的程序为：" + Conversion.getRunning().getName());
                int logicalAddress;
                Process process1 = Conversion.getRunning();

                System.out.println("请输入想要访问的逻辑地址：");
                logicalAddress = in.nextInt();
                if (logicalAddress >= process1.getTotalSize()) {
                    System.out.println("输入错误!");
                    break;
                }
                total++;
                int thisPageNum = logicalAddress / (memory.getBlockSize() * 1024);
                String trueOrFalse;
                System.out.println("是否进行修改(yes/no):");
                trueOrFalse = in.next();
                if (trueOrFalse.equals("yes")) {
                    process1.findByPage1(thisPageNum).modify = true;
                    process1.findByPage2(thisPageNum).modify = true;
                }
                n = process1.getBlockSize();

                if (!process1.findByPage1(thisPageNum).status) {
                    process1.findByPage1(thisPageNum).status = true;
                    process1.getPageList_1()[n - 1].status = false;
                    process1.findByPage1(thisPageNum).blockNum = process1.getPageList_1()[n - 1].blockNum;
                    process1.getPageList_1()[n - 1].blockNum = -1;
                    meta_F++;
                }

                if (!process1.findByPage2(thisPageNum).status) {
                    process1.findByPage2(thisPageNum).status = true;
                    process1.getPageList_2()[n - 1].status = false;
                    process1.findByPage2(thisPageNum).blockNum = process1.getPageList_2()[n - 1].blockNum;
                    process1.getPageList_2()[n - 1].blockNum = -1;
                    meta_L++;
                }


                for (int l = 0; l < process1.getPageList_1().length; l++) {
                    if (process1.getPageList_1()[l].blockNum != -1) {
                        process1.setPageList_1(Util.toFirst(l, process1.getPageList_1()));
                    }
                }
                for (int l = 0; l < process1.getPageList_1().length; l++) {
                    if (process1.getPageList_1()[l].blockNum != -1) {
                        process1.setPageList_1(Util.toFirst(l, process1.getPageList_1()));
                    }
                }
                for (int l = 0; l < process1.getPageList_2().length; l++) {
                    if (process1.getPageList_2()[l].blockNum != -1) {
                        process1.setPageList_2(Util.toFirst(l, process1.getPageList_2()));
                    }
                }
                for (int l = 0; l < process1.getPageList_2().length; l++) {
                    if (process1.getPageList_2()[l].blockNum != -1) {
                        process1.setPageList_2(Util.toFirst(l, process1.getPageList_2()));
                    }
                }

                System.out.println("FIFO算法：");

                int thisBlockNum_1 = process1.findByPage1(thisPageNum).blockNum;
                int offset = logicalAddress % (memory.getBlockSize() * 1024);
                int physicalAddress_1 = (memory.getBlockSize() * 1024) * thisBlockNum_1 + offset;
                System.out.println("物理地址为：" + physicalAddress_1);
                process1.findByPage1(thisPageNum).visitNum++;


                process1.showPageList1();
                System.out.println("置换次数：" + meta_F);
                double rate_1 = (double) meta_F / total;
                double rates_1 = (double) (meta_F + n) / (total + n);
                System.out.println("缺页率(除去首次装入)：" + rate_1);
                System.out.println("缺页率(包含首次装入)：" + rates_1);

                System.out.println();

                System.out.println("LRU算法：");

                int thisBlockNum_2 = process1.findByPage2(thisPageNum).blockNum;
                int physicalAddress_2 = (memory.getBlockSize() * 1024) * thisBlockNum_2 + offset;
                System.out.println("物理地址为：" + physicalAddress_2);
                process1.findByPage2(thisPageNum).visitNum++;

                process1.setPageList_2(Util.toFirst(process1.getIndex2(thisPageNum), process1.getPageList_2()));
                process1.showPageList2();

                System.out.println("置换次数：" + meta_L);
                double rate_2 = (double) meta_L / total;
                double rates_2 = (double) (meta_L + n) / (total + n);
                System.out.println("缺页率(除去首次装入)：" + rate_2);
                System.out.println("缺页率(包含首次装入)：" + rates_2);
                break;
            case 8:
                memory.showBitMap();
                break;
        }
        return choose;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int memorySize;
        int blockSize;
        System.out.println("请输入内存容量(单位：k)");
        memorySize = in.nextInt();
        memory.setTotalSize(memorySize);
        System.out.println("请输入块大小(单位：k)");
        blockSize = in.nextInt();
        int[] bitMap = new int[memorySize / blockSize];
        for (int i = 0; i < bitMap.length; i++) {
            Random random = new Random();
            bitMap[i] = random.nextInt(2);
        }
        memory.setBlockSize(blockSize);
        memory.setTotalBlock(memorySize / blockSize);
        memory.setBitMap(bitMap);
        memory.showBitMap();
        while (true) {
            if (Test.menu() == 0) {
                break;
            }
        }
    }
}
