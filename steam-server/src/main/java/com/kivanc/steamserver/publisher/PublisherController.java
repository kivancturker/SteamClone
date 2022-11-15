package com.kivanc.steamserver.publisher;

import com.kivanc.steamserver.publisher.dtos.PublisherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getPublishers() {
        List<PublisherDTO> publisherDTOS = publisherService.getPublishers();
        return new ResponseEntity<>(publisherDTOS, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisher(@PathVariable(name = "id") long id) {
        PublisherDTO publisherDTO = publisherService.getPublisherById(id);
        return new ResponseEntity<>(publisherDTO, HttpStatus.OK);
    }
}
