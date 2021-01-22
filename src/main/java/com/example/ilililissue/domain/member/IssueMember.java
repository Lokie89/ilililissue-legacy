package com.example.ilililissue.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class IssueMember implements Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
}
