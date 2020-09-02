package com.rahadyan.lelanguser.controller;

import com.rahadyan.lelanguser.dto.AddUserRequest;
import com.rahadyan.lelanguser.dto.ResponseWrapper;
import com.rahadyan.lelanguser.dto.TopupRequest;
import com.rahadyan.lelanguser.dto.UpdateRoleRequest;
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
    private static final String BROWSE_USER_SUCCESS = "Browse User Success!";
    private static final String BROWSE_USER_FAILED = "Browse User Failed!";
    private static final String ADD_USER_SUCCESS = "User Created Success!";
    private static final String ADD_USER_FAILED = "User Created Failed!";
    private static final String TOPUP_AMOUNT_SUCCESS = "Topup Amount Success!";
    private static final String TOPUP_AMOUNT_FAILED = "Topup Amount Failed!";
    private static final String DELETE_USER_SUCCESS = "Delete User Success!";
    private static final String DELETE_USER_FAILED = "Delete User Failed!";
    private static final String UPDATE_USER_ROLE_SUCCESS = "Update User Role Success!";
    private static final String UPDATE_USER_ROLE_FAILED = "Update User Role Failed!";

    @Autowired
    UserService userService;

    @CrossOrigin(origins = "*")
    @GetMapping("/browse-user")
    public ResponseEntity<ResponseWrapper> browse() {
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        try{
            List<User> users = userService.findAll();
            responseMessage = new ResponseWrapper(BROWSE_USER_SUCCESS, users, 200, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = new ResponseWrapper(BROWSE_USER_FAILED, null, 404, errors);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

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
            responseMessage = new ResponseWrapper(ADD_USER_FAILED, user, 500, errors);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/topup")
    public ResponseEntity<ResponseWrapper> topup(@RequestBody TopupRequest topupRequest, Errors err) {
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        User user = null;
        try {
            user = this.userService.topup(topupRequest);
            responseMessage = new ResponseWrapper(TOPUP_AMOUNT_SUCCESS, user, 200, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = new ResponseWrapper(TOPUP_AMOUNT_FAILED, user, 500, errors);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseWrapper> delete(@RequestParam("email") String email) {
        List<String> errors = new ArrayList<>();
        ResponseWrapper responseMessage = null;
        try {
            userService.delete(email);
            responseMessage =  new ResponseWrapper(DELETE_USER_SUCCESS, null, 200, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = new ResponseWrapper(DELETE_USER_FAILED, null, 500, errors);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/update-role")
    public ResponseEntity<ResponseWrapper> updateRole(@RequestBody UpdateRoleRequest updateRoleRequest, Errors err) {
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        User user = null;
        try {
            user = this.userService.updateRole(updateRoleRequest);
            responseMessage = new ResponseWrapper(UPDATE_USER_ROLE_SUCCESS, user, 200, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = new ResponseWrapper(UPDATE_USER_ROLE_FAILED, user, 500, errors);
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(responseMessage);
    }

}
