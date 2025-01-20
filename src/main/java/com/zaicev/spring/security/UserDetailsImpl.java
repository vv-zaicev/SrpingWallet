package com.zaicev.spring.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zaicev.spring.security.models.User;
import com.zaicev.spring.wallet.models.Wallet;

public class UserDetailsImpl implements UserDetails {

	private User user;

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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
		return user.isEnabled();
	}

	public List<Wallet> getWallets() {
		return user.getWallets();
	}

	public void addWallet(Wallet wallet) {
		user.addWallet(wallet);
	}

	public void removeWallet(Wallet wallet) {
		user.removeWallet(wallet);
	}

	public User getUser() {
		return user;
	}

}
