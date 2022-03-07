package com.hotnerds.user.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User followed;

    @Builder
    public Follow(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }

}
