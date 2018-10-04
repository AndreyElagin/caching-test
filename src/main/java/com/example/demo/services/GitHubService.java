package com.example.demo.services;

import com.example.demo.clients.SimpleGitHubApiClient;
import com.example.demo.entities.Contribution;
import com.example.demo.entities.GitRepository;
import com.example.demo.entities.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.infinispan.Cache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final SimpleGitHubApiClient client;
    private final ObjectMapper objectMapper;

    private final Cache<String, String> cache;

    @SneakyThrows
    public List<GitRepository> getTopGitHubRepos() {
        String getTopGitHubRepos = cache.get("getTopGitHubRepos");
        if (isNull(getTopGitHubRepos)) {
            getTopGitHubRepos = Optional.ofNullable(client.getMostStarredRepos())
                    .map(Response::body)
                    .map(this::extractResponseContent)
                    .orElseThrow(RuntimeException::new);
            cache.put("getTopGitHubRepos", getTopGitHubRepos);
        }
        return objectMapper
                .readValue(getTopGitHubRepos, SearchResponse.class)
                .getItems();
    }

    @SneakyThrows
    public Contribution[] getRepoContributors(String repoOwner, String repoName) {
        String repoContributors = cache.get("repoContributors");
        if (isNull(repoContributors)) {
            repoContributors = Optional.ofNullable(client.getRepoCommiters(repoOwner, repoName))
                    .map(Response::body)
                    .map(this::extractResponseContent)
                    .orElseThrow(RuntimeException::new);
            cache.put("repoContributors", repoContributors);
        }
        return objectMapper.readValue(repoContributors, Contribution[].class);
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
        String reposByUserName = cache.get("reposByUserName");
        if (isNull(reposByUserName)) {
            reposByUserName = Optional.ofNullable(client.getUserRepositories(userName))
                    .map(Response::body)
                    .map(this::extractResponseContent)
                    .orElseThrow(RuntimeException::new);
            cache.put("reposByUserName", reposByUserName);
        }
        return objectMapper
                .readValue(reposByUserName, SearchResponse.class)
                .getItems();
    }

    @SneakyThrows
    private String extractResponseContent(ResponseBody responseBody) {
        return responseBody.string();
    }

}
