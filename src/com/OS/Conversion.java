package com.OS;

import java.util.*;

public class Conversion {
    static LinkedList<Process> allProcess = new LinkedList<>();
    static LinkedList<Process> ready = new LinkedList<>();
    static LinkedList<Process> blocked = new LinkedList<>();
    static LinkedList<Process> running = new LinkedList<>();

    public static Process getRunning(){
        if(running.size()==0) return null;
        return running.get(0);
    }

    //根据名字得到进程
    public Process getPCBByName(String name) {
        for (Process process : allProcess) {
            if (process.getName().equals(name)) {
                return process;
            }
        }
        return null;
    }

    //显示所有进程状态
    public void printState() {
        System.out.print("所有进程：");
        for (Process process : allProcess) {
            System.out.print(process.getName() + " ");
        }
        System.out.println();
        System.out.print("就绪进程：");
        for (Process process : ready) {
            System.out.print(process.getName() + " ");
        }
        System.out.println();
        System.out.print("执行进程：");
        for (Process process : running) {
            System.out.print(process.getName() + " ");
        }
        System.out.println();
        System.out.print("阻塞进程：");
        for (Process process : blocked) {
            System.out.print(process.getName() + " ");
        }
        System.out.println();
    }

    //创建进程
    public void createProcess(Process process) {
        allProcess.add(process);
        ready.addLast(process);
        if (running.size() == 0) {
            Process toRunning = ready.removeFirst();
            running.add(toRunning);
        }
    }

    //时间片到
    public void timeSliceTo() {
        if (running.size() != 0) {
            Process toReady = running.removeFirst();
            ready.addLast(toReady);
            Process toRunning = ready.removeFirst();
            running.add(toRunning);
        } else {
            System.out.println("无执行进程！");
        }
    }

    //阻塞进程
    public void processBlock() {
        if (running.size() != 0) {
            Process toBlock = running.removeFirst();
            blocked.add(toBlock);
            if (ready.size() != 0) {
                Process toRunning = ready.removeFirst();
                running.add(toRunning);
            }
        } else {
            System.out.println("无执行进程！");
        }
    }

    public Process getBlockFirst(){
        if(blocked.size()!=0) {
            return blocked.getFirst();
        }else{
            return null;
        }
    }

    //唤醒进程
    public void wakeUpProcess() {
        if (blocked.size() != 0) {
            Process process = blocked.removeFirst();
            ready.add(process);
            if (running.size() == 0) {
                Process toRunning = ready.removeFirst();
                running.add(toRunning);
            }
        } else {
            System.out.println("无阻塞进程！");
        }
    }

    //结束进程
    public void endProcess() {
        if (running.size() != 0) {
            Process process = running.removeFirst();
            allProcess.remove(process);
            if (running.size() == 0) {
                if (ready.size() != 0) {
                    Process toRunning = ready.removeFirst();
                    running.add(toRunning);
                }
            }
        }else{
            System.out.println("无执行进程！");
        }
    }
}
