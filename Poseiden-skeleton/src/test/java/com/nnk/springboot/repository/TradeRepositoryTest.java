package com.nnk.springboot.repository;


import com.nnk.springboot.domain.Trade;
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
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    private final Trade trade = new Trade();



    @BeforeEach
    public void setUp () {
        trade.setAccount("UI-Account");
        trade.setType("UI-Type");
        trade.setBuyQuantity(99.9);
        tradeRepository.save(trade);
    }



    @Test
    void save_shouldSaveTrade () {

        // Given a Trade -- by setUp()

        // When try to save Trade -- by setUp()
        Trade response = tradeRepository.save(trade);

        // Then Trade is saved
        assertTrue(tradeRepository.findById(response.getId()).isPresent());

    }



    @Test
    void update_shouldUpdateTrade () {

        // Given a Trade -- by setUp()

        // Given fields to update
        trade.setAccount("UI-Account_Updated");
        trade.setBuyQuantity(0.);

        // When try to update Trade
        Trade response = tradeRepository.save(trade);

        // Then trade is updated
        assertEquals("UI-Account_Updated", response.getAccount());
        assertEquals(0., response.getBuyQuantity());

    }



    @Test
    void findAll_shouldReturnTradeList () {

        // Given a Trade -- by setUp()

        // When try to find all Trade
        List<Trade> responseList = tradeRepository.findAll();

        // Then return list of trade
        assertFalse(responseList.isEmpty());
        assertTrue(responseList.contains(trade));

    }



    @Test
    void findById_shouldReturnTrade_withValidId () {

        // Given a Trade -- by setUp()
        final Integer tradeId = trade.getId();

        // When try to find Trade by Id
        Optional<Trade> response = tradeRepository.findById(tradeId);

        // Then return the trade
        assertEquals(trade.getAccount(), response.get().getAccount());
        assertEquals(trade.getType(), response.get().getType());
        assertEquals(trade.getBuyQuantity(), response.get().getBuyQuantity());

    }



    @Test
    void findById_shouldReturnEmpty_withoutValidId () {

        // Given a Trade -- by setUp()
        final Integer tradeId = trade.getId() + 1;

        // When try to find Trade by Id
        Optional<Trade> response = tradeRepository.findById(tradeId);

        // Then return an empty trade
        assertTrue(response.isEmpty());

    }



    @Test
    void delete_shouldDeleteTrade () {

        // Given a Trade -- by setUp()
        tradeRepository.save(trade);

        // When try to delete trade
        tradeRepository.deleteById(trade.getId());

        // Then trade is deleted
        Optional<Trade> response = tradeRepository.findById(trade.getId());
        assertTrue(response.isEmpty());

    }

}
