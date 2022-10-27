package com.kivanc.steamserver.publisher;

import com.kivanc.steamserver.publisher.dtos.PublisherDTO;
import com.kivanc.steamserver.publisher.requests.PublisherRequest;

public interface PublisherService {
    void addPublisher(PublisherRequest publisherRequest);
    PublisherDTO getPublisherById(long id);
    PublisherDTO getPublisherByUsername(String username);

}
