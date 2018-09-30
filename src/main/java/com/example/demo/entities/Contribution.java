package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contribution {

    private int total;
    private List<ContributionWeek> weeks;
    private CommitAuthor author;

}
