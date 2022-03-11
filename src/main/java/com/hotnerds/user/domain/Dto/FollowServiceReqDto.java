package com.hotnerds.user.domain.Dto;

import com.hotnerds.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowServiceReqDto {
    private Long followerId;
    private Long followedId;

    @Builder
    public FollowServiceReqDto(Long followerId, Long followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }
}
