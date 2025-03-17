package com.example.demo.repositories;

import com.example.demo.models.SupplierRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRequestRepository extends JpaRepository<SupplierRequest, Long> {
    List<SupplierRequest> findByApprovedFalse();
}
