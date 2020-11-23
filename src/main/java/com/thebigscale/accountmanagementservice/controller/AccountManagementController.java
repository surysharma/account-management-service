package com.thebigscale.accountmanagementservice.controller;

import com.google.common.flogger.FluentLogger;
import com.thebigscale.accountmanagementservice.dto.ClientAccount;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class AccountManagementController {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @GetMapping("/client/accounts")
    @ResponseBody
    public ResponseEntity<?> getClients(@RequestParam(name = "pc", defaultValue = "1") String pageCount) {

        logger.atInfo().log("Page count is " + pageCount);

        val clientProfile = ClientAccount.builder()
                .id(UUID.randomUUID().toString())
                .name("Sury")
                .age(22)
                .email("some@email.com")
                .profileSummary("Test profile summary")
                .build();

        return new ResponseEntity<>(clientProfile, HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    @ResponseBody
    public ResponseEntity<?> getClient(@PathVariable String id) {
        val clientProfile = ClientAccount.builder()
        .id(UUID.randomUUID().toString())
                .name("sury")
                .age(22)
                .profileSummary("Test profile summary")
                .email("some@email.com")
                .build();
        return new ResponseEntity<>(clientProfile, HttpStatus.OK);
    }

    @PostMapping(value = "/client/account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientAccount> addClient(@Valid @RequestBody ClientAccount clientAccount) {
        assert clientAccount != null;
        val clientProfileWithId = ClientAccount.builder()
                .id(UUID.randomUUID().toString())
                .name(clientAccount.getName())
                .age(clientAccount.getAge())
                .profileSummary(clientAccount.getProfileSummary())
                .email(clientAccount.getEmail())
                .build();

        return new ResponseEntity<>(clientProfileWithId, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exp) {
        val errorMap = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String errMessage = err.getDefaultMessage();
            errorMap.put(fieldName, errMessage);
        });
        return ResponseEntity.badRequest().body(errorMap);
    }

}
