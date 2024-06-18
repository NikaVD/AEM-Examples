package com.traineeproject.core.workflows;


import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label" + "= Demo Application - Trainee Workflow Process"
        }

)
public class DemoTraineeWorkflow implements WorkflowProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoTraineeWorkflow.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) throws WorkflowException {
        LOGGER.info("===============START Workflow================");
        WorkflowData workflowData = workItem.getWorkflowData();
//        String type = workflowData.getPayloadType();

//        String[] pathToMove = processArguments.get("pathToMove", new String[]{});
//        for(String path : pathToMove) {
//            LOGGER.info("\n Workflow works: " + path);
//        }

        Session session = workflowSession.adaptTo(Session.class);
        String path = workflowData.getPayload().toString(); // + "/jcr:content";

        ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
        Resource resource = resourceResolver != null ? resourceResolver.getResource(path) : null;


        if (resource != null) {
            Page page = resource.adaptTo(Page.class);
            LOGGER.info("\n Page: " + page.toString());
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            LOGGER.info("\n Page Manager: " + pageManager.toString());
            if (pageManager != null && page != null) {
                if (page.getProperties().get("pathToMove", String.class) != null) {
                    String pathToMove = page.getProperties().get("pathToMove", String.class);
                    LOGGER.info("\n Workflow works: " + pathToMove);
                    movePage(resourceResolver, session, page, pageManager, pathToMove);
                } else {
                    LOGGER.info("Error: pathToMove Properties = null");
                }
            } else {
                LOGGER.info("Error: pageManager = null && page = null");
            }

                } else {
            LOGGER.info("Error: Resource = null");
        }


    }


    private void movePage(ResourceResolver resourceResolver, Session session, Page page, PageManager pageManager, String pathToMove) {
        if (pathToMove != null) {
            if (!pathToMove.startsWith("/")) {
                pathToMove = "/" + pathToMove;
            }
            if (!pathToMove.endsWith("/")) {
                pathToMove += "/";
            }
            if (resourceResolver.getResource(pathToMove) == null) {
                createPathToMove(resourceResolver, session, pathToMove);
            }
            try {
                pageManager.move(page, pathToMove + page.getTitle(), null, false, true, null);
                LOGGER.info("Page " + page.getTitle() + " move to " + pathToMove);
            } catch (WCMException e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("Path to move = null!");
        }
    }

    private void createPathToMove(ResourceResolver resolver, Session session, String pathToMove) {
        String[] folders = pathToMove.split("/");
        StringBuilder path = new StringBuilder();
        for (String folder : folders) {
            path.append(folder);
            path.append("/");
            try {
                JcrUtil.createPath(path.toString(), "sling:Folder", session);
                resolver.commit();
            } catch (RepositoryException | PersistenceException e) {
                e.printStackTrace();
                LOGGER.info(e.toString());
            }
        }
    }
}
