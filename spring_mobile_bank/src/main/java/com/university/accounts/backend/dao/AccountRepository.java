package com.university.accounts.backend.dao;

import com.university.accounts.backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.amount = :amount WHERE a.id = :id")
    void setAmountById(@Param("id") long id, @Param("amount") BigDecimal amount);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.isDefault = :defaultValue WHERE a.id = :id")
    void setIsDefaultById(@Param("id") long id, @Param("defaultValue") boolean defaultValue);
}
