package com.OS;

public class Util {

    public static PageList[] toFirst(int index, PageList[] dataArray){
        if(index>=dataArray.length || index<0){
            System.out.println("数组下标越界！");
            return null;
        }
        for(int i=0;i<index;i++){
            PageList temp=dataArray[i];
            dataArray[i]=dataArray[index];
            dataArray[index]=temp;
        }
        return dataArray;
    }

    public static PageList[] toLast(int index, PageList[] dataArray){
        if(index>=dataArray.length-1 || index<0){
            System.out.println("数组下标越界！");
            return null;
        }
        for(int i=dataArray.length-1;i>index;i--){
            PageList temp=dataArray[i];
            dataArray[i]=dataArray[index];
            dataArray[index]=temp;
        }
        return dataArray;
    }

    public static PageList[] reserve(PageList[] array){
        for(int i = 0;i<array.length/2;i++){
            PageList temp = array[i];
            array[i] = array[array.length-i-1];
            array[array.length-i-1]=temp;
        }
        return array;
    }

}
