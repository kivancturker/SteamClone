package com.kivanc.steamserver.publisher;

import com.kivanc.steamserver.publisher.dtos.PublisherDTO;
import com.kivanc.steamserver.publisher.requests.PublisherRequest;

import java.util.List;

public interface PublisherService {
    void addPublisher(PublisherRequest publisherRequest);
    List<PublisherDTO>  getPublishers();
    PublisherDTO getPublisherById(long id);
    PublisherDTO getPublisherByUsername(String username);

}
