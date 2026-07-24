package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.domain.Customer;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPersistenceController;
import br.edu.ifba.inf008.interfaces.core.IAuthenticationController;

public class AuthenticationController implements IAuthenticationController
{
    private Long customerId = Long.valueOf(1);

    public Customer signIn() {
        IPersistenceController persistenceController = ICore.getInstance().getPersistenceController();
        Customer customer = persistenceController.findById(Customer.class, customerId);

        if (customer == null) {
            throw new IllegalStateException(
                "Client with ID " + customerId + " not found."
            );
        }

        return customer;
    }
}
