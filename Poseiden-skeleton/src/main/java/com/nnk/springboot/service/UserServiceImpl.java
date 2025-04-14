package com.nnk.springboot.service;

import  com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudService<User> {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }


}
