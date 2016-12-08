package edu.iss.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.iss.caps.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("SELECT u FROM User u WHERE u.userId=:uid AND u.password=:pwd")
	User findUserByNamePwd(@Param("uid") String uid, @Param("pwd") String pwd);
}
