package pzinsta.strategy;

import pzinsta.model.Order;

public interface TrackingNumberGenerationStrategy {
    String generateTrackingNumber(Order order);
}
