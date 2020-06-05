package com.demo.sharon.service.serviceImpl;

import com.demo.sharon.dao.UserMapper;
import com.demo.sharon.pojo.User;
import com.demo.sharon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    // 表明服务层
public class UserServiceImpl implements UserService {

    @Autowired  // 控制层注入服务层对象
    private UserMapper userMapper;
    public String login(String username, String password) {
        // 可能会做一些复杂的业务逻辑和算法操作
        // 先通过用户名去数据库找用户，如果名字正确再执行密码的判定
        User user = userMapper.selectByUsername(username);
        if(user == null) {
            return "用户名或密码错误";
        } else {
            return "success";
        }
    }

    public String delete(String id) {
        int col = userMapper.deleteByPrimaryKey(id);
        if (col == 0 ) {
            return "failed";
        } else {
            return "success";
        }
    }

    public List<User> getAllUsers(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<User> users = userMapper.selectAll(page, limit);
        return users;
    }

    public Integer getCount() {
        Integer count = userMapper.getCount();
        return count;
    }
}
