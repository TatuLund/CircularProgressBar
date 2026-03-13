package org.vaadin.addons.tatu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.shared.HasTooltip;
import com.vaadin.flow.dom.SignalBinding;
import com.vaadin.flow.signals.Signal;

@JsModule("./circular-progress-bar.ts")
@Tag("circular-progress-bar")
public class CircularProgressBar extends Component
        implements HasSize, HasTooltip {

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
     * Bind the scale property to a signal.
     * 
     * @param signal
     *            Signal<Double> value
     * @return SignalBinding<Double> instance
     */
    public SignalBinding<Double> bindScale(Signal<Double> signal) {
        return getElement().bindProperty("scale", signal, null);
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
     * Bind the percent property to a signal.
     * 
     * @param signal
     *           Signal<Double> value between 0..1.
     * @return SignalBinding<Double> instance
     */
    public SignalBinding<Double> bindPercent(Signal<Double> signal) {
        return getElement().bindProperty("percent", signal, null);
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
     * Bind the caption property to a signal.
     * 
     * @param signal
     *           Signal<String> value
     * @return SignalBinding<String> instance
     */
    public SignalBinding<String> bindCaption(Signal<String> signal) {
        return getElement().bindProperty("label", signal, null);
    }

    /**
     * Set the color used for filling of the progress indicator.
     * 
     * @param color
     *            CSS compatible color string.
     */
    public void setColor(String color) {
        getElement().getStyle().set("--circle-color", color);
    }

    /**
     * Bind the color property to a signal.
     *
     * @param signal
     *           Signal<String> value, CSS compatible color string.
     * @return SignalBinding<String> instance
     */
    public SignalBinding<?> bindColor(Signal<String> signal) {
        return getElement().getStyle().bind("--circle-color", signal);
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
     * Bind the animation property to a signal.
     * 
     * @param signal
     *            Signal<Boolean> value
     * @return SignalBinding<Boolean> instance
     */
    public SignalBinding<Boolean> bindAnimation(Signal<Boolean> signal) {
        return getElement().bindProperty("animation", signal, null);
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
     * Bind the delay property to a signal.
     *
     * @param signal Signal<Integer> value, delay in millis.
     * @return SignalBinding<Integer> instance
     */
    public SignalBinding<Integer> bindDelay(Signal<Integer> signal) {
        return getElement().bindProperty("delay", signal, null);
    }

    /**
     * Use true to add border in the progress indicator, default false.
     * 
     * @param border
     *            Boolean value
     */
    public void setBorder(boolean border) {
        if (border) {
            getElement().removeAttribute("noborder");
        } else {
            getElement().setAttribute("noborder", true);
        }
    }

    /**
     * Bind the border attribute to a signal.
     * 
     * @param signal
     *            Signal<String> value, "true" or null
     * @return SignalBinding<String> instance
     */
    public SignalBinding<String> bindBorder(Signal<String> signal) {
        return getElement().bindAttribute("noborder", signal);
    }
}
