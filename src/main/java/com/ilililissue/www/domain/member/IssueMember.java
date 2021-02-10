package com.ilililissue.www.domain.member;

import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.manager.UnderControl;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Entity
public class IssueMember implements UnderControl {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Override
    public boolean isControlled(ManagerRole role) {
        return role.isOverAuthorized(ManagerRole.MASTER);
    }
}
