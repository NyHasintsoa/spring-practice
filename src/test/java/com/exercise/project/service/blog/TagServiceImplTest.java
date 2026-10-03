package com.exercise.project.service.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.repository.blog.TagRepository;
import com.exercise.project.request.blog.TagRequest;
import com.exercise.project.service.blog.tag.TagServiceImpl;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void addTagShouldPersistNewTag() {
        TagRequest request = new TagRequest("spring");
        when(tagRepository.save(any(Tag.class))).thenAnswer(invocation -> {
            Tag tag = invocation.getArgument(0);
            tag.setId(UUID.randomUUID());
            return tag;
        });

        Tag result = tagService.addTag(request);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("spring");
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void updateTagFromIdShouldChangeName() {
        UUID id = UUID.randomUUID();
        Tag existingTag = new Tag();
        existingTag.setId(id);
        existingTag.setName("old");

        when(tagRepository.findById(id)).thenReturn(Optional.of(existingTag));
        when(tagRepository.save(existingTag)).thenReturn(existingTag);

        Tag updated = tagService.updateTagFromId(id.toString(), new TagRequest("new-name"));

        assertThat(updated.getName()).isEqualTo("new-name");
        verify(tagRepository).save(existingTag);
    }

    @Test
    void findByNameShouldReturnTagIfItExists() {
        Tag tag = new Tag();
        tag.setId(UUID.randomUUID());
        tag.setName("java");
        when(tagRepository.findByName("java")).thenReturn(Optional.of(tag));

        Optional<Tag> found = tagService.findByName("java");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("java");
    }
}
