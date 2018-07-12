package pzinsta.pizzeria.service;

import pzinsta.pizzeria.model.order.PizzaTemplate;

import java.util.Collection;

public interface PizzaTemplateService {
    Collection<PizzaTemplate> getOrderItemTemplates();
}
