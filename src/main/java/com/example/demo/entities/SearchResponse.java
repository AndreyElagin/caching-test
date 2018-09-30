package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

    @JsonProperty("total_count")
    String totalCount;

    @JsonProperty("incomplete_results")
    boolean incompleteResults;

    List<GitRepository> items;

}
