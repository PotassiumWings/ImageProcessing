package main.transform;

import org.junit.Test;

public class LinearTransformMappingTest {

    @Test
    public void linearTransformMapping() {
        assert (LinearTransformMapping.linearTransformMapping(0, 255, 0, 255, 0) == 0);
        assert (LinearTransformMapping.linearTransformMapping(0, 255, 0, 255, 255) == 255);
        assert (LinearTransformMapping.linearTransformMapping(1, 255, 0, 255, 0) == 0);
        assert (LinearTransformMapping.linearTransformMapping(1, 255, 0, 255, 255) == 255);

        assert (LinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 50) == 100);
        assert (LinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 0) == 100);
        assert (LinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 100) == 200);
        assert (LinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 255) == 200);
        assert (LinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 75) == 150);

        assert (LinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 0) == 0);
        assert (LinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 50) == 0);
        assert (LinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 255) == 255);
        assert (LinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 100) == 255);
    }
}