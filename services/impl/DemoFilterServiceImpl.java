package com.traineeproject.core.services.impl;

import com.traineeproject.core.services.DemoFilterService;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(service = DemoFilterService.class)
@Designate(ocd = DemoFilterServiceImpl.Config.class)
public class DemoFilterServiceImpl implements DemoFilterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoFilterServiceImpl.class);
    private static final String replaceWord = "Men";



    @Reference
    private SlingSettingsService slingSettingsService;

    @ObjectClassDefinition(
            name = "DemoFilter",
            description = "Task1, Create Sling Filter." + "\n" +
            "This configuration sets the word to be places instead of 'men' "
    )
    @interface Config {
        @AttributeDefinition(name = "Word to be set: ", description = "Set the word to be places instead of 'men'")
        String newWord();

    }

    @Activate
    private Config filterConfig;

    @Override
    public void replace(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);

        try {
            filterChain.doFilter(request, wrapper);
            response.getWriter().write(wrapper.toString().replace(replaceWord, filterConfig.newWord()));
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            LOGGER.debug(e.getMessage());
        }

    }
}
