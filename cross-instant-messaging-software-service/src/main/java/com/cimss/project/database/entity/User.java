package com.cimss.project.database.entity;

import com.cimss.project.database.entity.token.LoginRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "CIMSS_USER")
public class User  implements Serializable, UserDetails {
	@EmbeddedId
	@EqualsAndHashCode.Include
	private UserId userId;
	@Schema(description = "User name",example = "David")
	@Column
	@EqualsAndHashCode.Exclude
	private String userDisplayName;

	@Schema(description = "System role",example = "NORMAL")
	@Column
	@EqualsAndHashCode.Exclude
	@Enumerated(EnumType.STRING)
	private LoginRole loginRole;
	public static User CreateUser(UserId userId, String userName){
		return new User(userId, userName,LoginRole.NORMAL);
	}
	public static User CreateUser(UserId userId){
		return new User(userId, "",null);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add((new SimpleGrantedAuthority(loginRole.name())));
		return authorities;
	}


	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return userId.toJsonString();
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
}
