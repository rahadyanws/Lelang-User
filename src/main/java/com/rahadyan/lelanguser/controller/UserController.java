package com.rahadyan.lelanguser.controller;

import com.rahadyan.lelanguser.dto.AddUserRequest;
import com.rahadyan.lelanguser.dto.ResponseWrapper;
import com.rahadyan.lelanguser.model.User;
import com.rahadyan.lelanguser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String READ_BY_EMAIL_SUCCESS = "User Shown Success!";
    private static final String READ_BY_EMAIL_FAILED = "User Not Found!";
    private static final String ADD_USER_SUCCESS = "User created Success!";
    private static final String ADD_USER_FAILED = "User created Failed!";

    @Autowired
    UserService userService;

    @CrossOrigin(origins = "*")
    @GetMapping("/read-by-email")
    public ResponseEntity<ResponseWrapper> readByEmail(@RequestParam("email") String email) {
        User user = null;
        List<String> errors = new ArrayList<>();
        ResponseWrapper responseMessage = null;
        try {
            user = userService.findByEmail(email);
            responseMessage = new ResponseWrapper(READ_BY_EMAIL_SUCCESS, user, 200, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = new ResponseWrapper(READ_BY_EMAIL_FAILED, user, 404, errors);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ResponseEntity<ResponseWrapper> add(@RequestBody AddUserRequest addUserRequest, Errors err) {
        User user = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        try {
            user = userService.createUser(addUserRequest);
            responseMessage = new ResponseWrapper(ADD_USER_SUCCESS, user, 200, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = new ResponseWrapper(ADD_USER_FAILED, null, 500, errors);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

}
