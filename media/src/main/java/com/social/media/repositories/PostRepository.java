package com.social.media.repositories;

import com.social.media.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface PostRepository extends JpaRepository<Post,Long> {
}
