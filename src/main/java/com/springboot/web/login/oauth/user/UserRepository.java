package com.springboot.web.login.oauth.user;


import com.springboot.web.login.oauth.social.userconnection.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findBySocial(UserConnection userConnection);

    User findByEmail(String email);

    List<User> findByEmailLike(String str);
}
