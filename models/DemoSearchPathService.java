package com.traineeproject.core.models;


import com.day.cq.wcm.api.Page;
import com.traineeproject.core.services.pojo.File;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import java.util.List;

public interface DemoSearchPathService {
    List<File> getListFiles();

    String getPath();
    String getAssets();


}
