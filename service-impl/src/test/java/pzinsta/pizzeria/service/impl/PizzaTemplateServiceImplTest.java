package pzinsta.pizzeria.service.impl;

import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.pizzeria.dao.PizzaTemplateDAO;
import pzinsta.pizzeria.model.order.PizzaTemplate;

import java.util.Collection;

public class PizzaTemplateServiceImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private PizzaTemplateDAO pizzaTemplateDAO;

    @InjectMocks
    private PizzaTemplateServiceImpl orderItemTemplateService;

    @Test
    public void shouldGetOrderItemTemplates() throws Exception {
        // given
        ImmutableList<PizzaTemplate> pizzaTemplates = ImmutableList.of();
        Mockito.when(pizzaTemplateDAO.findAll()).thenReturn(pizzaTemplates);

        // when
        Collection<PizzaTemplate> result = orderItemTemplateService.getOrderItemTemplates();

        // then
        Assertions.assertThat(result).isSameAs(pizzaTemplates);
    }
}