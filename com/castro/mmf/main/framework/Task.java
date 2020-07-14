package com.castro.mmf.main.framework;

import org.osbot.rs07.script.MethodProvider;

public abstract class Task {

    protected MethodProvider api;

    public Task(MethodProvider api) {
        this.api = api;
    }

    public abstract boolean validate();

    public abstract void execute();

    public void run() {
        if (validate())
            execute();
    }
}