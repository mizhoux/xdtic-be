package wenjing.xdtic.action;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.core.RemoteAddressCache;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.PagingModel;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.User;
import wenjing.xdtic.service.MessageService;
import wenjing.xdtic.service.ProjectService;
import wenjing.xdtic.service.UserService;

/**
 * api 功能 <br>
 * 功能包括：用户注册、登录、修改个人信息、修改密码，验证用户是否已经存在，获得用户当前的系统消息
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("fn")
public class UserFunction {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService msgService;

    @Autowired
    private ProjectService proService;

    @Autowired
    private RemoteAddressCache addrCache;

    /**
     * 根据用户名和密码进行注册（以 Form 提交）
     *
     * @param username 用户名
     * @param password 密码
     * @param passwordConfirm 确定密码时输入的密码
     * @return
     */
    @PostMapping(value = "user/register", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String register(@RequestParam String username,
            @RequestParam("pass") String password,
            @RequestParam("passConfirm") String passwordConfirm) {

        if (password.equals(passwordConfirm)) {
            if (userService.addUser(username, password)) {
                return "user/login";
            }
        }

        return "user/register";
    }

    /**
     * 根据用户名和密码进行登录（以 Form 提交）
     *
     * @param request
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping(value = "user/login", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String userLogin(HttpServletRequest request,
            @RequestParam String username, @RequestParam String password) {

        Optional<User> user = userService.getUser(username, password);

        user.ifPresent(u -> {
            u.setHasMsg(msgService.countUnreadMessages(u.getId()) > 0);

            request.getSession().setAttribute("user", u);
            addrCache.put("U".concat(request.getRemoteAddr()), u);
        });

        return user.map(u -> "redirect:/user/loginBySession").orElse("user/register");
    }

    /**
     * 修改用户密码
     *
     * @param username 用户名
     * @param passOld 旧密码
     * @param passNew 新密码
     * @param passNewConfirm 第二次输入的新密码
     * @return
     */
    @PostMapping(value = "user/resetPass", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String updateUserPassword(
            @RequestParam String username, @RequestParam String passOld,
            @RequestParam String passNew, @RequestParam String passNewConfirm) {

        if (passNew.equals(passNewConfirm)) {
            if (userService.updatePassword(username, passOld, passNew)) {
                return "user/login";
            }
        }
        return "user/resetPass"; // 更新密码不成功
    }

    /**
     * 修改用户个人信息
     *
     * @param user 提交的个人信息（以 Form 提交）
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping(value = "update/profile", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateUserProfile(HttpSession session, User user) {
        userService.syncDataForBack(user);
        boolean success = userService.updateUser(user);
        if (success) {
            session.setAttribute("user", user);
        }

        return success ? RespCode.OK : RespCode.ERROR;
    }

    /**
     * 验证用户名是否可用（以 JSON 提交）
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "valid/username", consumes = APPLICATION_JSON_VALUE)
    public RespCode validUsername(@RequestBody Map<String, String> params) {

        String username = params.get("username");
        return userService.containsUsername(username) ? RespCode.ERROR : RespCode.OK;
    }

    /**
     * 根据用户名和密码验证用户是否存在（以 JSON 提交）
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "valid/user", consumes = APPLICATION_JSON_VALUE)
    public RespCode validUser(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        return userService.getUser(username, password)
                .map(u -> RespCode.OK).orElse(RespCode.ERROR);
    }

    /**
     * 根据用户名和密码验证用户是否存在（以 Form 提交）
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @ResponseBody
    @PostMapping(value = "valid/user", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode validUser(
            @RequestParam String username, @RequestParam String password) {

        return userService.getUser(username, password)
                .map(u -> RespCode.OK).orElse(RespCode.ERROR);
    }

    /**
     * 根据用户的 id 获取系统消息列表
     *
     * @param userId 用户 id
     * @param pageNum 当前页索引
     * @param size 请求的消息数量
     * @return
     */
    @ResponseBody
    @GetMapping("get/msg")
    public PagingModel<Message> getMessages(
            @RequestParam("uid") Integer userId,
            @RequestParam int pageNum, @RequestParam int size) {

        return msgService.getPagingMessages(userId, pageNum, size);
    }

    @ResponseBody
    @PostMapping("read/msg")
    @SuppressWarnings("unchecked")
    public RespCode readMessage(@RequestBody Map<String, Object> params) {

        List<Integer> msgIds = (List<Integer>) params.get("mid");
        boolean success = msgService.setMessagesRead(msgIds);

        return success ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @PostMapping("user/project/operate")
    public RespCode deleteProject(@RequestBody Map<String, String> params) {
        String operation = params.get("operation");
        if ("delete".equals(operation)) {
            Integer proId = Integer.valueOf(params.get("proId"));
            boolean success = proService.deleteProject(proId);

            if (success) {
                return RespCode.OK;
            }
        }

        return RespCode.ERROR;
    }

}