package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.exception.UserNotFoundException;
import ai.openbanking.OpenBanking.model.User;
import ai.openbanking.OpenBanking.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository){
        this.repository = repository;
    }

    @GetMapping("")
    Iterable<User> all(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    User getById(@PathVariable Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
