package util;

/*
 * @class name:ThreadInfo
 * @author:Wu Gang
 * @create: 2018-12-26 21:37
 * @description:
 */
public class ThreadInfo {
    public static void threadPrint(){
        ThreadGroup currentGroup =
                Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++)
            System.out.println("线程号： " + lstThreads[i].getName());
        System.out.println("线程总数量：" + noThreads);
    }

    public static void shutDown(){

    }
}
