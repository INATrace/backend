package com.abelium.inatrace.api;

import java.time.Instant;

public class ApiTimestampEntity extends ApiBaseEntity {

    private Instant creationTimestamp;
    private Instant updateTimestamp;

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
