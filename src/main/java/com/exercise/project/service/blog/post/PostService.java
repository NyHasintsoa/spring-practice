package com.exercise.project.service.blog.post;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exercise.project.model.dto.blog.PostDto;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.entity.blog.Comment;
import com.exercise.project.model.entity.blog.Post;
import com.exercise.project.model.entity.blog.PostLike;
import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.exception.ResourceNotFoundException;
import com.exercise.project.repository.blog.CommentRepository;
import com.exercise.project.repository.blog.PostLikeRepository;
import com.exercise.project.repository.blog.PostRepository;
import com.exercise.project.request.blog.CommentRequest;
import com.exercise.project.request.blog.PostRequest;
import com.exercise.project.response.blog.LikePostResponse;
import com.exercise.project.response.blog.PostCommentResponse;
import com.exercise.project.security.user.AuthUserDetails;
import com.exercise.project.service.BaseService;
import com.exercise.project.service.blog.tag.TagServiceInterface;
import com.exercise.project.service.user.UserServiceInterface;
import com.exercise.project.util.ImageFileUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PostService extends BaseService<Post> implements PostServiceInterface {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private TagServiceInterface tagService;

    @Value("${project.blog.image.upload.path}")
    private String UPLOAD_DIR;

    @Override
    public Post submitNewPost(PostRequest request) {
        Post post = new Post();

        Date today = new Date();
        post.setTitle(request.getTitle());
        post.setAuthor(userService.getByEmail(getConnectedUser().getEmail()));
        post.setContent(request.getContent());
        post.setPublishedAt(today);
        post.setUpdatedAt(today);

        post.setSlug(slugify(request.getTitle()));
        post.setTags(tags(request.getTags()));
        post.setHeaderImagePath(ImageFileUtil.storeImage(request.getImageFile(), UPLOAD_DIR));

        return persistPost(post);
    }

    @Override
    @Transactional
    public Comment submitCommentByPostId(String postId, CommentRequest request) {
        Post post = postRepository.findById(UUID.fromString(postId))
            .orElseThrow(() -> new ResourceNotFoundException("Post Not found with ID " + postId));
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(post);
        comment.setPublishedAt(new Date());
        comment.setAuthor(getUser());
        comment.setId(UUID.randomUUID());

        return commentRepository.save(comment);
    }

    @Override
    public LikePostResponse likePostByPostId(String postId, HttpServletRequest request) {
        Post post = postRepository.findById(UUID.fromString(postId))
            .orElseThrow(() -> new ResourceNotFoundException("Post Not found with ID " + postId));

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndAuthor(post, getUser());

        Boolean isLiked = true;

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            isLiked = false;
        } else {
            PostLike newLike = new PostLike();
            newLike.setId(UUID.randomUUID());
            newLike.setPost(post);
            newLike.setAuthor(getUser());
            newLike.setIpAddress(request.getRemoteHost());
            newLike.setLikedAt(new Date());
            postLikeRepository.save(newLike);
        }

        return new LikePostResponse(
            isLiked,
            postId,
            postLikeRepository.countByPost(post));
    }

    private Collection<Tag> tags(Set<String> tagStrings) {
        Collection<Tag> tags = new HashSet<Tag>();

        for (String tagString : tagStrings) {
            Tag tag = tagService.findByName(tagString).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setName(tagString);

                return tagService.persistTag(newTag);
            });
            tags.add(tag);
        }

        return tags;
    }

    @Override
    public Post persistPost(Post post) {
        post.setId(UUID.randomUUID());

        return postRepository.save(post);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> getPaginatedPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public List<Post> getOwnPosts() {
        return postRepository.findByAuthor(getUser());
    }

    @Override
    public Page<Comment> getPaginatedCommentFromPost(Post post, Pageable pageable) {
        return commentRepository.findByPost(post, pageable);
    }

    @Override
    public Post getById(String id) {
        return postRepository.findById(UUID.fromString(id)).orElseThrow(
            () -> new ResourceNotFoundException("tag not found with this id : " + id));
    }

    @Override
    public Post getBySlug(String slug) {
        return postRepository.findBySlug(slug).orElseThrow(
            () -> new ResourceNotFoundException("tag not found with this slug : " + slug));
    }

    @Override
    public PostDto convertToDto(Post data) {
        data.setHeaderImagePath(getHeaderImagePath(data.getHeaderImagePath()));

        return new PostDto(data);
    }

    @Override
    public PostCommentResponse convertToResponse(Post post) {
        post.setHeaderImagePath(getHeaderImagePath(post.getHeaderImagePath()));

        return new PostCommentResponse(
            post,
            commentRepository.countByPost(post));
    }

    @Override
    public List<PostCommentResponse> convertAllToResponse(List<Post> posts) {
        return posts.stream().map(this::convertToResponse).toList();
    }

    private String getHeaderImagePath(String imagePath) {
        return ImageFileUtil.getImageUrl(imagePath, UPLOAD_DIR);
    }

    private User getUser() {
        AuthUserDetails user = getConnectedUser();

        return userService.getByEmail(user.getEmail());
    }

}
