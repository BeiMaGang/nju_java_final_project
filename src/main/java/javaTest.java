import logic.creatures.Creature;
import logic.creatures.Monster;
import logic.creatures.calabashBrother.DaWa;

import java.util.concurrent.TimeUnit;

class Counter implements Runnable{
    private int n = 0;

    void t1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (Counter.this){
                    System.out.println("t1");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    n++;
                    System.out.println("t1 end");
                }
            }
        }).start();
    }

    void t2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (Counter.this){
                    System.out.println("t2");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("t2 end");
                }
            }
        }).start();
    }

    void t3(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (Counter.this){
                    System.out.println("t3");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t3 end");
                }
            }
        }).start();
    }

    public void run() {
        t1();
        t2();
        t3();
    }

    public static final void main(String[] argv){
        Counter counter = new Counter();
        Thread thread1 = new Thread(counter, "A");
        thread1.start();
    }
}