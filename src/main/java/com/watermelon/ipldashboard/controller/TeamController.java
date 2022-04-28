package com.watermelon.ipldashboard.controller;

import com.watermelon.ipldashboard.model.Match;
import com.watermelon.ipldashboard.model.Team;
import com.watermelon.ipldashboard.repository.MatchRepository;
import com.watermelon.ipldashboard.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping(value = "/team")
    public Iterable<Team> getAllTeam() {
        return this.teamRepository.findAll();
    }

    @GetMapping(value = "/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4));
        return team;
    }

    @GetMapping(value = "/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(
            @PathVariable String teamName,
            @RequestParam int year
    ) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);

        return this.matchRepository.getMatchesByTeamBetweenDates(
                teamName,
                startDate,
                endDate
        );
    }

}
