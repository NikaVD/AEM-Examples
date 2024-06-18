package com.traineeproject.core.services;

import org.apache.sling.api.resource.Resource;
import org.json.JSONException;

public interface DemoServletService {
    String getJsonFile(Resource mainPath) throws JSONException;
}
