package org.example.clickstream001;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClickstreamService {

    private final Deque<ClickEvent> recentEvents = new ArrayDeque<>();
    private final AtomicLong nextId = new AtomicLong(1);
    private final int maxEvents;

    public ClickstreamService(@Value("${clickstream.max-events:100}") int maxEvents) {
        this.maxEvents = Math.max(1, maxEvents);
    }

    public synchronized ClickEvent store(ClickEventRequest request) {
        ClickEvent event = new ClickEvent(
                nextId.getAndIncrement(),
                Instant.now(),
                safe(request.eventType(), "click"),
                safe(request.elementId(), "unknown"),
                safe(request.page(), "/"),
                request.x(),
                request.y());

        recentEvents.addFirst(event);
        while (recentEvents.size() > maxEvents) {
            recentEvents.removeLast();
        }
        return event;
    }

    public synchronized List<ClickEvent> recent() {
        return new ArrayList<>(recentEvents);
    }

    private String safe(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value;
    }
}

