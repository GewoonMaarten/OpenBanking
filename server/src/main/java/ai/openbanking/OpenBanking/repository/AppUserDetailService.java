package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailService implements UserDetailsService {

    private final UserRepository repository;

    AppUserDetailService(UserRepository repository) { this.repository = repository; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
