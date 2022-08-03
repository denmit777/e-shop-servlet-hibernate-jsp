package service;

import java.math.BigDecimal;

public interface OrderService {

    void save(String login, BigDecimal totalPrice);

    String orderResult(BigDecimal totalPrice);
}

