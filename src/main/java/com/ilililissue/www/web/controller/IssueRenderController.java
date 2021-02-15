package com.ilililissue.www.web.controller;

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

    @GetMapping("/")
    public ModelAndView index() {

//        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.MASTER));
//        DefaultIssue issue = DefaultIssue.builder(master,"이슈 제목").images("/issueimage/simpson.jpg","/issueimage/simpson.jpg").build();
//        defaultIssueService.create(issue);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dayissue");
        mav.addObject("headerTitle", "오늘의 이슈");
        mav.addObject("issue", issueService.getAll().get(0));
        return mav;
    }

    @GetMapping("/signin")
    public ModelAndView signin() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signin");
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signup");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("list");
        return mav;
    }
}
