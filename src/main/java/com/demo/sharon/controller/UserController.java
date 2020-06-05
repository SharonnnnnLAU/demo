package com.demo.sharon.controller;

import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.User;
import com.demo.sharon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller // 被Spring扫描 表明这个类表示控制层
@RequestMapping("/users")    // 请求地址路径映射
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/getAllUsers")
    public Result getAllUsers(Integer page, Integer limit) {
        Result result = new Result();
        List<User> users = userService.getAllUsers(page,limit);
        Integer count = userService.getCount();
        result.setCount(count);
        result.setData(users);
        return result;
    }

}