package org.vaadin.addons.tatu;

import org.junit.Assert;
import org.junit.Test;

public class CircularProgressBarTest {

    @Test
    public void scale() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setScale(0.5);
        Assert.assertEquals(0.5,
                progress.getElement().getProperty("scale", 0.0), 0.01);
    }

    @Test
    public void percent() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setPercent(0.5);
        Assert.assertEquals(0.5,
                progress.getElement().getProperty("percent", 0.0), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void percentNegativeThrows() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setPercent(-1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void percentTooLargeThrows() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setPercent(1.1);
    }

    @Test
    public void caption() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setCaption("caption");
        Assert.assertEquals("caption",
                progress.getElement().getProperty("label"));
    }

    @Test
    public void color() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setColor("red");
        Assert.assertEquals("red",
                progress.getElement().getStyle().get("--lumo-primary-color"));
    }

    @Test
    public void delay() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setDelay(100);
        Assert.assertEquals(100, progress.getElement().getProperty("delay", 0));
    }

    @Test
    public void animation() {
        CircularProgressBar progress = new CircularProgressBar();
        progress.setAnimation(true);
        Assert.assertTrue(
                progress.getElement().getProperty("animation", false));
    }

    @Test
    public void border() {
        CircularProgressBar progress = new CircularProgressBar();
        Assert.assertEquals("",
                progress.getElement().getAttribute("noborder"));
        progress.setBorder(true);
        Assert.assertEquals(null,
                progress.getElement().getAttribute("noborder"));
    }
}
