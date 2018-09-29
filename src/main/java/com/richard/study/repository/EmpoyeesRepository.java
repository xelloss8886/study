package com.richard.study.repository;

import com.richard.study.entity.SalaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpoyeesRepository extends JpaRepository<SalaryEntity, Integer> {
    Page<SalaryEntity> findAll(Pageable pageable);
}
