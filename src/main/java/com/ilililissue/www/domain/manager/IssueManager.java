package com.ilililissue.www.domain.manager;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class IssueManager implements Manager {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "role")
    private String role;

    public IssueManager(String role) {
        this.role = role;
    }
}
