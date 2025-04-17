package com.nnk.springboot.repository;


import com.nnk.springboot.domain.BidList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
// We dont want the H2 in-memory database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BidListRepositoryTest {

    @Autowired
    private BidListRepository bidListRepository;

    private final BidList bidList = new BidList();



    @BeforeEach
    public void setUp () {
        bidList.setAccount("UI-Account");
        bidList.setType("UI-Type");
        bidList.setBidQuantity(99.9);
        bidListRepository.save(bidList);
    }



    @Test
    void save_shouldSaveBidList () {

        // Given a BidList -- by setUp()

        // When try to save BidList -- by setUp()
        BidList response = bidListRepository.save(bidList);

        // Then BidList is saved
        assertTrue(bidListRepository.findById(response.getId()).isPresent());

    }



    @Test
    void update_shouldUpdateBidList () {

        // Given a BidList -- by setUp()

        // Given fields to update
        bidList.setAccount("UI-Account_Updated");
        bidList.setBidQuantity(0.);

        // When try to update BidList
        BidList response = bidListRepository.save(bidList);

        // Then BidList is updated
        assertEquals("UI-Account_Updated", response.getAccount());
        assertEquals(0., response.getBidQuantity());

    }



    @Test
    void findAll_shouldReturnBidList () {

        // Given a BidList -- by setUp()

        // When try to find all BidList
        List<BidList> responseList = bidListRepository.findAll();

        // Then return list of BidList
        assertFalse(responseList.isEmpty());
        assertTrue(responseList.contains(bidList));

    }



    @Test
    void findById_shouldReturnBidList_withValidId () {

        // Given a BidList -- by setUp()
        final Integer bidListId = bidList.getId();

        // When try to find BidList by Id
        Optional<BidList> response = bidListRepository.findById(bidListId);

        // Then return the BidList
        assertEquals(bidList.getAccount(), response.get().getAccount());
        assertEquals(bidList.getType(), response.get().getType());
        assertEquals(bidList.getBidQuantity(), response.get().getBidQuantity());

    }



    @Test
    void findById_shouldReturnEmpty_withoutValidId () {

        // Given a BidList -- by setUp()
        final Integer bidListId = bidList.getId() + 1;

        // When try to find BidList by Id
        Optional<BidList> response = bidListRepository.findById(bidListId);

        // Then return an empty BidList
        assertTrue(response.isEmpty());

    }



    @Test
    void delete_shouldDeleteBidList () {

        // Given a BidList -- by setUp()
        bidListRepository.save(bidList);

        // When try to delete BidList
        bidListRepository.deleteById(bidList.getId());

        // Then BidList is deleted
        Optional<BidList> response = bidListRepository.findById(bidList.getId());
        assertTrue(response.isEmpty());

    }

}
