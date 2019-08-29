package com.demo.kafka.reports.domain.account;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountReportRepository extends CrudRepository<AccountReportJpa, String> {

    List<AccountReportJpa> findAll();
}
