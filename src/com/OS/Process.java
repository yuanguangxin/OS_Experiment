package com.OS;

public class Process{
    private String name;
    private int blockSize;//固定块大小
    private int totalSize;//进程总大小
    private int totalPage;//进程总页数
    private PageList[] pageList_1;//页表1
    private PageList[] pageList_2;//页表2
    private int arrivalTime;
    private int burstTime;
    private int finishedTime = -1;
    public int runnedTime = 0;

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(int finishedTime) {
        this.finishedTime = finishedTime;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public Process(){

    }

    public Process(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageList findByPage1(int pageNum) {
        for (int i = 0; i < pageList_1.length; i++) {
            if (pageList_1[i].pageNum == pageNum) return pageList_1[i];
        }
        return null;
    }

    public PageList findByPage2(int pageNum) {
        for (int i = 0; i < pageList_2.length; i++) {
            if (pageList_2[i].pageNum == pageNum) return pageList_2[i];
        }
        return null;
    }

    public int getIndex1(int pageNum) {
        for (int i = 0; i < pageList_1.length; i++) {
            if (pageList_1[i].pageNum == pageNum) return i;
        }
        return 0;
    }

    public int getIndex2(int pageNum) {
        for (int i = 0; i < pageList_2.length; i++) {
            if (pageList_2[i].pageNum == pageNum) return i;
        }
        return 0;
    }

    public void showPageList1() {
        System.out.println("页号\t" + "块号\t" + "状态位\t" + "访问字段\t" + "修改位");
        for (int i = 0; i < pageList_1.length; i++) {
            System.out.println(pageList_1[i].pageNum + "\t" + pageList_1[i].blockNum + "\t" + pageList_1[i].status + "\t" + pageList_1[i].visitNum + "\t\t" + pageList_1[i].modify);
        }
        System.out.println();
    }

    public void showPageList2() {
        System.out.println("页号\t" + "块号\t" + "状态位\t" + "访问字段\t" + "修改位");
        for (int i = 0; i < pageList_2.length; i++) {
            System.out.println(pageList_2[i].pageNum + "\t" + pageList_2[i].blockNum + "\t" + pageList_2[i].status + "\t" + pageList_2[i].visitNum + "\t\t" + pageList_2[i].modify);
        }
        System.out.println();
    }


    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public PageList[] getPageList_1() {
        return pageList_1;
    }

    public void setPageList_1(PageList[] pageList_1) {
        this.pageList_1 = pageList_1;
    }

    public PageList[] getPageList_2() {
        return pageList_2;
    }

    public void setPageList_2(PageList[] pageList_2) {
        this.pageList_2 = pageList_2;
    }
}

