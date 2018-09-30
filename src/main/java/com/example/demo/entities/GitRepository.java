package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitRepository {

    long id;

    String name;

    String ownerName;

    @JsonProperty("created_at")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime updatedAt;

    String language;

    @JsonProperty("full_name")
    String fullName;

    String description;

    @JsonProperty("private")
    String isPrivate;

    @JsonProperty("fork")
    String isFork;

    String url;

    @JsonProperty("html_url")
    String htmlUrl;

    @JsonProperty("git_url")
    String gitUrl;

    @JsonProperty("forks_count")
    Long forksCount;

    @JsonProperty("stargazers_count")
    Long stargazersCount;

    @JsonProperty("watchers_count")
    Long watchersCount;

    @JsonProperty("owner")
    private void unpackOwner(Map<String, Object> response) {
        ownerName = (String) response.get("login");
    }

}
