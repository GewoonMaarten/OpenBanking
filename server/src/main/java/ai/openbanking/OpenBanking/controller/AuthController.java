package ai.openbanking.OpenBanking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController("/api")
public class AuthController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
