package com.traineeproject.core.services.impl;

import com.day.cq.replication.ReplicationStatus;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.traineeproject.core.models.impl.DemoSearchPathImpl;
import com.traineeproject.core.services.SearchPathService;
import com.traineeproject.core.services.pojo.File;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.eclipse.jetty.websocket.api.WebSocketPartialListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.day.cq.wcm.api.NameConstants.NT_PAGE;

@Component(service = SearchPathService.class, immediate = true)
public class PdfSearchPathServiceImpl implements SearchPathService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfSearchPathServiceImpl.class);

    @Override
    public List<File> fileList(String path, ResourceResolver resourceResolver) {

        Map<String,String> map = new HashMap<>();
        map.put("path", path);
        map.put("type", "dam:Asset");
        map.put("nodename", "*.pdf");


        Session session = resourceResolver.adaptTo(Session.class);
        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        Query query = null;
        query = queryBuilder.createQuery(PredicateGroup.create(map), session);

        SearchResult result = query.getResult();

        List<File> paths = new ArrayList<>();

        Node node;
        String Status = "Deactivated";
        String WhenActivated = "";
        String WhomActivated = "";



            for (Hit hit : result.getHits()) {

                try {
                        node = hit.getNode();
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
                                hit.getPath(),
                                hit.getTitle(),
                                FileNameUtils.getExtension(hit.getTitle()),
                                Status,
                                WhenActivated,
                                WhomActivated));


                } catch (RepositoryException e) {
                    LOGGER.debug(e.getMessage());
                }
            }

        return paths;

    }
}
