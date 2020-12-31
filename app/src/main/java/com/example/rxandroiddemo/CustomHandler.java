package com.example.rxandroiddemo;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import java.util.concurrent.Semaphore;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CustomHandler extends Handler {

    static HandlerThread handlerThread;
    private static final String TAG = "CustomHandler";

    public CustomHandler(String name) {
        this(name, Process.THREAD_PRIORITY_BACKGROUND);
    }

    protected CustomHandler(String handlerName, int handlerPriority) {
       // startHandlerThread(handlerName);
    }

    public Looper startHandlerThread(String name) {
        final Semaphore semaphore = new Semaphore(0);
        handlerThread = new HandlerThread(name) {
            protected void onLooperPrepared() {
                Log.d(TAG, "onLooperPrepared: "+Thread.currentThread().getName());
                semaphore.release();
            }
        };
        handlerThread.start();
        semaphore.acquireUninterruptibly();
        return handlerThread.getLooper();
    }

    public void quit() {
        handlerThread.getLooper().quit();
    }

    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
