package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.NotFoundIdException;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Deprecated
@ExtendWith(MockitoExtension.class)
public class TradeServiceTest_manual {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;



    @Test
    void getById_shouldReturnTrade_withValidId () {

        // Given a trade
        final int tradeId = 128;
        Trade trade = new Trade();
        trade.setId(tradeId);
        trade.setAccount("UT-Account");
        trade.setType("UT-Type");
        trade.setBuyQuantity(999.9);

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(trade));

        // When try to get trade with valid Id
        Trade response = tradeService.getById(tradeId);

        // Then return trade
        assertEquals(trade, response);
        verify(tradeRepository, times(1)).findById(tradeId);

    }



    @Test
    void getById_shouldThrowException_withInvalidId () {

        // Given a trade
        final int tradeId = 129;

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.empty());

        // When try to get trade , then a exception thrown
        assertThrows(NotFoundIdException.class, () -> tradeService.getById(tradeId));
        verify(tradeRepository, times(1)).findById(tradeId);

    }



    @Test
    void create_shouldSaveTrade_whenNullId() {

        // Given a trade with null id (normal for create)
        final String tradeAccount = "UT-Account";
        final String tradeType = "UT-Type";

        Trade trade = new Trade();
        trade.setId(null);
        trade.setAccount(tradeAccount);
        trade.setType(tradeType);
        trade.setBuyQuantity(128.2);

        // When try to save trade
        tradeService.create(trade);

        // Then trade is saved
        ArgumentCaptor<Trade> tradeArgumentCaptor = ArgumentCaptor.forClass(Trade.class);
        verify(tradeRepository, times(1)).save(tradeArgumentCaptor.capture());
        Trade savedTrade = tradeArgumentCaptor.getValue();
        assertEquals(tradeAccount, savedTrade.getAccount());
        assertEquals(tradeType, savedTrade.getType());

    }



    @Test
    void create_shouldThrowException_whenIdNotNull() {

        // Given a trade with not null Id
        Trade trade = new Trade();
        trade.setId(99);

        // When try to create trade , Then a exception thrown
        assertThrows(NotFoundIdException.class, () -> tradeService.create(trade));
        verify(tradeRepository, never()).save(any());

    }



    @Test
    void update_shouldUpdateAndSaveTrade_whenIdExists() {

        // Given new values for existing trade
        final int tradeId = 28;
        final String tradeType = "UT-Type-Update";
        Trade existingTrade = mock(Trade.class);
        Trade trade = new Trade();
        trade.setId(tradeId);
        trade.setType(tradeType);

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(existingTrade));
        when(existingTrade.update(trade)).thenReturn(existingTrade);

        // When try to update trade
        tradeService.update(trade);

        // Then trade is updated with new values
        ArgumentCaptor<Trade> tradeArgumentCaptor = ArgumentCaptor.forClass(Trade.class);
        verify(existingTrade, times(1)).update(tradeArgumentCaptor.capture());
        Trade updatedTrade = tradeArgumentCaptor.getValue();
        assertEquals(tradeId, updatedTrade.getId());
        assertEquals(tradeType, updatedTrade.getType());
        verify(tradeRepository, times(1)).save(existingTrade);

    }



    @Test
    void update_shouldThrowException_whenIdNotFound() {

        // Given new value for existing trade with invalid Id
        final int tradeId = 999;
        Trade trade = new Trade();
        trade.setId(tradeId);

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.empty());

        // When try to update trade , Then a exception thrown
        assertThrows(NotFoundIdException.class, () -> tradeService.update(trade));
        verify(tradeRepository, never()).save(any());

    }



    @Test
    void delete_shouldRemoveTrade_whenIdExists() {

        // Given
        final int tradeId = 1;
        when(tradeRepository.existsById(tradeId)).thenReturn(true);

        // When
        tradeService.delete(tradeId);

        // Then
        verify(tradeRepository, times(1)).deleteById(tradeId);

    }



    @Test
    void delete_shouldThrowException_whenIdNotFound() {

        // Given
        int tradeId = 999;
        when(tradeRepository.existsById(tradeId)).thenReturn(false);

        // When / Then
        assertThrows(NotFoundIdException.class, () -> tradeService.delete(tradeId));
        verify(tradeRepository, never()).deleteById(any());

    }


}
