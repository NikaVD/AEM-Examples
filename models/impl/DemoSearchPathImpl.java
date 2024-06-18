package com.traineeproject.core.models.impl;

import com.drew.lang.annotations.Nullable;
import com.traineeproject.core.models.DemoSearchPathService;
import com.traineeproject.core.services.SearchPathService;
import com.traineeproject.core.services.impl.AssetsSearchPathServiceImpl;
import com.traineeproject.core.services.impl.PdfSearchPathServiceImpl;
import com.traineeproject.core.services.pojo.File;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = DemoSearchPathService.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DemoSearchPathImpl implements DemoSearchPathService {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE)
    @Default(values="/content/traineeproject/us/en")
    protected String resourceType;

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoSearchPathImpl.class);

    @Inject
    @Via("resource")
    @Default(values = "/content/traineeproject/us")
    String path;

    @Inject
    @Via("resource")
    @Default(values = "pdf")
    String assets;

    @ValueMapValue
    private List<File> listFiles;


    @Reference
    private SearchPathService searchPathService;


    @Self
    private SlingHttpServletRequest slingHttpServletRequest;

    ResourceResolver resourceResolver;

    @PostConstruct
    public void init() {
        resourceResolver = slingHttpServletRequest.getResourceResolver();
        String extension = getAssets().toLowerCase();
        LOGGER.info(extension);

        if(extension.equals("pdf")) {
            searchPathService = new PdfSearchPathServiceImpl();

            LOGGER.info(path);

            listFiles = searchPathService.fileList(path, resourceResolver);

        } if(extension.equals("image")) {
            searchPathService = new AssetsSearchPathServiceImpl();

            LOGGER.info(path);

            listFiles = searchPathService.fileList(path, resourceResolver);
        }

    }


    @Override
    public String getPath() {
        return StringUtils.isNotBlank(this.path) ? this.path : null;
    }

    @Override
    public String getAssets() {
        return assets;
    }

    @Nullable
    @Override
    public List<File> getListFiles() {
        LOGGER.info("list: " + listFiles.size());
        return listFiles.isEmpty() ? null : listFiles;


    }
}
