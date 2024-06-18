package com.traineeproject.core.services.impl;

import com.day.cq.replication.ReplicationStatus;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.traineeproject.core.services.SearchPathService;
import com.traineeproject.core.services.pojo.File;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SearchPathService.class, immediate = true)
public class AssetsSearchPathServiceImpl implements SearchPathService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsSearchPathServiceImpl.class);

    @Override
    public List<File> fileList(String path, ResourceResolver resourceResolver) {

        Session session = resourceResolver.adaptTo(Session.class);

        String selectString = "SELECT * FROM [dam:Asset] AS node\n" +
                "WHERE ISDESCENDANTNODE(node, \"" + path + "\")\n" +
                "AND CONTAINS (node.*, '\".jpg\"')\n" +
                "OR CONTAINS (node.*, '\".png\"')";

        Workspace workspace = session.getWorkspace();
        QueryManager queryManager = null;
        NodeIterator nodeIterator = null;

        try {
            queryManager = workspace.getQueryManager();
            Query query = queryManager.createQuery(selectString, Query.JCR_SQL2);
            QueryResult queryResult = query.execute();
            nodeIterator = queryResult.getNodes();


        } catch (RepositoryException e) {
            LOGGER.debug(e.getMessage());
        }


        List<File> paths = new ArrayList<>();


        String Status = "Deactivated";
        String WhenActivated = "";
        String WhomActivated = "";


        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.nextNode();
            try {
                LOGGER.info(" Info: " + node.getName());
            } catch (RepositoryException e) {
                LOGGER.debug(e.getMessage());
            }
            try {
                if (node.hasProperty(ReplicationStatus.NODE_PROPERTY_LAST_REPLICATION_ACTION)) {
                    Status = node.getProperty(ReplicationStatus.NODE_PROPERTY_LAST_REPLICATION_ACTION).getString();
                    if (node.hasProperty(ReplicationStatus.NODE_PROPERTY_LAST_REPLICATED_BY)) {
                        WhomActivated = node.getProperty(ReplicationStatus.NODE_PROPERTY_LAST_REPLICATED_BY).getString();
                    }
                    if (node.hasProperty(ReplicationStatus.NODE_PROPERTY_LAST_REPLICATED)) {
                        WhenActivated = node.getProperty(ReplicationStatus.NODE_PROPERTY_LAST_REPLICATED).getString();
                    }
                }

                paths.add(new File(
                        node.getPath(),
                        node.getName(),
                        FileNameUtils.getExtension(node.getName()),
                        Status,
                        WhenActivated,
                        WhomActivated
                ));


            } catch (RepositoryException e) {
                LOGGER.debug(e.getMessage());
            }
        }

        return paths;

    }
}
