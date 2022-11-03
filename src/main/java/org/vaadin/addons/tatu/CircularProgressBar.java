package org.vaadin.addons.tatu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

@JsModule("./circular-progress-bar.ts")
@Tag("circular-progress-bar")
public class CircularProgressBar extends Component implements HasSize {

    public CircularProgressBar() {
        setBorder(false);
    }

    /**
     * Set scaling factor. E.g. if scale is 0.5 and percent is 0.5, progress
     * indicator will show complete. Default 1.0.
     * 
     * @param scale
     *            double value.
     */
    public void setScale(double scale) {
        getElement().setProperty("scale", scale);
    }

    /**
     * Set the percentage.
     * 
     * @param percent
     *            double value between 0..1.
     */
    public void setPercent(double percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("percent can't be negative");
        } else if (percent > 1.0) {
            throw new IllegalArgumentException("percent can't be over 1.0");
        }
        getElement().setProperty("percent", percent);
    }

    /**
     * Set the caption text shown in the middle of the component below the
     * percentage value.
     * 
     * @param caption
     *            String value.
     */
    public void setCaption(String caption) {
        getElement().setProperty("label", caption);
    }

    /**
     * Set the color used for filling of the progress indicator.
     * 
     * @param color
     *            CSS compatible color string.
     */
    public void setColor(String color) {
        getElement().getStyle().set("--lumo-primary-color", color);
    }

    /**
     * Use true to set animation on.
     * 
     * @see setDelay
     * 
     * @param animation
     *            boolean value
     */
    public void setAnimation(boolean animation) {
        getElement().setProperty("animation", animation);
    }

    /**
     * Set the animation frame delay, default 10ms.
     * 
     * @see setAnimation
     * 
     * @param delay
     *            int value in millis.
     */
    public void setDelay(int delay) {
        getElement().setProperty("delay", delay);
    }

    /**
     * Use true to add border in the progress indicator, default false.
     * 
     * @param border Boolean value
     */
    public void setBorder(boolean border) {
        if (border) {
            getElement().removeAttribute("noborder");
        } else {
            getElement().setAttribute("noborder", true);
        }
    }
}
