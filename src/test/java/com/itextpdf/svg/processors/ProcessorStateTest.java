package com.itextpdf.svg.processors;

import com.itextpdf.svg.dummy.renderers.impl.DummySvgNodeRenderer;
import com.itextpdf.svg.processors.impl.ProcessorState;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.test.annotations.type.UnitTest;

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category( UnitTest.class )
public class ProcessorStateTest {

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    /**
     * Push test
     */
    @Test
    public void processorStateTestPush() {
        ProcessorState testProcessorState = new ProcessorState();
        ISvgNodeRenderer renderer = new DummySvgNodeRenderer("test");
        testProcessorState.push(renderer);

        Assert.assertTrue(testProcessorState.size() == 1);
    }

    /**
     * Pop test
     */
    @Test
    public void processorStateTestPop() {
        ProcessorState testProcessorState = new ProcessorState();
        ISvgNodeRenderer renderer = new DummySvgNodeRenderer("test");
        testProcessorState.push(renderer);

        ISvgNodeRenderer popped = testProcessorState.pop();
        Assert.assertTrue(popped.toString().equals("test") && testProcessorState.empty());
    }

    /**
     * Peek test
     */
    @Test
    public void processorStateTestPeek() {
        ProcessorState testProcessorState = new ProcessorState();
        ISvgNodeRenderer renderer = new DummySvgNodeRenderer("test");
        testProcessorState.push(renderer);

        ISvgNodeRenderer viewed = testProcessorState.top();
        Assert.assertTrue(viewed.toString().equals("test") && ! testProcessorState.empty());

    }

    /**
     * Multiple push test
     */
    @Test
    public void processorStateTestMultiplePushesPopAndPeek() {
        ProcessorState testProcessorState = new ProcessorState();
        ISvgNodeRenderer rendererOne = new DummySvgNodeRenderer("test01");
        testProcessorState.push(rendererOne);
        ISvgNodeRenderer rendererTwo = new DummySvgNodeRenderer("test02");
        testProcessorState.push(rendererTwo);

        ISvgNodeRenderer popped = testProcessorState.pop();
        boolean result = popped.toString().equals("test02");
        result = result && testProcessorState.top().toString().equals("test01");
        Assert.assertTrue(result);
    }

    @Test
    public void processorStateTestPopEmpty() {
        junitExpectedException.expect(EmptyStackException.class);
        ProcessorState testProcessorState = new ProcessorState();

        testProcessorState.pop();
    }

    @Test
    public void processorStateTestPushSameElementTwice() {
        ProcessorState testProcessorState = new ProcessorState();
        ISvgNodeRenderer rendererOne = new DummySvgNodeRenderer("test01");
        testProcessorState.push(rendererOne);
        testProcessorState.push(rendererOne);

        ISvgNodeRenderer popped = testProcessorState.pop();
        boolean result = popped.toString().equals("test01");
        result = result && testProcessorState.top().toString().equals("test01");
        Assert.assertTrue(result);
    }


    @Test
    public void processorStateTestPeekEmpty() {
        junitExpectedException.expect(EmptyStackException.class);
        ProcessorState testProcessorState = new ProcessorState();
        testProcessorState.pop();
    }


}
