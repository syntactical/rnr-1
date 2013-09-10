package stories.view_banner;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.junit.JUnitStories;

public class SimpleJBehave extends JUnitStories {

    public SimpleJBehave() {
        super();
        this.configuredEmbedder().candidateSteps().add(new NavigationSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList("de/codecentric/simplejbehave/Math.story");
    }
}