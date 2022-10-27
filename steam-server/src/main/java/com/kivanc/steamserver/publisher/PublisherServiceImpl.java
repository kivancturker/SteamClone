package com.kivanc.steamserver.publisher;

import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.publisher.dtos.PublisherDTO;
import com.kivanc.steamserver.publisher.requests.PublisherRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService{

    private final ModelMapper mapper;
    private final PublisherDao publisherDao;

    @Autowired
    public PublisherServiceImpl(ModelMapper mapper, PublisherDao publisherDao) {
        this.mapper = mapper;
        this.publisherDao = publisherDao;
    }

    @Override
    public void addPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = mapper.map(publisherRequest, Publisher.class);
        if (publisherDao.existsPublisherByEmail(publisher.getEmail()) ||
                publisherDao.existsPublisherByUsername(publisher.getUsername()))
        {
            throw new RecordAlreadyExistException("Publisher Already Exist");
        }
        publisherDao.save(publisher);
    }

    @Override
    public PublisherDTO getPublisherById(long id) {
        Optional<Publisher> maybePublisher = publisherDao.findById(id);
        if (maybePublisher.isEmpty())
        {
            throw new RecordNotFoundException("Publisher not found with given id");
        }
        PublisherDTO publisherDTO = mapper.map(maybePublisher.get(), PublisherDTO.class);
        return publisherDTO;
    }

    @Override
    public PublisherDTO getPublisherByUsername(String username) {
        Optional<Publisher> maybePublisher = publisherDao.getPublisherByUsername(username);
        if (maybePublisher.isEmpty())
        {
            throw new RecordNotFoundException("Publisher not found by username: " + username);
        }
        PublisherDTO publisherDTO = mapper.map(maybePublisher.get(), PublisherDTO.class);
        return publisherDTO;
    }
}
