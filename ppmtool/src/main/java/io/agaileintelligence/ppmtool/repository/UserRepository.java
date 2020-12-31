package io.agaileintelligence.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.agaileintelligence.ppmtool.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	

    User findByUsername(String username);
    User getById(Long id);

}
