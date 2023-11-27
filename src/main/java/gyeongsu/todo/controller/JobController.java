package gyeongsu.todo.controller;

import gyeongsu.todo.domain.Job;
import gyeongsu.todo.domain.JobStatus;
import gyeongsu.todo.domain.Member;
import gyeongsu.todo.service.JobService;
import gyeongsu.todo.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Controller
public class JobController {
    private final JobService jobService;
    private final MemberService memberService;

    @Autowired
    public JobController(JobService jobService, MemberService memberService) {
        this.jobService = jobService;
        this.memberService = memberService;
    }

    @RequestMapping("/")
    public String list(Model model) {
        List<Job> jobs = jobService.findJobs();

        model.addAttribute("jobs", jobs);
        return "jobs/jobList";
    }
    @GetMapping("/jobs/new")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<JobStatus> statuses = new ArrayList<>(EnumSet.allOf(JobStatus.class));

        model.addAttribute("members", members);
        model.addAttribute("statuses", statuses);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("form", new JobForm());

        return "jobs/createJobForm";
    }

    @PostMapping("/jobs/new")
    public String create(@Valid JobForm form, BindingResult result) {
        if (result.hasErrors())
            throw new RuntimeException();
        jobService.saveJob(form.getMemberId(), form.getName(), form.getDescription(), form.getStatus(), form.getExpiryDate());
        return "redirect:/";
    }

//    @GetMapping("/jobs/{jobId}/edit")
//    public String updateJobForm(@PathVariable("jobId") Long jobId, Model model) {
//        Job job = jobService.findOne(jobId);
//        JobForm form = new JobForm();
//        form
//
//        form.setId(item.getId());
//        form.setName(item.getName());
//        form.setPrice(item.getPrice());
//        form.setStockQuantity(item.getStockQuantity());
//        form.setAuthor(item.getAuthor());
//        form.setIsbn(item.getIsbn());
//        model.addAttribute("form", form);
//        return "jobs/updateJobForm";
//    }

//    @PostMapping("/jobs/{jobId}/edit")
//    public String updateJob(@ModelAttribute("form") JobForm form) {
//        jobService.saveJob();
//        return "redirect:/";
//    }
}
