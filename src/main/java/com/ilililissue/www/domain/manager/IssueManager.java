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

    @Enumerated
    @Column(name = "role")
    private ManagerRole role;

    public IssueManager(ManagerRole role) {
        this.role = role;
    }

    @Override
    public boolean hasControl() {
        return role.isOverAuthorized(ManagerRole.LV3);
    }

}
