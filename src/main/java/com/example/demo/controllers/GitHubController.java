package com.example.demo.controllers;

import com.example.demo.entities.Contribution;
import com.example.demo.entities.GitRepository;
import com.example.demo.services.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping("/topReposByStars")
    public List<GitRepository> getTopReposByStars() {
        return gitHubService.getTopGitHubRepos();
    }

    @GetMapping("commiters")
    public Contribution[] getCommitersInRepo(String repoOwner, String repoName) {
        return gitHubService.getRepoContributors(repoOwner, repoName);
    }

}
