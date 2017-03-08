package wenjing.xdtic.action;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.service.ProjectService;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private ProjectService proService;

    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping("login")
    public String getLoginPage(HttpServletRequest request) {
        request.setAttribute("loginFail", Boolean.FALSE);
        return "admin/login";
    }

    @GetMapping("project/check")
    public String getUnchekedProjectsPage() {
        return "admin/project/check";
    }

    @GetMapping("project")
    public ModelAndView getProjectDetailPage(@RequestParam Integer proId) {
        Project project = proService.getProject(proId);
        return new ModelAndView("admin/project/detail", "project", project);
    }

    @GetMapping("project/look")
    public String getAcceptedProjectsPage() {
        return "admin/project/look";
    }

    @GetMapping("user/look")
    public String getUsersPage() {
        return "admin/user/look";
    }
    
}
