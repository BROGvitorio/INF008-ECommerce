package br.edu.ifba.inf008.interfaces.core;

import br.edu.ifba.inf008.domain.Customer;

public interface IAuthenticationController
{
    public abstract Customer signIn();
}
