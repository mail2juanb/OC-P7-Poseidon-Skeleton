package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repository.BidListRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest extends AbstractCrudServiceTest<BidList> {

    @Mock
    private BidListRepository bidListRepository;

    @Override
    protected AbstractCrudService<BidList> initService() {
        this.repository = bidListRepository;
        return new BidListServiceImpl(bidListRepository);
    }


    @Override
    protected BidList createModelWithId(int id) {
        BidList bidList = mock(BidList.class);
        lenient().when(bidList.getId()).thenReturn(id);
        return bidList;
    }


    @Override
    protected BidList createModelWithNullId() {
        BidList bidList = new BidList();
        bidList.setId(null);
        bidList.setAccount("Test");
        return bidList;
    }


    @Override
    protected BidList createUpdatedModel() {
        return mock(BidList.class);
    }

}
