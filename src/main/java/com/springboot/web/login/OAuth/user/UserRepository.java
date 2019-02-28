package com.springboot.web.login.OAuth.user;


import com.springboot.web.login.OAuth.social.userconnection.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findBySocial(UserConnection userConnection);

}
