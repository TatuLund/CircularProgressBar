package org.vaadin.addons.tatu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.shared.Tooltip.TooltipPosition;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.signals.local.ValueSignal;

@Route("")
public class View extends VerticalLayout {

    CircularProgressBar progress = new CircularProgressBar();
    ValueSignal<Double> percentSignal = new ValueSignal<>(0.0);
    ValueSignal<String> captionSignal = new ValueSignal<>("");
    ValueSignal<String> colorSignal = new ValueSignal<>("red");
    ValueSignal<Boolean> animationSignal = new ValueSignal<>(false);
    ValueSignal<Boolean> borderSignal = new ValueSignal<>(false);
    ValueSignal<Integer> delaySignal = new ValueSignal<>(10);
    ValueSignal<String> widthSignal = new ValueSignal<String>("200px");
    ValueSignal<String> heightSignal = new ValueSignal<String>("200px");

    public View() {
        progress.bindWidth(widthSignal);
        progress.bindHeight(heightSignal);
        progress.bindPercent(percentSignal);
        progress.bindCaption(captionSignal);
        progress.bindColor(colorSignal);
        progress.bindAnimation(animationSignal);
        progress.bindBorder(borderSignal);
        progress.bindDelay(delaySignal);
        progress.setTooltipText("Loading...").withPosition(TooltipPosition.TOP);

        var button = new Button("Load");
        button.addClickListener(event -> {
            percentSignal.set(0.0);
            captionSignal.set("Loading...");
            Thread thread = new Thread(runnable);
            thread.start();
        });

        var size = new Select<String>();
        size.setLabel("Size");
        size.setItems("100px", "150px", "200px", "250px");

        size.addValueChangeListener(event -> {
            widthSignal.set(event.getValue());
            heightSignal.set(event.getValue());
        });

        var field = new IntegerField("Percent");
        field.setMax(100);
        field.setMin(0);
        field.setStep(1);
        //field.setHasControls(true);
        field.setSuffixComponent(new Span("%"));
        field.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                percentSignal.set(event.getValue() / 100.0);
            }
        });

        var animate = new Checkbox("Animate");
        animate.addValueChangeListener(event -> {
            animationSignal.set(event.getValue());
        });

        var border = new Checkbox("Border");
        border.addValueChangeListener(event -> {
            borderSignal.set(event.getValue());
        });

        var delay = new Select<Integer>();
        delay.setLabel("Delay");
        delay.setItems(5, 10, 15, 20);
        delay.addValueChangeListener(event -> {
            delaySignal.set(event.getValue());
        });

        var caption = new TextField("Caption");
        caption.addValueChangeListener(event -> {
            captionSignal.set(event.getValue());
        });

        add(progress, button, size, field, animate, border, delay, caption);

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    Runnable runnable = () -> {
        percentSignal.set(0.05);
        while (percentSignal.peek() <= 1.0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            percentSignal.set(percentSignal.peek() + 0.05);
            if (percentSignal.peek() <= 0.25) {
                colorSignal.set("red");
            } else if (percentSignal.peek() <= 0.5) {
                colorSignal.set("orange");
            } else if (percentSignal.peek() > 0.75) {
                colorSignal.set("green");
            } else {
                colorSignal.set("yellow");
            }
        }
        captionSignal.set("Done!");
    };


}
