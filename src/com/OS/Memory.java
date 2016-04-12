package com.OS;

//内存
public class Memory {
    private int totalSize;
    private int blockSize;
    private int totalBlock;
    private int[] bitMap;//位示图

    public void showBitMap() {
        System.out.print("位示图为：");
        for (int i = 0; i < bitMap.length; i++) {
            if (i % 8 == 0) System.out.println();
            System.out.print(bitMap[i] + " ");
        }
        System.out.println();
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getTotalBlock() {
        return totalBlock;
    }

    public void setTotalBlock(int totalBlock) {
        this.totalBlock = totalBlock;
    }

    public int[] getBitMap() {
        return bitMap;
    }

    public void setBitMap(int[] bitMap) {
        this.bitMap = bitMap;
    }
}
