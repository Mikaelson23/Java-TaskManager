package com.mikaelson.taskManager.service;

import com.mikaelson.taskManager.dto.request.TaskCreateRecord;
import com.mikaelson.taskManager.dto.response.TaskCreateResponse;
import com.mikaelson.taskManager.dto.response.TaskResponse;
import com.mikaelson.taskManager.dto.response.TaskResponseRecord;
import com.mikaelson.taskManager.dto.response.TaskStatusResponse;
import com.mikaelson.taskManager.entity.Status;
import com.mikaelson.taskManager.entity.Task;
import com.mikaelson.taskManager.entity.UserRole;
import com.mikaelson.taskManager.exceptions.TaskNotFoundException;
import com.mikaelson.taskManager.exceptions.UserNotHasAuthorityException;
import com.mikaelson.taskManager.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;


    public TaskService(TaskRepository repository){
        this.repository = repository;

    }

    public TaskCreateResponse createTask(Authentication auth, TaskCreateRecord dto){
        if(dto == null){
            throw new NullPointerException();
        }
        if(auth.isAuthenticated() && auth.getAuthorities().contains("ROLE_ADMIN")){
            Task task = new Task();
            task.setTitle(dto.title());
            task.setDescription(dto.description());
            task.setPriority(dto.priority());
            task.setLimitDate(dto.limitDate());
            task.setStatus(Status.Pending);
            repository.save(task);
            return toResponseCreateDTO(task);
        }
        throw new UserNotHasAuthorityException(auth.getName());


    }

    public List<TaskResponseRecord> getTasks(){
        List<Task> tasks = repository.findAll();
        List<TaskResponseRecord> taskDTO= new ArrayList<>();
        for(Task i: tasks){
            taskDTO.add(toResponseDTO(i));
        }
        return taskDTO;
    }
    public TaskStatusResponse acceptTask(Long id){
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        if(task.getStatus()!= Status.Pending){
            throw new IllegalStateException("Task em andamento");
        }
        task.setStatus(Status.OnWork);
        task.setAcceptDate(LocalDate.now());
        Task updated = repository.save(task);
        return toResponseStatusDTO(updated);
    }

    public TaskStatusResponse completeTask(Long id) {
        Task task = repository.findById(id).orElseThrow(()-> new TaskNotFoundException(id));
        if(task.getStatus()!= Status.OnWork){
            throw new IllegalStateException("task finalizada");
        }
        task.setStatus(Status.Done);
        task.setCompletionDate(LocalDate.now());
        return toResponseStatusDTO(repository.save(task));
    }

    public TaskResponse findByIDTask(Long id){;
        return toResponseCompleteDTO(repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
    }


    public List<TaskStatusResponse> getTasksStatus() {
        List<Task> tasks = repository.findAll();
        List<TaskStatusResponse> taskDTO= new ArrayList<>();
        for(Task i: tasks){
            taskDTO.add(toResponseStatusDTO(i));

        }
        return taskDTO;
    }

    public List<TaskStatusResponse> getTasksAccepts() {
        List<Task> tasks = repository.findAll();
        List<TaskStatusResponse> taskDTO= new ArrayList<>();
        for(Task i: tasks){
            if(i.getStatus()==Status.OnWork){
                taskDTO.add(toResponseStatusDTO(i));
            }
        }
        return taskDTO;
    }

    public void deleteTask(Long id) {
        Task task = repository.findById(id).orElseThrow(()-> new TaskNotFoundException(id));
        repository.delete(task);
    }

    private TaskCreateResponse toResponseCreateDTO(Task task) {
        return new TaskCreateResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus());
    }

    private TaskStatusResponse toResponseStatusDTO(Task task) {
        return new TaskStatusResponse(
                task.getId(),
                task.getStatus(),
                task.getAcceptDate(),
                task.getCompletionDate());
    }

    private TaskResponseRecord toResponseDTO(Task task) {
        return new TaskResponseRecord(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getLimitDate());
    }

    private TaskResponse toResponseCompleteDTO(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getLimitDate(),
                task.getAcceptDate(),
                task.getCompletionDate());
    }
}
