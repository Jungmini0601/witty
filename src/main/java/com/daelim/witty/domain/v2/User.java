package com.daelim.witty.domain.v2;

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
    private String password;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private User userFollowing = this;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private User userFollower = this;

    @JsonIgnore
    @OneToMany(mappedBy = "userFollowing")
    private List<User> followingList = new ArrayList<User>();

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

}