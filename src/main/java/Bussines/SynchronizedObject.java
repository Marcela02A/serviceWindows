/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussines;

/**
 *
 * @author Marcela
 */
public class SynchronizedObject {

    private boolean state = false;

    public synchronized void isChangeState() throws InterruptedException {
        if (state) {
            wait();
        }
    }

    public synchronized void waitChange() throws InterruptedException {
        wait();
    }

    public synchronized void setState(boolean value) {
        state = value;
        if (value) {
            notifyAll();
        }
    }

    public synchronized void freeState() {
        state = false;
        notifyAll();
    }
}
