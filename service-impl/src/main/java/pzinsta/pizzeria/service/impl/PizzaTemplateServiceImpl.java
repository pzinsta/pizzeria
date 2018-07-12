package pzinsta.pizzeria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pzinsta.pizzeria.dao.PizzaTemplateDAO;
import pzinsta.pizzeria.model.order.PizzaTemplate;
import pzinsta.pizzeria.service.PizzaTemplateService;

import java.util.Collection;

@Service
public class PizzaTemplateServiceImpl implements PizzaTemplateService {

    private PizzaTemplateDAO pizzaTemplateDAO;

    @Autowired
    public PizzaTemplateServiceImpl(PizzaTemplateDAO pizzaTemplateDAO) {
        this.pizzaTemplateDAO = pizzaTemplateDAO;
    }

    @Override
    public Collection<PizzaTemplate> getOrderItemTemplates() {
        return pizzaTemplateDAO.findAll();
    }
}
