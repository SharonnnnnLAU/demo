package com.demo.sharon.service.serviceImpl;

import com.demo.sharon.dao.UserMapper;
import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.User;
import com.demo.sharon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service    // 表明服务层
public class UserServiceImpl implements UserService {

    @Autowired  // 控制层注入服务层对象
    private UserMapper userMapper;

    public String login(String username, String password) {
        // 可能会做一些复杂的业务逻辑和算法操作
        // 先通过用户名去数据库找用户，如果名字正确再执行密码的判定
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return "用户名或密码错误";
        } else {
            return "success";
        }
    }

    public String delete(String id) {
        int col = userMapper.deleteByPrimaryKey(id);
        if (col == 0) {
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

    public Result del(String id) {
        Result result = new Result();
        // 删除前先判断数据是否存在
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            int del = userMapper.deleteByPrimaryKey(id);
            result.setMsg("Delete success.");
        } else {
            result.setCode(500);
            result.setMsg("System is busy, plz try it later.....");
        }
        return result;
    }

    @Transactional  // 添加事务
    // SQL事务：做某件事的时候，要么全部成功，要么全部失败
    public Result delete(String[] ids) {
        // iter：增强for循环
        // fori：普通循环
        Result result = new Result();
        for (int i = 0; i < ids.length; i++) {
            userMapper.deleteByPrimaryKey(ids[i]);
        }
        return result;
    }

    public Result selectNameByLike(String value, Integer page, Integer limit) {
        Result result = new Result();
        page = (page - 1) * limit;
        List<User> users = userMapper.selectNameByLike(value, page, limit);
//        List<User> list = userMapper.selectNameByLike(value);
//        result.setCount(list.size());
        result.setData(users);
        return result;
    }

    public Result login(String username, String password, String code, HttpServletRequest request) {
        Result result = new Result();
        // 获取session中的code
        String code1 = (String) request.getSession().getAttribute("code");
        //  判断验证码是否正确
        if (code1.equalsIgnoreCase(code)) {
            User user = userMapper.selectByUsername(username);
//            String s = Md5Util.encryption(username, password);
//            String s = password;
            if (user != null && password.equals(user.getPassword())) {
                request.getSession().setAttribute("user", user);
                request.getSession().setMaxInactiveInterval(24 * 60 * 60);
                return result;

            } else {
                result.setCode(500);
                result.setMsg("用户名或密码错误");
                return result;
            }
        } else {
            result.setCode(500);
            result.setMsg("验证码错误");
            return result;

        }
    }
}
