package com.stockspring.repository;

import com.stockspring.model.User;
import org.springframework.data.repository.CrudRepository;

//Get all CRUD functions
public interface UserRepository extends CrudRepository<User, Long>{

}
