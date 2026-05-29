package runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")

@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "src/e2e/java/stepdefinitions"
)

@ConfigurationParameter(
        key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty"
)

public class RunCucumberTest {
}