package com.example.demo.services;

import com.example.demo.models.Supplier;
import com.example.demo.models.SupplierRequest;
import com.example.demo.repositories.SupplierRepository;
import com.example.demo.repositories.SupplierRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierRequestService {

    private final SupplierRequestRepository supplierRequestRepository;
    private final SupplierRepository supplierRepository;

    // Конструктор (Spring сам подставит зависимости)
    public SupplierRequestService(SupplierRequestRepository supplierRequestRepository, SupplierRepository supplierRepository) {
        this.supplierRequestRepository = supplierRequestRepository;
        this.supplierRepository = supplierRepository;
    }

    // Запрос на добавление/изменение поставщика
    public SupplierRequest createRequest(SupplierRequest request) {
        return supplierRequestRepository.save(request);
    }

    // Получить все заявки на проверку
    public List<SupplierRequest> getPendingRequests() {
        return supplierRequestRepository.findByApprovedFalse();
    }

    // Подтверждение заявки
    public Supplier approveRequest(Long requestId) {
        SupplierRequest request = supplierRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Запрос не найден"));

        Supplier supplier = new Supplier();
        supplier.setId(request.getSupplierId() != null ? request.getSupplierId() : null);
        supplier.setName(request.getName());
        supplier.setType(request.getType());
        supplier.setDescription(request.getDescription());
        supplier.setContactInfo(request.getContactInfo());
        supplier.setPrice(request.getPrice());

        supplier = supplierRepository.save(supplier);
        request.setApproved(true);
        supplierRequestRepository.save(request);

        return supplier;
    }
}


