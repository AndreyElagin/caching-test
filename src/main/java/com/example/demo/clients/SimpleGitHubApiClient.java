package com.example.demo.clients;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleGitHubApiClient {

    public static final String GET_MOST_STARRED_REPOS_REQUEST = "/search/repositories?q=stars:>1&order=desc&sort=stars";
    private final OkHttpClient httpClient;

    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String GITHUB_V3_MIME_TYPE = "application/vnd.github.v3+json";

    @SneakyThrows
    public Response getMostStarredRepos() {
        Request request = new Request.Builder()
                .get()
                .url(GITHUB_API_URL + GET_MOST_STARRED_REPOS_REQUEST)
                .addHeader(HttpHeaders.CONTENT_TYPE, GITHUB_V3_MIME_TYPE)
                .build();
        return httpClient.newCall(request)
                .execute();
    }

    @SneakyThrows
    public Response getRepoCommiters(String repoOwner, String repoName) {
        Request request = new Request.Builder()
                .get()
                .url(GITHUB_API_URL + "/"
                        + "repos" + "/"
                        + repoOwner + "/"
                        + repoName + "/stats/contributors")
                .addHeader(HttpHeaders.CONTENT_TYPE, GITHUB_V3_MIME_TYPE)
                .build();
        return httpClient.newCall(request)
                .execute();
    }

    public void getUserRepos() {

    }

    public void getUserLanguages() {

    }

}
