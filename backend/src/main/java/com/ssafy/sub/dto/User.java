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
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class User implements UserDetails {
	
//	@GeneratedValue: 원하는 키 생성 전략 / IDENTITY: 기본키 생성을 db에 위임
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
//	@JsonProperty: 데이터 어떤식으로 접근할지 / Access.WRITE_ONLY 읽어올 때 제외됨
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length =100, nullable = false)
	private String uid;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(length = 100, nullable = false)
	private String upw;
	
	@Column(length = 100, nullable = false, unique = true)
	private String unick;
	
	@Column(length = 100, nullable = false, unique = true)
	private String uemail;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(length = 100)
	private Date uregdate;
	
	@Temporal(TemporalType.DATE)
	@Column(length = 100)
	private Date ubirth;
	
	@Column
	private int usex;
	
	@Transient
	private DBProfile DBProfile;
		
	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> roles = new ArrayList<>();
	
	@Override
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnabled() {
        return true;
    }

	@Override
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String getPassword() {
		return null;
	}
}