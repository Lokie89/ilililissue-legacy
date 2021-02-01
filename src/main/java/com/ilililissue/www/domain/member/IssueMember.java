package com.ilililissue.www.domain.member;

import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.manager.UnderControl;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class IssueMember implements UnderControl {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    public IssueMember(String name) {
        this.name = name;
    }

    @Override
    public boolean isControlled(ManagerRole role) {
        return role.isOverAuthorized(ManagerRole.MASTER);
    }
}
