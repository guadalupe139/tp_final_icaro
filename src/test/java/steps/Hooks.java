package steps;

import base.BaseTest;
import io.cucumber.java.Before;
import io.cucumber.java.After;

public class Hooks {

    @Before
    public void setUpScenario() throws Exception {
        BaseTest.setup();
    }

    @After
    public void tearDownScenario() {
        BaseTest.tearDown(); // cierra el driver
    }

}