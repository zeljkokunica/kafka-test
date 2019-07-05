package com.demo.kafka.reports.account.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountJpa, String> {

    List<AccountJpa> findAll();
}
