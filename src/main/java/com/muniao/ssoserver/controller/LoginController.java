package com.muniao.ssoserver.controller;

import com.muniao.ssoserver.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController
{
    @Autowired
    private RedisService redisService;

    /**
     * 跳转登录页面
     */
    @RequestMapping("/sso/loginPage")
    private String loginPage(String url, Map<String, Object> map)
    {
        map.put("url", url);

        return "/login";
    }

    /**
     * 跳转登录页面
     */
    @RequestMapping("/test")
    private String test()
    {

        return "/test";
    }

    /**
     * 页面登录
     */
    @RequestMapping("/sso/login")
    private String login(HttpServletResponse response, String username, String password, String url, Map<String, Object> map)
    {
        String check = redisService.checkUsernameAndPassword(username, password);
        if (!StringUtils.isEmpty(check))
        {

            Cookie cookie = new Cookie("accessToken", check);
            cookie.setMaxAge(60 * 3);
            //设置域
            //                cookie.setDomain("huanzi.cn");
            //设置访问路径
            cookie.setPath("/");
            response.addCookie(cookie);

           /* try
            {
                url = URLEncoder.encode(url, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }*/
            return "redirect:" + url;
        }
        map.put("url", url);
        map.put("msg", "登录失败");
        return "/login";
    }

}
