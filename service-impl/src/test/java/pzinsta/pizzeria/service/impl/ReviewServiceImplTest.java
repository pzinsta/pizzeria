package pzinsta.pizzeria.service.impl;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pzinsta.pizzeria.dao.ReviewDAO;
import pzinsta.pizzeria.model.order.Review;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ReviewDAO reviewDAO;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void shouldGetReviews() throws Exception {
        // given
        List<Review> reviews = ImmutableList.of();
        when(reviewDAO.findAll()).thenReturn(reviews);

        // when
        List<Review> result = reviewService.getReviews();

        // then
        assertThat(result).isSameAs(reviews);
    }

    @Test
    public void shouldGetReviewsWithinRange() throws Exception {
        // given
        int offset = 10;
        int limit = 5;
        List<Review> reviews = ImmutableList.of();
        when(reviewDAO.findWithinRange(offset, limit)).thenReturn(reviews);

        // when
        List<Review> result = reviewService.getReviews(offset, limit);

        // then
        assertThat(result).isSameAs(reviews);
    }

    @Test
    public void shouldGetCount() throws Exception {
        // given
        long count = 42L;
        when(reviewDAO.getCount()).thenReturn(count);

        // when
        long result = reviewService.getTotalCount();

        // then
        assertThat(result).isEqualTo(count);
    }
}