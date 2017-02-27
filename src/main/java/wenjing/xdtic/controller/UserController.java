package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.User;

/**
 * 用户页面的路由
 *
 * @author wenjing
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("")
    public String getUserCenterPage(
            @RequestParam Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/center";
    }

    @GetMapping("profile")
    public String getUserProfilePage(
            @RequestParam Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/profile";
    }

    @GetMapping("{pageName}")
    public String route(@PathVariable String pageName) {
        return "page/user/" + pageName;
    }

    /**
     * 测试代码，用户个人信息查询
     *
     * @param id 用户 ID
     * @return
     */
    @ResponseBody
    @GetMapping("info/{id}")
    public User getPesonalInfoById(@PathVariable("id") Integer id) {
        User user = userDao.getUser(id);
        return user;
    }

}
