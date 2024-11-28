package com.example.oauth2.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true, nullable = false)
    private String username; // google_101926511570168785716
    private String password;  // 임의값?(암호화)
    private String uname; //   박매일
    private String email; //  bmy19751975@gmail.com
    // oAuth2에서 추가 되는 정보
    private String provider; // google, naver, kakao
    private String providerId;// sub=101926511570168785716
    @CreationTimestamp
    private Timestamp createDate;

    // 권한정보(USER, MANAGER, ADMIN) : Role
    @ManyToMany
    @JoinTable(
            name="member_roles",
            joinColumns = @JoinColumn(name="member_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;
}
