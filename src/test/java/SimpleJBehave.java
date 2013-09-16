
import java.util.Arrays;
import java.util.List;

import com.springapp.mvc.web.NavigationSteps;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class SimpleJBehave extends JUnitStories {

    public SimpleJBehave() {
        super();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new NavigationSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("Banner.story");
    }
}