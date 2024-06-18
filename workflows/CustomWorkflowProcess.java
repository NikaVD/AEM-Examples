package com.traineeproject.core.workflows;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = WorkflowProcess.class,
            immediate = true,
            property = {Constants.SERVICE_VENDOR + "=UBERDIGITECH",
            "process.label = My custom workflow process"
            })
public class CustomWorkflowProcess implements WorkflowProcess {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TYPE_JCR_PATH = "JCR_PATH";

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {

        WorkflowData workflowData = workItem.getWorkflowData();
        if(workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
            String path = workflowData.getPayload().toString() + "/jcr:content";
        }
        logger.info("Argument : {}", metaDataMap.get("PROCESS_ARGS", String.class));
        logger.info("CustomWorkflowProcess step called");

    }
}
