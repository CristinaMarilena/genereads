package com.example.config.securityConfig;

import com.example.model.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * UserDetailsService: Core interface which loads user-specific data.
 * Has only one method: 'loadUserByUsername'.
 */

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    public MyUserDetailsService() {
        super();
    }

    /**
     * Locates the user based on the username
     * @param email
     * @return Spring Security User object
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            final Account account = accountService.findByEmail(email);
            if (account == null) {
                throw new UsernameNotFoundException("No account found with email: " + email);
            }

            Collection<? extends GrantedAuthority> userAuthorities;
            userAuthorities = getAuthorities();
            return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(),true, true, true, true, userAuthorities);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * If we would want to add different privileges, we should add to the Account
     * model a list of roles.
     * Because the application doesn't require, we added the same privilege for all users.
     * @return user privileges
     */

    private final Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> privileges = new ArrayList<>();
        privileges.add("All privileges");
        return getGrantedAuthorities(privileges);
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}