package com.github.fashionbrot.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author fashionbrot
 * @date 2021/07/28 22:45
 */
public class MarsListenerEvent extends ApplicationEvent {

    private static final long serialVersionUID = 975253233625382817L;

    private String fileName;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MarsListenerEvent(Object source) {
        super(source);
    }

    public MarsListenerEvent(Object source, String fileName) {
        super(source);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
