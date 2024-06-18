package com.traineeproject.core.models.impl;

import com.traineeproject.core.models.Canvas;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class,
        adapters = Canvas.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CanvasImpl implements Canvas {
    String defaultInfo = "Lorem ipsum dolor sit amet. Ad consequatur dolor vel enim rerum et incidunt culpa ut debitis facilis ut atque nisi est vero deleniti. Et voluptatem doloribus est praesentium porro id asperiores internos non impedit galisum et voluptatibus obcaecati sit architecto nesciunt et minima odio.\n" +
            "Ea laborum doloremque rem quibusdam omnis est repellat quia aut incidunt sequi. Ut soluta tempora aut aperiam voluptatem quo quia odit ut laudantium animi eum voluptate sunt in sunt odio qui nesciunt modi.\n" +
            "Hic molestiae rerum aut earum galisum et magnam consectetur! Sit totam rerum aut ipsam quos ex omnis pariatur est mollitia recusandae ea consequatur perferendis non officiis commodi sed sunt nesciunt. Est tempore doloremque qui saepe debitis et vero beatae et sequi numquam sed velit laudantium ea voluptatem cumque.";

    @Inject
    @Default(values = "CanvasLogic CL 900 Pro Barista")
    String name;

    @Inject
    @Default(values = "https://storage.googleapis.com/clp-basic-configurator-dev/index.html#/configurator/coffee-machine/cl_500?token=salesforce")
    String url;

    @Inject
    @Default(values = "Lorem ipsum dolor sit amet. Ad consequatur dolor vel enim rerum et incidunt culpa ut debitis facilis ut atque nisi est vero deleniti. Et voluptatem doloribus est praesentium porro id asperiores internos non impedit galisum et voluptatibus obcaecati sit architecto nesciunt et minima odio.\n" +
            "Ea laborum doloremque rem quibusdam omnis est repellat quia aut incidunt sequi. Ut soluta tempora aut aperiam voluptatem quo quia odit ut laudantium animi eum voluptate sunt in sunt odio qui nesciunt modi.\n" +
            "Hic molestiae rerum aut earum galisum et magnam consectetur! Sit totam rerum aut ipsam quos ex omnis pariatur est mollitia recusandae ea consequatur perferendis non officiis commodi sed sunt nesciunt. Est tempore doloremque qui saepe debitis et vero beatae et sequi numquam sed velit laudantium ea voluptatem cumque.")
    String information;

    @Inject
    @Default(values = "false")
    Boolean isHalfFrame;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getInformation() {
        return information;
    }

    @Override
    public boolean getIsHalfFrame() {
        return isHalfFrame;
    }

    @Override
    public String getFrame() {
        String frame = "1000px";
        if (isHalfFrame.equals(true)) {
            frame = "500px";
        }
        return frame;
    }
}
