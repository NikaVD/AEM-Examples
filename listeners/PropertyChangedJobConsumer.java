package com.traineeproject.core.listeners;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.traineeproject.core.utils.ResolverUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component(service = JobConsumer.class,
            immediate = true,
            property = {JobConsumer.PROPERTY_TOPICS + "=" + PropertyChangedJCREventListener.JOB_FOLDER
            })
public class PropertyChangedJobConsumer implements JobConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyChangedJobConsumer.class);

    public static final String NODE_LOCATION_PATH = "/var/log/editedNodes";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    private Session session;



    @Override
    public JobResult process(Job job) {
        if(job != null) {
            LOGGER.info("Start Job Consumer");

            try {
                ResourceResolver resolver = ResolverUtil.newResolver(resourceResolverFactory);

                session = resolver.adaptTo(Session.class);
                LOGGER.info("create session");

                if (session != null) {
                    Resource resource = resolver.getResource(NODE_LOCATION_PATH);
                    if (resource == null) {
                        JcrUtil.createPath(NODE_LOCATION_PATH, "sling:Folder", session);
                        LOGGER.info("New Folder - " + NODE_LOCATION_PATH + " was created");
                    } else {
                        LOGGER.info("Folder - " + resolver.getResource(NODE_LOCATION_PATH).getPath() + " already exist");
                    }

                    createNode(job, session);
                    LOGGER.info("Node was created");
                }

                return JobResult.OK;

            } catch (Exception e) {
                LOGGER.info("\n Error in Job Consumer : {}", e.getMessage());
                return JobResult.FAILED;
            }
        }
        return JobResult.OK;
    }

    private void createNode(Job job, Session session) throws RepositoryException {
        StringBuffer stringBuffer = new StringBuffer();
        Date date = job.getCreated().getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ssZ");

        String nodePath = job.getProperty("Path").toString();
        String nodeName = job.getProperty("Node").toString().split("/")[0] + "_" + simpleDateFormat.format(date, stringBuffer, new FieldPosition(0));

        //Create the Node
        Node node = JcrUtil.createPath(NODE_LOCATION_PATH + "/" + nodeName, JcrConstants.NT_UNSTRUCTURED, session);

        //Set the required properties
        node.setProperty("nodePath", nodePath);
        node.setProperty("nodeName", nodeName);

        //Save the session
        session.save();
    }
}
