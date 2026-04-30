package com.mikaelson.taskManager.controller;

import com.mikaelson.taskManager.dto.request.TeamAddRecord;
import com.mikaelson.taskManager.dto.request.TeamCreateRecord;
import com.mikaelson.taskManager.dto.response.TeamAddResponse;
import com.mikaelson.taskManager.dto.response.TeamCreateResponse;
import com.mikaelson.taskManager.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @PostMapping("/team/create")
    public ResponseEntity<TeamCreateResponse> createTeam(Authentication auth, @RequestBody TeamCreateRecord dto){
        TeamCreateResponse newTeam = service.createTeam(auth,dto);
        return ResponseEntity.ok(newTeam);
    }
    @PostMapping("/team/{teamName}")
    public ResponseEntity<TeamAddResponse> addUserOnTeam(Authentication auth, @RequestBody TeamAddRecord dto, @PathVariable String teamName){
        TeamAddResponse newUserTeam = service.addUser(auth,dto,teamName);
        return ResponseEntity.ok(newUserTeam);
    }
}
