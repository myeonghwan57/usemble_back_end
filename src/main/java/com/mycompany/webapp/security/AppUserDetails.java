package com.mycompany.webapp.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mycompany.webapp.dto.Member;

public class AppUserDetails extends User {
	private Member member;

	public AppUserDetails(Member member, List<GrantedAuthority> authority) {
		super(member.getMid(), member.getMpassword(), member.isMenabled(), true, true, true, authority);
		this.member = member;
	}

	public Member getMember() {
		return member;
	}
}
