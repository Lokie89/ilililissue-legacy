package com.example.ilililissue.domain.like;

import com.example.ilililissue.domain.comment.Comment;
import com.example.ilililissue.domain.member.Member;
import lombok.Builder;

@Builder
public class CommentLike implements Like {
    private Member member;
    private Comment comment;

    @Override
    public int createLike() {
        return 1;
    }
}
