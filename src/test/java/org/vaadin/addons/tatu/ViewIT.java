package org.vaadin.addons.tatu;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.testbench.TestBenchElement;

public class ViewIT extends AbstractViewTest {

    @Test
    public void componentWorks() {
        // Smoketest
        final TestBenchElement progress = $("circular-progress-bar").waitForFirst();
        Assert.assertTrue(
                progress.$(TestBenchElement.class).all().size() > 0);
    }
}
