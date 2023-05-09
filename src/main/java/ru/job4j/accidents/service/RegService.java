package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@ThreadSafe
@AllArgsConstructor
@Log4j
public class RegService {
    private final UserRepository users;
    private final AuthorityRepository authorities;
    private final PasswordEncoder encoder;


    public Optional<User> save(User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            log.error("Save user error ", e);
        }
        return Optional.empty();
   }
}
