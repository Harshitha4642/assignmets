package com.prodapt.learningspring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.prodapt.learningspring.entity.User;

public interface RegisteredUsersRepository extends CrudRepository<User, Integer> {
	@Query(value="select count(*) from registered_user where username=?1 && password=?2", nativeQuery = true)
	public int checkRegisteredUserOrNot(String username, String password);

}
