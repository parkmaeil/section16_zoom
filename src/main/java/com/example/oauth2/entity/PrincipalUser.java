package com.example.oauth2.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class PrincipalUser extends User implements OAuth2User {
    private Member member;
    public PrincipalUser(Member member){
        super(member.getUsername(), member.getPassword(), getAuthorities(member.getRoles()));
        this.member=member;
    }
   public static Collection<? extends  GrantedAuthority> getAuthorities(Set<Role> roles){
        return roles.stream()
                .map(role-> new SimpleGrantedAuthority("ROLE_"+role.getName()))
                .collect(Collectors.toList());
   }
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
