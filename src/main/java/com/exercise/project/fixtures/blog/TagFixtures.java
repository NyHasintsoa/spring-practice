package com.exercise.project.fixtures.blog;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.blog.Tag;
import com.exercise.project.fixtures.BaseFixtures;
import com.exercise.project.repository.blog.TagRepository;

@Service
public class TagFixtures extends BaseFixtures<Tag> {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag create() {
        Tag tag = new Tag();
        tag.setId(UUID.randomUUID());
        tag.setName(faker().book().genre());

        return tag;
    }

    @Override
    public void store(Tag data) {
        try {
            tagRepository.save(data);
        } catch (Exception e) {
            System.out.println(
                "\n##############################################\n" +
                    "TAGS \n" +
                    "\n##############################################\n" +
                    e.getMessage() +
                    "\n##############################################\n");
        }
    }

}
