package com.traineeproject.core.services.impl;

import com.traineeproject.core.services.VersioningPageService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionManager;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component(immediate = true, service = VersioningPageService.class)
public class VersioningPageServiceImpl implements VersioningPageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersioningPageServiceImpl.class);


    @Override
    public void versioningPage(Node parentNode, Session session) throws RepositoryException {
        LOGGER.info("Start");
        StringBuffer stringBuffer = new StringBuffer();
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ssZ");
        String childNode = "childNode" + "_" + simpleDateFormat.format(date, stringBuffer, new FieldPosition(0));

        VersionManager versionManager = session.getWorkspace().getVersionManager();

        // create versionable node
        Node versionableNode = parentNode.addNode(childNode, "nt:unstructured");
        versionableNode.addMixin("mix:versionable");
//        versionableNode.setProperty("property", "foo");
        session.save();
        Version firstVersion = versionManager.checkin(versionableNode.getPath());
        LOGGER.info(String.format("Create versionable node. Version id: %s, property: %s", versionManager.getBaseVersion(versionableNode.getPath())
                .getIdentifier(), versionableNode.getProperty("property").getString()));


//        // modify the node
//        versionManager.checkout(versionableNode.getPath());
//        versionableNode.setProperty("property", "bar");
//        session.save();
//        versionManager.checkin(versionableNode.getPath());
//        LOGGER.info(String.format("Modify the node. Version id: %s, property: %s", versionManager.getBaseVersion(versionableNode.getPath())
//                .getIdentifier(), versionableNode.getProperty("property").getString()));
//
//        // restore first version
//        versionManager.restore(firstVersion, true);
//        LOGGER.info(String.format("Restore first version. Version id: %s, property: %s", versionManager.getBaseVersion(versionableNode.getPath())
//                .getIdentifier(), versionableNode.getProperty("property").getString()));
    }

}
