package com.springapp.mvc.web;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnitReportingRunner.class)

public class HomepageFunctionalityTest extends JUnitStories {

    public HomepageFunctionalityTest() {
        super();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new NavigationSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("Banner.story", "StartDate.story", "ShowVacationDays.story");
    }


}
