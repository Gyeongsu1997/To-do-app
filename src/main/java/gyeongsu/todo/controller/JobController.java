package gyeongsu.todo.controller;

import gyeongsu.todo.domain.Job;
import gyeongsu.todo.domain.JobStatus;
import gyeongsu.todo.domain.Member;
import gyeongsu.todo.dto.JobDto;
import gyeongsu.todo.dto.JobForm;
import gyeongsu.todo.dto.JobSearch;
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
    public String list(@ModelAttribute("jobSearch") JobSearch jobSearch, Model model) {
        List<Job> jobs = jobService.findJobs(jobSearch);

        model.addAttribute("jobs", jobs);
        model.addAttribute("today", LocalDate.now());
        return "jobs/jobList";
    }
    @GetMapping("/jobs/new")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<JobStatus> statuses = new ArrayList<>(EnumSet.allOf(JobStatus.class));

        model.addAttribute("members", members);
        model.addAttribute("statuses", statuses);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("jobForm", new JobForm());

        return "jobs/createJobForm";
    }

    @PostMapping("/jobs/new")
    public String create(@Valid JobForm jobForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Member> members = memberService.findMembers();
            List<JobStatus> statuses = new ArrayList<>(EnumSet.allOf(JobStatus.class));

            model.addAttribute("members", members);
            model.addAttribute("statuses", statuses);
            model.addAttribute("today", LocalDate.now());
            return "jobs/createJobForm";
        }

        JobDto jobDto = new JobDto();
        jobDto.setMemberId(jobForm.getMemberId());
        jobDto.setName(jobForm.getName());
        jobDto.setDescription(jobForm.getDescription());
        jobDto.setStatus(jobForm.getStatus());
        jobDto.setExpiryDate(jobForm.getExpiryDate());

        jobService.saveJob(jobDto);
        return "redirect:/";
    }

    @GetMapping("/jobs/{jobId}/edit")
    public String updateJobForm(@PathVariable("jobId") Long jobId, Model model) {
        Job job = jobService.findOne(jobId);
        List<JobStatus> statuses = new ArrayList<>(EnumSet.allOf(JobStatus.class));

        JobForm jobForm = new JobForm();
        jobForm.setMemberId(job.getMember().getId());
        jobForm.setName(job.getName());
        jobForm.setDescription(job.getDescription());
        jobForm.setStatus(job.getStatus());
        jobForm.setExpiryDate(job.getExpiryDate());

        model.addAttribute("member", job.getMember().getName());
        model.addAttribute("statuses", statuses);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("jobForm", jobForm);

        return "jobs/updateJobForm";
    }

    @PostMapping("/jobs/{jobId}/edit")
    public String updateJob(@PathVariable("jobId") Long jobId, @Valid JobForm jobForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<JobStatus> statuses = new ArrayList<>(EnumSet.allOf(JobStatus.class));

            model.addAttribute("member", jobService.findOne(jobId).getMember().getName());
            model.addAttribute("statuses", statuses);
            model.addAttribute("today", LocalDate.now());
            return "jobs/updateJobForm";
        }

        JobDto jobDto = new JobDto();

        jobDto.setId(jobId);
        jobDto.setName(jobForm.getName());
        jobDto.setDescription(jobForm.getDescription());
        jobDto.setStatus(jobForm.getStatus());
        jobDto.setExpiryDate(jobForm.getExpiryDate());

        jobService.updateJob(jobDto);
        return "redirect:/";
    }
}
