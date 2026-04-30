package com.mikaelson.taskManager.service;

import com.mikaelson.taskManager.dto.request.TeamAddRecord;
import com.mikaelson.taskManager.dto.request.TeamCreateRecord;
import com.mikaelson.taskManager.dto.response.TeamAddResponse;
import com.mikaelson.taskManager.dto.response.TeamCreateResponse;
import com.mikaelson.taskManager.entity.Team;
import com.mikaelson.taskManager.entity.User;
import com.mikaelson.taskManager.entity.UserRole;
import com.mikaelson.taskManager.exceptions.UserNotHasAuthorityException;
import com.mikaelson.taskManager.repository.TeamRepository;
import com.mikaelson.taskManager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository repository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public TeamCreateResponse createTeam(Authentication auth, TeamCreateRecord dto){
        if(dto==null){
            throw new NullPointerException();
        }
        boolean isManager = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
        if(auth.isAuthenticated() && isManager){
            Team team = new Team();
            User user = userRepository.findByUserLogin(dto.userLogin());
            team.setManager(user);
            team.setTeamName(dto.nameTeam());
            user.setRole(UserRole.MANAGER);
            userRepository.save(user);
            repository.save(team);
            return toResponseCreateTeamDto(team);
        }
        throw new UserNotHasAuthorityException(auth.getName());
    }

    public TeamAddResponse addUser(Authentication auth, TeamAddRecord dto, String teamName){
        if(dto==null){
            throw new NullPointerException();
        }
        boolean isManager = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
        if(auth.isAuthenticated() && isManager){
            Team team = repository.findByTeamName(teamName);
            User user = userRepository.findByUserLogin(dto.userLogin());
            if(!team.getUsuarios().contains(user)){
                team.addUser(user);
                repository.save(team);
                return toResponseAddDto(team, user);
            }
        }
        throw new UserNotHasAuthorityException(auth.getName());
    }

    private TeamAddResponse toResponseAddDto(Team team, User user) {
        return new TeamAddResponse(
                team.getTeamName(),
                user.getName());
    }

    private TeamCreateResponse toResponseCreateTeamDto(Team team) {
        return new TeamCreateResponse(
                team.getTeamName(),
                team.getManager().getName());
    }
}
