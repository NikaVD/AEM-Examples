package com.traineeproject.core.services.impl;

import com.day.cq.wcm.api.Page;
import com.google.gson.*;
import com.traineeproject.core.services.DemoServletService;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@Component
public class DemoServletServiceImpl implements DemoServletService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoServletServiceImpl.class);

    @Override
    public String getJsonFile(Resource mainPath) {

//        if (mainPath != null) {


            List<DTO> pageList = new ArrayList<>();
            Iterator<Resource> listChildren = mainPath.listChildren();
            Resource childrenPageResource;

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            while (listChildren.hasNext()) {
                childrenPageResource = listChildren.next();
                Page childrenPage = childrenPageResource.adaptTo(Page.class);
//                Map<String, String> pageDetails = new HashMap<String, String>();


                if (childrenPage != null) {

                    DTO dto = new DTO(childrenPage.getPath(), childrenPage.getName(), childrenPage.getTemplate().getTitle());

                    pageList.add(dto);

                }


            }

            LOGGER.debug(gson.toJson(pageList));
            return gson.toJson(pageList);
//        } else {
//            return null;
//        }

    }

    private static class DTO {
        private String path;
        private String title;
        private String template;

        public DTO(String path, String title, String template) {
            this.path = path;
            this.title = title;
            this.template = template;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }
    }

}
