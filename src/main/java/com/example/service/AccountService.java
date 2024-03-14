package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Optional<?> registerAccount(Account account){
        if (account.getUsername().isBlank() != true && account.getPassword().length() >= 4){
            Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
            if (accountOptional.isEmpty()){
                accountRepository.save(account);
                Optional<Account> registeredAccount = accountRepository.findByUsername(account.getUsername());
                return registeredAccount;
            }
            else {
                return Optional.of(-1);
            }
        }
        return Optional.empty();
    }

    public Optional<Account> loginAccount(Account account){
        Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
        if (accountOptional.isPresent()){
            Account returnedaccount = accountOptional.get();
            if (returnedaccount.getPassword().equals(account.getPassword())){
                return accountOptional;
            }
        }
        return Optional.empty();
    }

    public boolean findById(Integer id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()){
            return true;
        }
        return false;
    }
}
