package org.example.clickstream001;

import java.time.Instant;

public record ClickEvent(
        long id,
        Instant timestamp,
        String eventType,
        String elementId,
        String page,
        Integer x,
        Integer y) {
}

