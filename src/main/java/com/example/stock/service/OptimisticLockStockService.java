package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockStockService {

    private StockRepository stockRepository;

    public OptimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void decrease(Long id , Long quantity) {
        Stock s = stockRepository.findByIdWithOptimistickLock(id);
        s.decrease(quantity);
        stockRepository.saveAndFlush(s);
    }
}
