package io.d1av.blog.service.impl;

import io.d1av.blog.entity.Post;
import io.d1av.blog.exception.ResourceNotFoundException;
import io.d1av.blog.payload.PostDto;
import io.d1av.blog.repository.PostRepository;
import io.d1av.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return new PostDto(repository.save(post));
    }

    @Override
    public Page<PostDto> getAll(Pageable pageable) {
        Page<Post> result = repository.findAll(pageable);
        return result.map(PostDto::new);
    }

    @Override
    public PostDto getOnePostById(Long id) {
        return new PostDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
    }

    @Override
    public PostDto updateOneById(Long id, PostDto postDto) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return new PostDto(repository.save(post));
    }

    @Override
    public void deleteOneById(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        repository.delete(post);
    }
}
