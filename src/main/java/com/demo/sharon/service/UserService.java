package com.demo.sharon.service;

import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    String login(String username, String password);

    String delete(String id);

    List<User> getAllUsers(Integer page, Integer limit);

    Integer getCount();

    Result del(String id);

    Result delete(String[] ids);

    Result selectNameByLike(String value, Integer page, Integer limit);

    Result login(String username, String password, String code, HttpServletRequest request);
}
