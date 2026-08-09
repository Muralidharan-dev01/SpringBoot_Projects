package com.social.media.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.media.repositories.SocialUserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
//    @Version
//    private Long version;

    @OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}`) //mappedBy = "user",
   // @OneToOne( cascade =CascadeType.ALL)
    @JoinColumn(name = "Social_Profile_Id")
    //@JsonIgnore //used to avoid circular reference---->causing endless loop
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser")
    private List<Post> posts=new ArrayList<>();

    @ManyToMany //(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Group",
            joinColumns = @JoinColumn(name = "User_Id"),
            inverseJoinColumns = @JoinColumn(name = "Group_Id")
    )

    private Set<SocialGroup> groups=new HashSet<>();

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }


    public void setSocialProfile(SocialProfile socialProfile)
    {
         //setting the given profile to this current user.
        socialProfile.setUser(this);
        this.socialProfile=socialProfile;



    }
}
