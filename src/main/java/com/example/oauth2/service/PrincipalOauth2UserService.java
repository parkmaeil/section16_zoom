package com.example.oauth2.service;

import com.example.oauth2.entity.Member;
import com.example.oauth2.entity.PrincipalUser;
import com.example.oauth2.entity.Role;
import com.example.oauth2.repository.MemberRepository;
import com.example.oauth2.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PrincipalOauth2UserService  extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional // O
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("userRequest" + userRequest); // ?
        //userRequestorg.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@355aaf3d
        System.out.println(userRequest.getClientRegistration()); // google~~~
        System.out.println(userRequest.getAccessToken().getTokenValue());
        System.out.println(super.loadUser(userRequest).getAttributes()); //----엑세스토큰 -->요청---email, profile--->
        // 강제로 회원가입을 진행하기 위한 정보 추출
        OAuth2User oAuth2User=super.loadUser(userRequest);
        String provider=userRequest.getClientRegistration().getRegistrationId(); // google
        String providerId=oAuth2User.getAttribute("sub"); //  101926511570168785716
        String username=provider+"_"+providerId;
        // 데이터베이스에 데이터를 저장하기 전에 ? 가입유무를 확인 ?
        Optional<Member>  optional=memberRepository.findByUsername(username);
        Member member=null;
        if(optional.isPresent()){
            System.out.println("로그인을 이미 한적이 있습니다. 당신은 자동회원 가입이 되었습니다");
            member=optional.get();
        }else{
            System.out.println("처음 OAuth2 로그인을한 사용자 입니다");
            String password=passwordEncoder.encode("임의의값");
            String uname=(String)oAuth2User.getAttribute("name");
            String email=(String)oAuth2User.getAttribute("email");
            Set<Role> roles=new HashSet<>(); // USER
            Role userRole=roleRepository.findByName("USER");
            roles.add(userRole);
            member=new Member();
            member.setUsername(username);
            member.setPassword(password);
            member.setUname(uname);
            member.setProvider(provider);
            member.setProviderId(providerId);
            member.setEmail(email);
            member.setRoles(roles);
            memberRepository.save(member); // 회원가입
        }
        // 로그인 성공 -> 세션(SecurityContextHolder)<---- Member member
                                       // OAuth2User(interface)
        return new PrincipalUser(member); // 세션으로 만들어진다.
        //return member; // UserDetails-->User(class)
    }
}
