package com.social.media.service;

import com.social.media.models.SocialUser;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers() {

        return socialUserRepository.findAll();
    }

    public SocialUser saveUser(SocialUser socialUser) {

        return socialUserRepository.save(socialUser);
    }

    public SocialUser deleteUser(Long userId) {
        SocialUser findSocialUser= socialUserRepository.findById(userId).
                orElseThrow(()->new RuntimeException("User Not found!"));
         socialUserRepository.delete(findSocialUser);
         return findSocialUser;

    }
}
