package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeDomainTest {

    @Test
    void update_shouldUpdateAccountTypeAndBuyQuantityOnly() {
        // Given an initial Trade
        Trade original = new Trade();
        original.setId(1);
        original.setAccount("OriginalAccount");
        original.setType("OriginalType");
        original.setBuyQuantity(10.0);
        original.setSellQuantity(5.0);
        original.setSecurity("ABC");

        // And a new Trade with different values
        Trade updated = new Trade();
        updated.setAccount("UpdatedAccount");
        updated.setType("UpdatedType");
        updated.setBuyQuantity(99.9);
        updated.setSellQuantity(1000.0);
        updated.setSecurity("DEF");

        // When updating original with updated
        original.update(updated);

        // Then only account, type, and buyQuantity should be updated
        assertEquals("UpdatedAccount", original.getAccount());
        assertEquals("UpdatedType", original.getType());
        assertEquals(99.9, original.getBuyQuantity());

        // And the other fields remain unchanged
        assertEquals(5.0, original.getSellQuantity());
        assertEquals("ABC", original.getSecurity());
    }

}
