package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest extends AbstractCrudServiceTest<CurvePoint> {

    @Mock
    private CurvePointRepository curvePointRepository;



    @Override
    protected AbstractCrudService<CurvePoint> initService() {
        this.repository = curvePointRepository;

        return new CurvePointServiceImpl(curvePointRepository);

    }



    @Override
    protected CurvePoint createModelWithId(int id) {
        CurvePoint curvePoint = mock(CurvePoint.class);
        lenient().when(curvePoint.getId()).thenReturn(id);

        return curvePoint;

    }



    @Override
    protected CurvePoint createModelWithNullId() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(null);
        curvePoint.setTerm(123.123);

        return curvePoint;

    }



    @Override
    protected CurvePoint createUpdatedModel() {
        return mock(CurvePoint.class);

    }

}
