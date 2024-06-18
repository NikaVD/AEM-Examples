package com.traineeproject.core.services;

import org.apache.sling.api.resource.ResourceResolver;
import com.traineeproject.core.services.pojo.File;

import javax.jcr.RepositoryException;
import java.util.List;

public interface SearchPathService {
//    List<String>fileList(String path, ResourceResolver resourceResolver);
    List<File>fileList(String path, ResourceResolver resourceResolver);
}
