package com.IO;

import com.OS.Process;

import java.util.Vector;

public class DCT {
    private String name;
    private Process process;
    private Vector<Process> waitingList = new Vector<>();
    private COCT parent;

    public String showWaitingList() {
        String s = "";
        for (int i = 0; i < waitingList.size(); i++) {
            s += waitingList.get(i).getName() + ",";
        }
        return s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Vector<Process> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(Vector<Process> waitingList) {
        this.waitingList = waitingList;
    }

    public COCT getParent() {
        return parent;
    }

    public void setParent(COCT parent) {
        this.parent = parent;
    }
}
