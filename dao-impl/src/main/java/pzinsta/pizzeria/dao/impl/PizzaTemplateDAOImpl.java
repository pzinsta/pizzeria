package pzinsta.pizzeria.dao.impl;

import org.springframework.stereotype.Repository;
import pzinsta.pizzeria.dao.PizzaTemplateDAO;
import pzinsta.pizzeria.model.order.PizzaTemplate;

@Repository
public class PizzaTemplateDAOImpl extends GenericDAOImpl<PizzaTemplate, Long> implements PizzaTemplateDAO {

    public PizzaTemplateDAOImpl() {
        super(PizzaTemplate.class);
    }
}
