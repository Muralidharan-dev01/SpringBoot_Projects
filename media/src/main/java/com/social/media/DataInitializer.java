package com.social.media;

import com.social.media.models.Post;
import com.social.media.models.SocialGroup;
import com.social.media.models.SocialProfile;
import com.social.media.models.SocialUser;
import com.social.media.repositories.PostRepository;
import com.social.media.repositories.SocialGroupRepository;
import com.social.media.repositories.SocialProfileRepository;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    //@Autowired
    private SocialUserRepository userRepository;
    private SocialGroupRepository groupRepository;
    private SocialProfileRepository profileRepository;
    private PostRepository postRepository;


    //Field Injection.
    public DataInitializer(SocialUserRepository userRepository,SocialGroupRepository groupRepository,SocialProfileRepository profileRepository,
                           PostRepository postRepository)
    {
       this.userRepository=userRepository;
       this.groupRepository=groupRepository;
       this.profileRepository=profileRepository;
       this.postRepository=postRepository;
    }

    @Bean
    public CommandLineRunner initializeData()
    {
      return args -> {

          // Create Users
          SocialUser user1=new SocialUser();
          SocialUser user2=new SocialUser();
          SocialUser user3=new SocialUser();

          //Save users to DB
          userRepository.save(user1);
          userRepository.save(user2);
          userRepository.save(user3);

          //Create Groups
          SocialGroup group1= new SocialGroup();
          SocialGroup group2=new SocialGroup();

          //Add users to group
          group1.getSocialusers().add(user1);
          group1.getSocialusers().add(user2);

          group2.getSocialusers().add(user2);
          group2.getSocialusers().add(user3);

          //Save groups to DB
          groupRepository.save(group1);
          groupRepository.save(group2);


          //Associate groups with Users
          user1.getGroups().add(group1);
          user2.getGroups().add(group1);
          user2.getGroups().add(group2);
          user3.getGroups().add(group2);

          //save user back to DB
          userRepository.save(user1);
          userRepository.save(user2);
          userRepository.save(user3);



          //Create some posts
          Post post1=new Post();
          Post post2=new Post();
          Post post3=new Post();
          Post post4=new Post();

          //Associate Posts with users;
          post1.setSocialUser(user1);
          post2.setSocialUser(user2);
          post3.setSocialUser(user3);
          post4.setSocialUser(user2);

          //Save posts to DB
          postRepository.save(post1);
          postRepository.save(post2);
          postRepository.save(post3);
          postRepository.save(post4);

          // create SocialProfiles,
          SocialProfile profile1=new SocialProfile();
          SocialProfile profile2= new SocialProfile();
          SocialProfile profile3= new SocialProfile();

          //Associate profiles with user
          profile1.setUser(user1);
          profile2.setUser(user2);
          profile3.setUser(user3);

          //Save profiles to DB
          profileRepository.save(profile1);
          profileRepository.save(profile2);
          profileRepository.save(profile3);

          //Fetch Types
          System.out.println("Fetching Social User!");
          userRepository.findById(1L);

      };
    }




}
