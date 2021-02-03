package com.raj.service;

import com.raj.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();

    Tag getTagById(long id);

    void saveTag(Tag tag);

    void updateTag(Tag tag);

    void deleteTagById(long id);
}
