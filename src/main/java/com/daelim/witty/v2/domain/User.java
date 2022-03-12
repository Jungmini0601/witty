package com.daelim.witty.v2.domain;

import com.daelim.witty.v2.web.controller.dto.users.UserSignUpDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// TODO JSONIGNORE 제거 어디서 해야 하는지 조사 필요.

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Getter
public class User {

    @Id @Column(name = "user_id")
    private String id;
    private String email;
    private String department; // 학부
    @JsonIgnore
    private String password;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private User userFollowing = this;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private User userFollower = this;

    // TODO fetch 타입 이거 어떻게 해야 할지 고민 필요함.
    @JsonIgnore
    @OneToMany(mappedBy = "userFollowing")
    private List<User> followingList = new ArrayList<User>();

    // TODO fetch 타입 이거 어떻게 해야 할지 고민 필요함.
    @JsonIgnore
    @OneToMany(mappedBy = "userFollower")
    private List<User> followerList = new ArrayList<User>();

    /*연관관계 메서드 */
    public void addFollowing(User following) {
        this.followingList.add(following);

        if(!following.getFollowerList().contains(this)) {
            following.getFollowerList().add(this);
        }
        //연관관계의 주인을 통한 확인
        if(!following.getUserFollower().getFollowerList().contains(this)) {
            following.getUserFollower().getFollowerList().add(this);
        }
    }

    public void addFollower(User follower) {
        this.followerList.add(follower);

        if(follower.getFollowingList().contains(this)) {
            follower.getFollowingList().add(this);
        }
        //연관관계의 주인을 통한 확인
        if(!follower.getUserFollowing().getFollowingList().contains(this)) {
            follower.getUserFollowing().getFollowingList().add(this);
        }
    }

    /*생성 메서드*/
    public static User createUserByDTO(UserSignUpDTO userSignUpDTO) {
        User user = new User();
        user.id = userSignUpDTO.getUser_id();
        user.email = userSignUpDTO.getUser_email();
        user.department = userSignUpDTO.getUser_department();
        user.password = userSignUpDTO.getPassword();

        return user;
    }
}
