package pzinsta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pzinsta.dto.CustomerRegistration;
import pzinsta.exception.CustomerNotFoundException;
import pzinsta.exception.DeliveryAddressNotFoundException;
import pzinsta.model.Customer;
import pzinsta.model.DeliveryAddress;
import pzinsta.repository.CustomerRepository;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with username %s not found.", username)));
    }

    public Customer register(CustomerRegistration customerRegistration) {
        Customer customer = new Customer();
        customer.setUsername(customerRegistration.getUsername());
        customer.setEmail(customerRegistration.getEmail());
        customer.setFirstName(customerRegistration.getFirstName());
        customer.setLastName(customerRegistration.getLastName());
        customer.setPhoneNumber(customerRegistration.getPhoneNumber());
        return customerRepository.save(customer);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer update(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        return existingCustomer;
    }

    public DeliveryAddress findDeliveryAddressById(Long customerId, Long deliveryAddressId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return customer.getDeliveryAddresses().stream()
                .filter(deliveryAddress -> deliveryAddress.getId().equals(deliveryAddressId))
                .findFirst()
                .orElseThrow(() -> new DeliveryAddressNotFoundException(deliveryAddressId));
    }

    @Transactional
    public DeliveryAddress addDeliveryAddress(Long customerId, DeliveryAddress deliveryAddress) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.getDeliveryAddresses().add(deliveryAddress);
        return deliveryAddress;
    }

    @Transactional
    public DeliveryAddress updateDeliveryAddress(Long customerId, Long deliveryAddressId, DeliveryAddress deliveryAddress) {
        DeliveryAddress deliveryAddressToUpdate = findDeliveryAddress(customerId, deliveryAddressId);
        copyFields(deliveryAddress, deliveryAddressToUpdate);
        return deliveryAddressToUpdate;
    }

    public Customer create(Customer customer) {
        customer.setId(null);
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteDeliveryAddress(Long customerId, Long deliveryAddressId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.getDeliveryAddresses()
                .removeIf(deliveryAddress -> deliveryAddress.getId().equals(deliveryAddressId));
    }

    private DeliveryAddress findDeliveryAddress(Long customerId, Long deliveryAddressId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return customer.getDeliveryAddresses().stream()
                .filter(address -> address.getId().equals(deliveryAddressId))
                .findFirst()
                .orElseThrow(() -> new DeliveryAddressNotFoundException(deliveryAddressId));
    }

    private void copyFields(DeliveryAddress source, DeliveryAddress destination) {
        destination.setApartmentNumber(source.getApartmentNumber());
        destination.setCity(source.getCity());
        destination.setHouseNumber(source.getHouseNumber());
        destination.setStreet(source.getStreet());
    }
}
