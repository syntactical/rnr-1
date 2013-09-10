package stories.view_banner;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

public class StoryRunner {

    @Test
    public void runClasspathLoadedStoriesAsJUnit() {
        Embedder embedder = new BannerEmbedder();
        List<String> storyPaths = storyPaths();
        embedder.runStoriesAsPaths(storyPaths);
    }

    protected List<String> storyPaths() {
        // Specify story paths as URLs
        String codeLocation = codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findPaths(codeLocation, asList("**/*.story"), asList(""), "file:" + codeLocation);
    }
}