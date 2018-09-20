package com.company.housekeeping.view.wizard;

import com.company.housekeeping.model.Operation;
import com.google.inject.AbstractModule;

public class WizardModule extends AbstractModule {
    @Override
    protected void configure() {
        Operation model = new Operation();
        bind(Operation.class).toInstance(model);
    }
}
