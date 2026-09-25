package org.example.clickstream001;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class ClickstreamController {

    private final ClickstreamService clickstreamService;

    public ClickstreamController(ClickstreamService clickstreamService) {
        this.clickstreamService = clickstreamService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClickEvent track(@RequestBody(required = false) ClickEventRequest request) {
        ClickEventRequest safeRequest = request == null
                ? new ClickEventRequest("click", "unknown", "/", null, null)
                : request;
        return clickstreamService.store(safeRequest);
    }

    @GetMapping("/recent")
    public List<ClickEvent> recent() {
        return clickstreamService.recent();
    }
}

