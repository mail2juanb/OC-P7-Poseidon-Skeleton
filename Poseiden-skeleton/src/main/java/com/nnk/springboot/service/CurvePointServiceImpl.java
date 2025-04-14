package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
import org.springframework.stereotype.Service;

@Service
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> {

    public CurvePointServiceImpl(CurvePointRepository repository) {
        super(repository);
    }

}
