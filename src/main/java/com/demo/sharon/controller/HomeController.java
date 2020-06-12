package com.demo.sharon.controller;

import cn.dsna.util.images.ValidateCode;
import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.User;
import com.demo.sharon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller // 被Spring扫描 表明这个类表示控制层
@RequestMapping("/home")    // 请求地址路径映射
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        // 可以判断登陆前的先决条件
        User user = (User)request.getSession().getAttribute("user");
        if (user != null) {
            request.getSession().setMaxInactiveInterval(24*60*60);
            return "index";
        } else
            return "login";
    }

    @RequestMapping("/main")
    public String main() {
        // 可以判断登陆前的先决条件
        return "index";
    }

    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成验证码
        ValidateCode validateCode = new ValidateCode(160, 40, 4, 34);
        // 获取验证码中的验证信息 后面登陆的时候需要在登陆的服务层验证输入的验证码是否正确
        String code = validateCode.getCode();
        // 将code添加到session中
        request.getSession().setAttribute("code", code);
        // 给session设置时间 单位：s 永不过期：-1
        request.getSession().setMaxInactiveInterval(300);
        // 通过输出流的方式将验证码图片返回给前端
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login")
    public Result login(String username, String password, String code, HttpServletRequest request) {
        // 可以判断登陆前的先决条件
            return userService.login(username, password, code, request);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }

}
