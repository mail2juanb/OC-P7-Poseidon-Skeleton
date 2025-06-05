package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BidListDomainTest {

    @Test
    void update_shouldUpdateFieldsFromGivenBidList() {
        // Given an existing BidList
        BidList bidList = new BidList();
        bidList.setId(1);
        bidList.setAccount("Account-Original");
        bidList.setType("Type-Original");
        bidList.setBidQuantity(10.0);

        // And a BidList with new values
        BidList updated = new BidList();
        updated.setId(2);
        updated.setAccount("Account-Updated");
        updated.setType("Type-Updated");
        updated.setBidQuantity(99.9);

        // When calling update
        bidList.update(updated);

        // Then only the expected fields are updated
        //assertEquals(updated.getId(), bidList.getId());
        assertEquals("Account-Updated", bidList.getAccount());
        assertEquals("Type-Updated", bidList.getType());
        assertEquals(99.9, bidList.getBidQuantity());
    }

}
