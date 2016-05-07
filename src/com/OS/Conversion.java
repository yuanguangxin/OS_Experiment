package com.OS;

import java.util.*;

public class Conversion {
    Scanner in = new Scanner(System.in);
    static LinkedList<Process> allProcess = new LinkedList<>();
    static LinkedList<Process> ready = new LinkedList<>();
    static LinkedList<Process> blocked = new LinkedList<>();
    static LinkedList<Process> running = new LinkedList<>();
    static LinkedList<Process> finished = new LinkedList<>();

    public LinkedList<Process> getAllProcess() {
        return allProcess;
    }

    public void setAllProcess(LinkedList<Process> allProcess) {
        Conversion.allProcess = allProcess;
    }

    public void addFinished(Process process) {
        finished.add(process);
    }

    public static Process getRunning() {
        if (running.size() == 0) return null;
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

    public Process getBlockFirst() {
        if (blocked.size() != 0) {
            return blocked.getFirst();
        } else {
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
        } else {
            System.out.println("无执行进程！");
        }
    }

    public void f1Sort() {
        for (int i = 0; i < allProcess.size(); i++) {
            for (int j = 0; j < allProcess.size() - 1; j++) {
                if (allProcess.get(j).getArrivalTime() > allProcess.get(j + 1).getArrivalTime()) {
                    Process t = allProcess.get(j);
                    Process t1 = allProcess.get(j + 1);
                    allProcess.remove(j);
                    allProcess.add(j, t1);
                    allProcess.remove(j + 1);
                    allProcess.add(j + 1, t);
                }
            }
        }
    }

    public void f2Sort() {
        int time = 0;
        LinkedList<Process> newAllProcess = new LinkedList<>();
        while (true) {
            if(allProcess.size()==0) break;
            for (int i = 0; i < allProcess.size(); i++) {
                if (allProcess.get(i).getArrivalTime() == time) {
                    time = time + allProcess.get(i).getBurstTime();
                    newAllProcess.add(allProcess.remove(i));
                    int min = 9999;
                    Process p = null;
                    for (int j = 0; j < allProcess.size(); j++) {
                        if (allProcess.get(j).getArrivalTime() <= time) {
                            if(allProcess.get(j).getBurstTime()<min){
                                min = allProcess.get(j).getBurstTime();
                                p = allProcess.get(j);
                            }
                        }
                    }
                    time = time + p.getBurstTime();
                    allProcess.remove(p);
                    newAllProcess.add(p);
                    continue;
                }
            }
            time++;
        }
        allProcess = newAllProcess;
        showSt();
    }

    public void cal() {
        double[] everyTime = new double[allProcess.size()];
        double[] everyTime1 = new double[allProcess.size()];
        for (int i = 0; i < allProcess.size(); i++) {
            everyTime[i] = allProcess.get(i).getFinishedTime() - allProcess.get(i).getArrivalTime();
            everyTime1[i] = everyTime[i] / allProcess.get(i).getBurstTime();
        }
        for (int i = 0; i < allProcess.size(); i++) {
            System.out.println("进程名：" + allProcess.get(i).getName());
            System.out.println("周转时间：" + everyTime[i]);
            System.out.println("带权周转时间：" + everyTime1[i]);
            System.out.println();
        }
        double total = 0;
        for (int i = 0; i < allProcess.size(); i++) {
            total = total + everyTime[i];
        }
        double total1 = 0;
        for (int i = 0; i < allProcess.size(); i++) {
            total1 = total1 + everyTime1[i];
        }
        System.out.println("平均周转时间：" + total / allProcess.size());
        System.out.println("平均带权周转时间：" + total1 / allProcess.size());
    }

    public int menu() {
        int op = 0;
        System.out.println("请选择：");
        System.out.println("1.查看状态");
        System.out.println("2.单位时间+1");
        op = in.nextInt();
        return op;
    }

    public void f1() {
        int i = 0;
        int j = 0;
        int tt = 0;
        int op = -1;
        while (true) {
            if (j == allProcess.size()) break;
            if (tt == 0) {
                op = menu();
            } else {
                tt = 0;
            }
            if (op == 1) {
                showSt();
                System.out.println("当前时间：" + i);
                continue;
            } else if (op == 2) {
                System.out.println(allProcess.get(j).getName() + "的arrTime：" + allProcess.get(j).getArrivalTime());
                if (allProcess.get(j).getArrivalTime() <= i) {
                    int needTime = allProcess.get(j).getBurstTime();
                    int t = i;
                    while (i < t + needTime) {
                        System.out.println(allProcess.get(j).getName() + "的runnedTime++");
                        allProcess.get(j).runnedTime++;
                        i++;
                        if (i == t + needTime) {
                            allProcess.get(j).setFinishedTime(i);
                        }
                        while (true) {
                            op = menu();
                            if (op == 2) {
                                if (allProcess.size() > j + 1) {
                                    if (allProcess.get(j + 1).getArrivalTime() <= i + 1) {
                                        tt = 1;
                                    }
                                }
                                break;
                            }
                            showSt();
                            System.out.println("当前时间：" + i);
                        }
                    }
                    if (allProcess.get(j).runnedTime == needTime) {
                        j++;
                        System.out.println("tt：" + tt);
                        if (tt == 1) {
                            i--;
                        }
                    }
                }
                i++;
            }
        }
        cal();
    }

    public void showSt() {
        for (int i = 0; i < allProcess.size(); i++) {
            System.out.println("进程名：" + allProcess.get(i).getName());
            System.out.println("到达时间：" + allProcess.get(i).getArrivalTime());
            System.out.println("服务时间：" + allProcess.get(i).getBurstTime());
            System.out.println("结束时间：" + allProcess.get(i).getFinishedTime());
            System.out.println("已运行时间：" + allProcess.get(i).runnedTime);
            System.out.println();
        }
    }
}
