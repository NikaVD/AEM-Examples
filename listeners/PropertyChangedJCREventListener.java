package com.traineeproject.core.listeners;

import com.traineeproject.core.utils.ResolverUtil;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component(immediate = true, service = EventListener.class)
public class PropertyChangedJCREventListener implements EventListener{

    public static final String EVENT_FOLDER = "/content/we-retail/";
    public static final String JOB_FOLDER = "trainee/job";

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyChangedJCREventListener.class);

    private Session session;

    private ResourceResolver resourceResolver;

    @Reference
    private SlingRepository slingRepository;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    JobManager jobManager;

    @Activate
    public void activate() {
        LOGGER.info("Activated");
        try {
            resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            session = resourceResolver.adaptTo(Session.class);

            if(!Objects.requireNonNull(session).getUserID().isEmpty()) {

                session.getWorkspace().getObservationManager().addEventListener(
                        this,
                        Event.PROPERTY_CHANGED,
                        EVENT_FOLDER,
                        true,
                        null,
                        null,
                        false
                );
                LOGGER.info("Session was created for user: " + Objects.requireNonNull(session).getUserID());
            } else {
                LOGGER.error("User doesn't exist");
            }


        }catch (Exception e) {
            LOGGER.error("\n Error while activating : {}", e.getMessage());
        }

    }

    @Deactivate
    protected void deactivate() {
        if(session != null) {
            session.logout();
            LOGGER.info("Deactivated");
        }
    }

    @Override
    public void onEvent(EventIterator eventIterator) {
        LOGGER.info("EventIterator");
        try{
            Map<String, Object> jobProperties = new HashMap<>();

            LOGGER.info(" new Map created: " + jobProperties.isEmpty());

            while(eventIterator.hasNext()) {

                jobProperties.put("Path", eventIterator.nextEvent().getPath());
                jobProperties.put("Node", eventIterator.nextEvent().getIdentifier());

                LOGGER.info("Map: " + jobProperties.keySet());

                Job job = jobManager.addJob(JOB_FOLDER, jobProperties);
                LOGGER.info("job: " + job.getTopic() + " was added: " + job.getCreated().getTime() + "_prop1: " + job.getProperty("Path") + "_prop2: " + job.getProperty("Node"));

            }


        } catch (Exception e) {
            LOGGER.error("\n Error while processing events : {}", e.getMessage());
        }


    }
}
