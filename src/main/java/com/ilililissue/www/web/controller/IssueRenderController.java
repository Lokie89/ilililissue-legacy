package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.service.member.IssueMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class IssueRenderController {

    private final SimpleIssueService issueService;
    private final IssueMemberService issueMemberService;
    private final SimpleIssueService simpleIssueService;
    private final IssueCommentService issueCommentService;

    @GetMapping("/")
    public ModelAndView index() {

        IssueMember master = issueMemberService.create(new IssueMember());
        String[] images = new String[]{"/issueimage/simpson.jpg", "/issueimage/simpson.jpg"};
        SimpleIssue issue = SimpleIssue.builder().creator(master).title("이슈 제목").images(images).build();
        simpleIssueService.create(issue);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dayissue");
        mav.addObject("headerTitle", "오늘의 이슈");
        mav.addObject("issue", issueService.getAll().get(0));
        mav.addObject("comment", issueCommentService.getAll());
        return mav;
    }

//    @GetMapping({"/", "/home"})
//    public String home() {
//        return "home";
//    }
//
//    @GetMapping("/hello")
//    public void hello() {
//    }

    @GetMapping("/login")
    public void login() {

    }
}
