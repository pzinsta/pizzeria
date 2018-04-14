package pzinsta.pizzeria.dao.impl;

import org.springframework.stereotype.Repository;
import pzinsta.pizzeria.dao.ReviewDAO;
import pzinsta.pizzeria.model.order.Review;

@Repository
public class ReviewDAOImpl extends GenericDAOImpl<Review, Long> implements ReviewDAO {

    public ReviewDAOImpl() {
        super(Review.class);
    }
}
