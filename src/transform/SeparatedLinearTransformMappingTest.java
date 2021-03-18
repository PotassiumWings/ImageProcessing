package transform;

import org.junit.Test;

public class SeparatedLinearTransformMappingTest {
    @Test
    public void linearTransformMapping() {
        assert(SeparatedLinearTransformMapping.linearTransformMapping(0, 255, 0, 255, 0) == 0);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(0, 255, 0, 255, 255) == 255);

        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 50) == 100);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 0) == 0);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 100) == 200);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 255) == 255);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 100, 200, 75) == 150);

        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 0) == 0);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 50) == 0);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 255) == 255);
        assert(SeparatedLinearTransformMapping.linearTransformMapping(50, 100, 0, 255, 100) == 255);
    }
}