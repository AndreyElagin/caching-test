package com.example.demo.services;

import com.example.demo.clients.SimpleGitHubApiClient;
import com.example.demo.entities.Contribution;
import com.example.demo.entities.GitRepository;
import com.example.demo.entities.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

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

    public List<GitRepository> getUserRepositories(String userName) {
        return getGitRepositoriesByUserName(userName);
    }

    public Map<String, Integer> getUserLanguagesUsage(String userName) {
        return getGitRepositoriesByUserName(userName).stream()
                .map(GitRepository::getLanguage)
                .filter(Objects::nonNull)
                .map(language -> new Tuple2<>(language, 1))
                .collect(groupingBy(Tuple2::_1, summingInt(Tuple2::_2)));
    }

    @SneakyThrows
    private List<GitRepository> getGitRepositoriesByUserName(String userName) {
        ResponseBody body = client.getUserRepositories(userName).body();
        requireNonNull(body);
        return objectMapper
                .readValue(body.string(), SearchResponse.class)
                .getItems();
    }

}
