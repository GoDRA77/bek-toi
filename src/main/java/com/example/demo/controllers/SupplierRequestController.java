package com.example.demo.controllers;

import com.example.demo.models.Supplier;
import com.example.demo.models.SupplierRequest;
import com.example.demo.services.SupplierRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier-requests")

public class SupplierRequestController {

    private final SupplierRequestService supplierRequestService;

    public SupplierRequestController(SupplierRequestService supplierRequestService) {
        this.supplierRequestService = supplierRequestService;
    }

    @PostMapping
    public SupplierRequest createRequest(@RequestBody SupplierRequest request) {
        return supplierRequestService.createRequest(request);
    }

    @GetMapping("/pending")
    public List<SupplierRequest> getPendingRequests() {
        return supplierRequestService.getPendingRequests();
    }

    @PostMapping("/{requestId}/approve")
    public Supplier approveRequest(@PathVariable Long requestId) {
        return supplierRequestService.approveRequest(requestId);
    }
}
