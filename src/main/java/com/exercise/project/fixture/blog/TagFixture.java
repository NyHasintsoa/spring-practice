package com.exercise.project.fixture.blog;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exercise.project.fixture.BaseFixture;
import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.repository.blog.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagFixture extends BaseFixture<Tag> {

    private final TagRepository tagRepository;

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
                "\n##############################################\n"
            );
        }
    }

}
