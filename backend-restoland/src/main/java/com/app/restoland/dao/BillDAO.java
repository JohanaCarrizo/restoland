package com.app.restoland.dao;

import com.app.restoland.POJO.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDAO extends JpaRepository<Bill, Long> {

    List<Bill> getAllBills();

    List<Bill> getBillByUserName(@Param("username") String username);
}
