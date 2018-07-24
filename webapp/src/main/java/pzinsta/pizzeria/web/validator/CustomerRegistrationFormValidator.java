package pzinsta.pizzeria.web.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pzinsta.pizzeria.web.client.CustomerServiceClient;
import pzinsta.pizzeria.web.client.dto.user.Customer;
import pzinsta.pizzeria.web.form.CustomerRegistrationForm;

import java.util.Optional;

@Component
public class CustomerRegistrationFormValidator implements Validator {

    private CustomerServiceClient customerServiceClient;

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerRegistrationForm customerRegistrationForm = (CustomerRegistrationForm) target;
        validateUsername(customerRegistrationForm, errors);
        validatePasswords(customerRegistrationForm, errors);
    }

    private void validateUsername(CustomerRegistrationForm customerRegistrationForm, Errors errors) {
        Optional<Customer> customerOptional = customerServiceClient.findByUsername(customerRegistrationForm.getUsername());

        if (customerOptional.isPresent()) {
            errors.rejectValue("username", "username.already.exists");
        }
    }

    private void validatePasswords(CustomerRegistrationForm customerRegistrationForm, Errors errors) {
        if (!StringUtils.equals(customerRegistrationForm.getPassword(), customerRegistrationForm.getPasswordAgain())) {
            errors.rejectValue("passwordAgain", "passwords.not.equal");
        }
    }

    public CustomerServiceClient getCustomerServiceClient() {
        return customerServiceClient;
    }

    @Autowired
    public void setCustomerServiceClient(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }
}
