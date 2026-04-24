package com.mikaelson.taskManager.controller;

import com.mikaelson.taskManager.dto.request.TaskCreateRecord;
import com.mikaelson.taskManager.dto.response.TaskCreateResponse;
import com.mikaelson.taskManager.dto.response.TaskResponse;
import com.mikaelson.taskManager.dto.response.TaskResponseRecord;
import com.mikaelson.taskManager.dto.response.TaskStatusResponse;
import com.mikaelson.taskManager.service.TaskService;
import com.mikaelson.taskManager.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service){
        this.service = service;
    }

    @PostMapping("/task/create")
    public ResponseEntity<TaskCreateResponse> createTask(Authentication auth, @RequestBody TaskCreateRecord dto) throws UserPrincipalNotFoundException {
        TaskCreateResponse response = service.createTask(auth,dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/all")
    public ResponseEntity<List<TaskResponseRecord>> getTasks(Authentication auth){
        if(auth.isAuthenticated()){
            System.out.println(auth.getName());
            List<TaskResponseRecord> response = service.getTasks();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/task/accept/{id}")
    public ResponseEntity<TaskStatusResponse> acceptTask(@PathVariable Long id){
        TaskStatusResponse dto = service.acceptTask(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/task/done/{id}")
    public ResponseEntity<TaskStatusResponse> completeTask(@PathVariable Long id){
        TaskStatusResponse dto = service.completeTask(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponse> findByIdTask(@PathVariable Long id){
        TaskResponse response = service.findByIDTask(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/status")
    public ResponseEntity<List<TaskStatusResponse>> tasksStatus(){
        List<TaskStatusResponse> response = service.getTasksStatus();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/task/accepts")
    public ResponseEntity<List<TaskStatusResponse>> tasksAccepts(){
        List<TaskStatusResponse> response = service.getTasksAccepts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<Void> taskDelete(@PathVariable Long id){
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
