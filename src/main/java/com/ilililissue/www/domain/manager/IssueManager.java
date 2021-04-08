package com.ilililissue.www.domain.manager;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class IssueManager {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated
    @Column(name = "role")
    private ManagerRole role;

    public IssueManager(ManagerRole role) {
        this.role = role;
    }

    public boolean hasControl(UnderControl underControl) {
        return underControl.isControlled(role);
    }
}
