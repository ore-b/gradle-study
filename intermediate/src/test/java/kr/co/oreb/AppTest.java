package kr.co.oreb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void AppTest() {
        App app = new App();
        Assertions.assertNotNull(app);
    }
}