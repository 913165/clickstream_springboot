package org.example.clickstream001;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Clickstream001ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void shouldStoreEventsInNewestFirstOrder() {
        ClickstreamService service = new ClickstreamService(3);
        service.store(new ClickEventRequest("click", "first", "/", 1, 1));
        service.store(new ClickEventRequest("click", "second", "/", 2, 2));

        assertEquals(2, service.recent().size());
        assertEquals("second", service.recent().getFirst().elementId());
    }

}
