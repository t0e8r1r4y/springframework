package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        // tranaction을 사용하면 영속성 context에 데이터를 넣어두고 DB에 쓰기 까지 Gap이 생기면서 원하는 결과 처리가 안된다.
        // java에서 제공하는 synchronized는 각 프로세스 안에서만 보장이 가능함
        // 그러나 여러 서버에서 동시에 접근하게 되면 race condition은 여전히 발생하게 됨
        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
