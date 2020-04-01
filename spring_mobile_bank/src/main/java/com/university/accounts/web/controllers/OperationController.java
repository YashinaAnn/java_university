package com.university.accounts.web.controllers;

import com.university.accounts.backend.dto.MoneyTransferRequestDto;
import com.university.accounts.backend.dto.PutMoneyRequestDto;
import com.university.accounts.backend.model.Operation;
import com.university.accounts.backend.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationsService service;

    @PostMapping("/replenish")
    public ResponseEntity replenishAccount(@RequestBody PutMoneyRequestDto request) {
        service.putMoney(request);
        return ResponseEntity.ok("Operation is performed successfully");
    }

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestBody MoneyTransferRequestDto request) {
        service.transfer(request);
        return ResponseEntity.ok("Operation is performed successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Operation>> getOperationsList() {
        return ResponseEntity.ok(service.getOperationsList());
    }
}


