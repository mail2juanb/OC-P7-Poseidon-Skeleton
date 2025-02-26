package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl  extends AbstractCrudService<Trade> {

    public TradeServiceImpl(TradeRepository repository) {
        super(repository);
    }
}