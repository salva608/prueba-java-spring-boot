package com.talentboard.talentboard.services;

import com.talentboard.talentboard.models.User;
import com.talentboard.talentboard.dtos.AuthRequest;
import java.util.List;

public interface UserService {
    User register(AuthRequest request);
    User findById(Long id);
    List<User> findAll();
}