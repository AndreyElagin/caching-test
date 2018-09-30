package com.example.demo.services;

import com.example.demo.clients.SimpleGitHubApiClient;
import com.example.demo.entities.Contribution;
import com.example.demo.entities.GitRepository;
import com.example.demo.entities.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final SimpleGitHubApiClient client;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public List<GitRepository> getTopGitHubRepos() {
        ResponseBody body = client.getMostStarredRepos().body();
        requireNonNull(body);
        return objectMapper
                .readValue(body.string(), SearchResponse.class)
                .getItems();
    }

    @SneakyThrows
    public Contribution[] getRepoContributors(String repoOwner, String repoName) {
        ResponseBody body = client.getRepoCommiters(repoOwner, repoName).body();
        requireNonNull(body);
        return objectMapper.readValue(body.string(), Contribution[].class);
    }

}
