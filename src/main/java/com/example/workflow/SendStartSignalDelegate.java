package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SendStartSignalDelegate implements JavaDelegate {
    final
    RuntimeService runtimeService;

    public SendStartSignalDelegate(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String correlationVariable = "correlation"+ Math.random();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("correlationKey", correlationVariable);


        runtimeService
                .createSignalEvent("sig-start")
                .setVariables(variables)
                .send();

        delegateExecution.setVariable("correlationKey", correlationVariable);



    }
}
