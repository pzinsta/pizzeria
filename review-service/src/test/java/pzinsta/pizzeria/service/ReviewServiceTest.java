package pzinsta.pizzeria.service;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pzinsta.pizzeria.exception.ReviewNotFoundException;
import pzinsta.pizzeria.model.Review;
import pzinsta.pizzeria.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    public static final long REVIEW_ID = 42L;
    public static final long ORDER_ID = 17L;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Captor
    private ArgumentCaptor<Review> reviewArgumentCaptor;

    @Test
    public void shouldFindAllReviews() throws Exception {
        // given
        List<Review> reviews = ImmutableList.of();
        when(reviewRepository.findAll()).thenReturn(reviews);

        // when
        Iterable<Review> result = reviewService.findAll();

        // then
        assertThat(result).isSameAs(reviews);
    }

    @Test
    public void shouldFindAllReviewsForPageable() throws Exception {
        // given
        List<Review> reviews = ImmutableList.of();
        Pageable pageable = PageRequest.of(1, 20);
        Page<Review> page = new PageImpl<>(reviews);
        when(reviewRepository.findAll(pageable)).thenReturn(page);

        // when
        Page<Review> result = reviewService.findAll(pageable);

        // then
        assertThat(result).isSameAs(page);
    }

    @Test
    public void shouldFindById() throws Exception {
        // given
        Review review = new Review();
        when(reviewRepository.findById(REVIEW_ID)).thenReturn(Optional.of(review));

        // when
        Review result = reviewService.findById(REVIEW_ID);

        // then
        assertThat(result).isSameAs(review);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindById() throws Exception {
        // given
        when(reviewRepository.findById(REVIEW_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> reviewService.findById(REVIEW_ID));

        // then
        assertThat(throwable).isInstanceOf(ReviewNotFoundException.class);
    }

    @Test
    public void shouldFindReviewByOrderId() throws Exception {
        // given
        Review review = new Review();
        when(reviewRepository.findByOrderId(ORDER_ID)).thenReturn(Optional.of(review));

        // when
        Review result = reviewService.findByOrderId(ORDER_ID);

        // then
        assertThat(result).isSameAs(review);
    }

    @Test
    public void shouldThrowExceptionWhenCannotFindReviewByOrderId() throws Exception {
        // given
        when(reviewRepository.findByOrderId(ORDER_ID)).thenReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> reviewService.findByOrderId(ORDER_ID));

        // then
        assertThat(throwable).isInstanceOf(ReviewNotFoundException.class);
    }


    @Test
    public void shouldSaveNewReview() throws Exception {
        // given
        Review review = new Review();
        review.setRating(5);
        review.setMessage("could have been better");
        review.setImages(ImmutableList.of(56L, 57L));
        review.setOrderId(ORDER_ID);

        when(reviewRepository.findById(any())).thenReturn(Optional.empty());

        // when
        Review result = reviewService.save(review);

        // then
        verify(reviewRepository).save(reviewArgumentCaptor.capture());
        assertThat(reviewArgumentCaptor.getValue()).isEqualToComparingFieldByField(review);
    }

}