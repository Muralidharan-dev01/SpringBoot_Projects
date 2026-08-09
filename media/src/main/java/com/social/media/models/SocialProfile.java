package com.social.media.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfile {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @OneToOne(mappedBy = "socialProfile")
    @JoinColumn(name = "Social_User") //naming control
    @JsonIgnore //-->used to avoid circular reference---->causing endless loop
    private SocialUser user;



     public void setSocialUser(SocialUser socialUser)
     {
         this.user=socialUser;
         if(user.getSocialProfile()!=this)
         {
             user.setSocialProfile(this);
         }
     }
}
