package com.springboot.web.login.oauth.user;

import com.springboot.web.login.oauth.social.userconnection.UserConnection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signUp(UserConnection userConnection) {
        final User user = User.signUp(userConnection);
        return userRepository.save(user);
    }

    public User findBySocial(UserConnection userConnection) {
        final User user = userRepository.findBySocial(userConnection);
        if (user == null) throw new RuntimeException();
        return user;
    }

    public boolean isExistUser(UserConnection userConnection) {
        final User user = userRepository.findBySocial(userConnection);
        return (user != null);
    }
}
