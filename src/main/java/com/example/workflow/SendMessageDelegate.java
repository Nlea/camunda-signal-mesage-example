package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMessageDelegate implements JavaDelegate {
    final
    RuntimeService runtimeService;

    public SendMessageDelegate(RuntimeService runtimeService){
        this.runtimeService= runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String correlationKey = (String) delegateExecution.getVariable("correlationKey");

        String messageName = "msg_" + delegateExecution.getProcessEngineServices().getRepositoryService().getProcessDefinition(delegateExecution.getProcessDefinitionId()).getKey();
        System.out.println("Das ist the message: "+ messageName);

        runtimeService
                .createMessageCorrelation(messageName)
                .processInstanceVariableEquals("correlationKey", correlationKey)
                .correlate();




    }
}
