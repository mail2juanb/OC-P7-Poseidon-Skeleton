package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl  extends AbstractCrudService<Trade> {

    public TradeServiceImpl(TradeRepository repository) {
        super(repository);
    }
}