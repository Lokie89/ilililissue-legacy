package com.ilililissue.www.domain.member;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Entity
public class IssueMember implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

}
