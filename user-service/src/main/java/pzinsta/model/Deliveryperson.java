package pzinsta.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Deliveryperson extends User implements Serializable {

	private double latitude;
	private double longitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
