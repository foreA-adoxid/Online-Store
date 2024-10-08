package ru.org.backend.Services;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.org.backend.Repositories.UserRepositories;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.Role;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepositories userRepository;

    public void save(MyUser user)
        throws ConstraintViolationException, DataIntegrityViolationException, UnexpectedTypeException {
        userRepository.save(user);
    }

    public MyUser getByLogin(String login) {
        return userRepository
            .findByLogin(login)
            .orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден")
            );
    }

    public MyUser getByEmail(String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден")
            );
    }

    public MyUser getCurrentUser() {
        var username = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        return getByLogin(username);
    }

    public ResponseEntity<MyUser> getResponseCurrentUser() {
        return ResponseEntity.ok(getCurrentUser());
    }

    public ResponseEntity<Map<String, Integer>> findUserId() {
        MyUser myUser = getCurrentUser();

        return ResponseEntity.ok(Map.of("Id", myUser.getId()));
    }
}
