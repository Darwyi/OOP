package org.Darwyi.courseProject.storage;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class SystemMemento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final StoreState state;
    private final LocalDateTime createdAt;

    SystemMemento(StoreState state){
        this.state = state;
        this.createdAt = LocalDateTime.now();
    }

    StoreState getState(){ return state; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
}
