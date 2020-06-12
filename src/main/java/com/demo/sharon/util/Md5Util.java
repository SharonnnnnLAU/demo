package com.demo.sharon.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5Util {
    public static String encryption(String username, String password) {
//    public static void main(String[] args) {
//        String username = "fcc";
//        String password = "hmh";

        Md5Hash md5Hash = new Md5Hash(password, username);
        String s = md5Hash.toString();
        System.out.println(s);
        return s;
    }
}
