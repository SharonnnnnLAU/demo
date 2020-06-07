package com.demo.sharon.service;

import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.User;

import java.util.List;

public interface UserService {

    String login(String username, String password);

    String delete(String id);

    List<User> getAllUsers(Integer page, Integer limit);

    Integer getCount();

    Result del(String id);

    Result delete(String[] ids);
}
