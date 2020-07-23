package com.ssafy.sub.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class User implements UserDetails {
	
//	@GeneratedValue: 원하는 키 생성 전략 / IDENTITY: 기본키 생성을 db에 위임
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length =100, nullable = false)
	private String uid;
	
	@Column(length = 100, nullable = false)
	private String upw;
	
	@Column(length = 100, nullable = false, unique = true)
	private String unick;
	
	@Column(length = 100, nullable = false, unique = true)
	private String uemail;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(length = 100)
	private Date uregdate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(length = 100)
	private Date ubirth;
	
	@Column
	private int usex;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return uemail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public String getPassword() {
		return null;
	}
}