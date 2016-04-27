package com.file;

import java.util.*;

public class FCB {
    private String name;
    private int size;
    private int firstBlock;
    private int type;
    private String datetime;
    private FCB parent;
    private static ArrayList<FCB> children = new ArrayList<>();

    public FCB getParent() {
        return parent;
    }

    public void setParent(FCB parent) {
        this.parent = parent;
    }

    public ArrayList<FCB> getChildren() {
        return children;
    }

    public void addChildren(FCB fcb){
        this.children.add(fcb);
    }

    public void deleteFile(){
        this.type = 0;
    }

    public FCB findByName(String name){
        for(int i = 0;i<this.children.size();i++){
            if(this.children.get(i).getName().equals(name)&&this.children.get(i).getType()!=0){
                return this.children.get(i);
            }
        }
        return null;
    }

    public boolean isAvailable(String name){
        for(int i = 0;i<this.children.size();i++){
            if(this.children.get(i).getName().equals(name)&&this.children.get(i).getType()!=0){
                return false;
            }
        }
        return true;
    }

    public String showAllChildren(){
        String message = "";
        for(int i = 0;i<children.size();i++){
            if(children.get(i).getType()!=0) {
                String[] names = children.get(i).getName().split("/");
                String partName = names[names.length - 1];
                message += children.get(i).getDatetime() + "\t" + partName + "\n";
            }
        }
        return message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(int firstBlock) {
        this.firstBlock = firstBlock;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
