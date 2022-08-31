package be.shop.slow_delivery.stock.application;

import be.shop.slow_delivery.common.domain.Quantity;
import be.shop.slow_delivery.stock.domain.Stock;
import be.shop.slow_delivery.stock.domain.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class StockCommandServiceTest {
    @Mock private StockRepository stockRepository;
    @InjectMocks private StockCommandService stockCommandService;

    @Test @DisplayName("새로운 재고 저장")
    void create() throws Exception{
        //given
        Quantity quantity = new Quantity(50);
        doAnswer((invocation) -> {
            Stock argument = (Stock) invocation.getArgument(0);
            ReflectionTestUtils.setField(argument, "id", 1L);
            return argument;
        }).when(stockRepository).save(any(Stock.class));

        //when
        long stockId = stockCommandService.create(quantity);

        //then
        assertThat(stockId).isGreaterThan(0L);
    }
}