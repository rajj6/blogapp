package com.raj.service;

import com.raj.model.Tag;
import com.raj.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

public class TagServiceImpl implements TagService{

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public void saveTag(Tag tag) {
        tag.setCreatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        tag.setUpdatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        tagRepository.save(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        tag.setUpdatedAt(new Timestamp(ZonedDateTime.now().toInstant().toEpochMilli()));
        tagRepository.save(tag);
    }

    @Override
    public void deleteTagById(long id) {
        tagRepository.deleteById(id);
    }
}
