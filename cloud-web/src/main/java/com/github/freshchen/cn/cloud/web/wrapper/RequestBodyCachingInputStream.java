package com.github.freshchen.cn.cloud.web.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;

/**
 * @author freshchen
 * @since 2022/7/12
 */
public class RequestBodyCachingInputStream extends ServletInputStream {
    private byte[] body;
    private int lastIndexRetrieved = -1;
    private ReadListener listener;

    public RequestBodyCachingInputStream(byte[] body) {
        this.body = body;
    }

    @Override
    public int read() throws IOException {
        if (isFinished()) {
            return -1;
        }
        int i = body[lastIndexRetrieved + 1];
        lastIndexRetrieved++;
        if (isFinished() && listener != null) {
            try {
                listener.onAllDataRead();
            } catch (IOException e) {
                listener.onError(e);
                throw e;
            }
        }
        return i;
    }

    @Override
    public boolean isFinished() {
        return lastIndexRetrieved == body.length - 1;
    }

    @Override
    public boolean isReady() {
        return isFinished();
    }

    @Override
    public void setReadListener(ReadListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener can not be null");
        }
        if (this.listener != null) {
            throw new IllegalArgumentException("listener has been set");
        }
        this.listener = listener;
        if (!isFinished()) {
            try {
                listener.onAllDataRead();
            } catch (IOException e) {
                listener.onError(e);
            }
        } else {
            try {
                listener.onAllDataRead();
            } catch (IOException e) {
                listener.onError(e);
            }
        }
    }

    @Override
    public int available() throws IOException {
        return body.length - lastIndexRetrieved - 1;
    }

    @Override
    public void close() throws IOException {
        lastIndexRetrieved = body.length - 1;
        body = null;
    }
}