package com.wind.auth.utils;

/**
 * @author qianchun 2019/04/16
 */
public class ThreadStateTest {

    private void NEW() {
        Thread t = new Thread();
        System.out.println("Test NEW, Thread.state=" + t.getState());
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void RUNNABLE() {
        Thread t = new Thread() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("Test RUNNABLE, i=" + i);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();
//        sleep(5000);
        System.out.println("Test RUNNABLE, Thread.state=" + t.getState());

    }

    private void BLOCKED() {

    }

    private void WAITING() {

    }

    private void TIMED_WAITING() {

    }

    private void TERMINATED() {

    }

    public static void main(String[] args) {
        ThreadStateTest test = new ThreadStateTest();

        test.NEW();
        test.RUNNABLE();
        // test.BLOCKED();
        // test.WAITING();
        // test.TIMED_WAITING();
        // test.TERMINATED();
    }

}
