package com.cimss.project.database.entity;

import com.cimss.project.database.entity.token.LoginRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "CIMSS_USER")
public class User  implements Serializable, UserDetails {
	@EmbeddedId
	private UserId userId;
	@Schema(description = "User name",example = "David")
	@Column
	private String userName;
	@Schema(description = "System role",example = "NORMAL")
	@Column
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
	public String getUserName() {
		return userName;
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
