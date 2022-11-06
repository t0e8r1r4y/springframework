package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassimistickLockStockService {

    private StockRepository stockRepository;

    public PassimistickLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock s = stockRepository.findByIdWithPassmistickLock(id);

        s.decrease(quantity);

        stockRepository.saveAndFlush(s);
    }
}
