package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributionWeek {

    @JsonProperty("w")
    private int startOfWeek;

    @JsonProperty("a")
    private int numberOfAdditions;

    @JsonProperty("d")
    private int numberOfDeletions;

    @JsonProperty("c")
    private int numberOfCommits;

}
