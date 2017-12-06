package pzinsta.pizzeria.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pzinsta.pizzeria.dao.OrderDao;
import pzinsta.pizzeria.dao.PizzaDao;
import pzinsta.pizzeria.dao.UserDao;
import pzinsta.pizzeria.dao.impl.OrderDaoImpl;
import pzinsta.pizzeria.dao.impl.PizzaDaoImpl;
import pzinsta.pizzeria.dao.impl.UserDaoImpl;
import pzinsta.pizzeria.service.OrderService;
import pzinsta.pizzeria.service.PizzaService;
import pzinsta.pizzeria.service.UserService;
import pzinsta.pizzeria.service.impl.OrderServiceImpl;
import pzinsta.pizzeria.service.impl.PizzaServiceImpl;
import pzinsta.pizzeria.service.impl.SignUpValidationServiceImpl;
import pzinsta.pizzeria.service.impl.UserServiceImpl;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        PizzaDao pizzaDao = new PizzaDaoImpl();
        UserDao userDao = new UserDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();

        PizzaService pizzaService = new PizzaServiceImpl(pizzaDao);
        UserService userService = new UserServiceImpl(userDao);
        OrderService orderService = new OrderServiceImpl(orderDao, userService, pizzaService);
        SignUpValidationServiceImpl signUpValidationService = new SignUpValidationServiceImpl(userDao);
        
        servletContext.setAttribute("pizzaService", pizzaService);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("orderService", orderService);
        servletContext.setAttribute("signUpValidationService", signUpValidationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
