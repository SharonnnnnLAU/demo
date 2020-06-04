package com.demo.sharon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 被Spring扫描 表明这个类表示控制层
@RequestMapping("/home")    // 请求地址路径映射
public class HomeController {

}
