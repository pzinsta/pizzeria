package pzinsta.pizzeria.service.impl.strategy;

import pzinsta.pizzeria.model.order.Order;

public interface TrackNumberGenerationStrategy {
    String generateTrackNumber(Order order);
}
