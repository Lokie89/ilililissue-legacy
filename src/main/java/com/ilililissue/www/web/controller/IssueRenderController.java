package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class IssueRenderController {

    private final SimpleIssueService issueService;
    private final IssueManagerService issueManagerService;
    private final SimpleIssueService simpleIssueService;

    @GetMapping("/index")
    public ModelAndView index() {

        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.MASTER));
        String[] images = new String[]{"/issueimage/simpson.jpg", "/issueimage/simpson.jpg"};
        SimpleIssue issue = SimpleIssue.builder().creator(master).title("이슈 제목").images(images).build();
        simpleIssueService.create(issue);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dayissue");
        mav.addObject("headerTitle", "오늘의 이슈");
        mav.addObject("issue", issueService.getAll().get(0));
        return mav;
    }

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/hello")
    public void hello() {
    }

    @GetMapping("/login")
    public void login() {

    }
}
