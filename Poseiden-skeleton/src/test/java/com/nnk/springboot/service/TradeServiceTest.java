package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest extends AbstractCrudServiceTest<Trade> {

    @Mock
    private TradeRepository tradeRepository;

    @Override
    protected AbstractCrudService<Trade> initService() {
        this.repository = tradeRepository;
        return new TradeServiceImpl(tradeRepository);
    }


    @Override
    protected Trade createModelWithId(int id) {
        Trade trade = mock(Trade.class);
        lenient().when(trade.getId()).thenReturn(id);
        return trade;
    }


    @Override
    protected Trade createModelWithNullId() {
        Trade trade = new Trade();
        trade.setId(null);
        trade.setAccount("Test");
        return trade;
    }


    @Override
    protected Trade createUpdatedModel() {
        return mock(Trade.class);
    }

}
