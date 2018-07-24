package pzinsta.pizzeria.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id;

    @NotNull
   	private Long orderId;

    @CreationTimestamp
	private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;
	
    @Size(max = 1000)
    @NotNull
	private String message;

    @Min(1)
    @Max(10)
	private int rating;

    @Fetch(FetchMode.SUBSELECT)
	@ElementCollection(fetch = FetchType.EAGER)
	private Collection<Long> images = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Instant getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Instant lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Collection<Long> getImages() {
		return images;
	}

	public void setImages(Collection<Long> images) {
		this.images = images;
	}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
