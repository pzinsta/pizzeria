package pzinsta.pizzeria.model;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Set<UserRole> roles;
	
	public User() {
		roles = ImmutableSet.of(UserRole.UNREGISTERED_CUSTOMER);
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
	
	public boolean hasRole(String role) {
		return roles.contains(UserRole.valueOf(role));
	}
}
