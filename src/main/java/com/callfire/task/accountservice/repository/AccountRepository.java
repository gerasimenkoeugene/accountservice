package com.callfire.task.accountservice.repository;

import com.callfire.task.accountservice.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
