package com.traineeproject.core.listeners;

import com.traineeproject.core.services.VersioningPageService;
import com.traineeproject.core.utils.ResolverUtil;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Objects;

@Component(service = EventHandler.class,
        immediate = true,
        property = {
                Constants.SERVICE_DESCRIPTION + "= This event handler listens will be triggered if any page will be modified under specified path",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_FILTER + "=(path=/content/we-retail/us/en/*)"
//                            + "/*/jcr:content) (|("
//                            + SlingConstants.PROPERTY_CHANGED_ATTRIBUTES + "=*jcr:title) " + "(" + ResourceChangeListener.CHANGES
//                            + "=*jcr:title)))"
        })
public class ModifiedPageEventHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModifiedPageEventHandler.class);

    @Reference
    VersioningPageService versioningPageService;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    private Session session;

    @Override
    public void handleEvent(Event event) {

        LOGGER.info("Start EventHandler");
        try {
            ResourceResolver resolver = ResolverUtil.newResolver(resourceResolverFactory);

            session = resolver.adaptTo(Session.class);

            if(!Objects.requireNonNull(session).getUserID().isEmpty()) {
                LOGGER.info("Session was created for user: " + Objects.requireNonNull(session).getUserID());

//                Node parentNode = session.getNode(event.getProperty("path").toString().split("/jcr:content/")[0]);
                Node parentNode = session.getNode(event.getProperty("path").toString());

                versioningPageService.versioningPage(parentNode, session);

                session.save();
                session.logout();
            } LOGGER.error("User doesn't exist");


        }catch (Exception e) {
            LOGGER.info("\n Error while Activating/Deactivating - {} ", e.getMessage());
        }

    }

}
