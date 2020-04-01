package com.university.accounts.backend.dao;

import com.university.accounts.backend.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT e from Operation e " +
            "WHERE ((e.accountFrom IN :accounts AND type = 'TRANSFER') " +
            "OR (e.accountTo IN :accounts AND (type = 'RECEIPTS' OR type = 'REPLENISHMENT')))")
    List<Operation> findByAccounts(@Param("accounts") List<Long> accounts);
}
