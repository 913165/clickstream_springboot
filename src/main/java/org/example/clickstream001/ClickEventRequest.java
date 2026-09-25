package org.example.clickstream001;

public record ClickEventRequest(
        String eventType,
        String elementId,
        String page,
        Integer x,
        Integer y) {
}

