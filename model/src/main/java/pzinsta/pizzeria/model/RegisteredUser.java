package pzinsta.pizzeria.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "registered_user")
@PrimaryKeyJoinColumn(name = "user_id")
public class RegisteredUser extends User {
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    @CreationTimestamp
    private Instant registrationDate;
    
    @NotNull
    private String hashedPassword;

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }
}
