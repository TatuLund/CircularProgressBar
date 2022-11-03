package org.vaadin.addons.tatu;

import java.util.concurrent.atomic.AtomicInteger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.Transport;

@Push(transport = Transport.LONG_POLLING)
@Route("")
public class View extends VerticalLayout implements AppShellConfigurator {

    CircularProgressBar progress = new CircularProgressBar();

    public View() {
        progress.setWidth("200px");
        progress.setHeight("200px");
        progress.setPercent(0);
        progress.setCaption("Loading...");

        Button button = new Button("Load");
        button.addClickListener(event -> {
            progress.setPercent(0);
            progress.setCaption("Loading...");
            Runnable runnable = () -> {
                AtomicInteger percent = new AtomicInteger(5);
                while (percent.get() <= 100) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    getUI().ifPresent(ui -> ui.access(() -> {
                        progress.setPercent(percent.getAndAdd(5) / 100.0);
                        if (percent.get() <= 25) {
                            progress.setColor("red");
                        } else if (percent.get() <= 50) {
                            progress.setColor("orange");
                        } else if (percent.get() > 75) {
                            progress.setColor("green");
                        } else {
                            progress.setColor("yellow");
                        }
                    }));
                }
                getUI().ifPresent(ui -> ui.access(() -> {
                    progress.setCaption("Done!");
                }));
            };

            Thread thread = new Thread(runnable);
            thread.start();
        });

        Select<String> size = new Select<>();
        size.setLabel("Size");
        size.setItems("100px", "150px", "200px", "250px");

        size.addValueChangeListener(event -> {
            progress.setWidth(event.getValue());
            progress.setHeight(event.getValue());
        });

        IntegerField field = new IntegerField("Percent");
        field.setMax(100);
        field.setMin(0);
        field.setStep(1);
        field.setHasControls(true);
        field.setSuffixComponent(new Span("%"));
        field.addValueChangeListener(event -> {
            progress.setPercent(event.getValue() / 100.0);
        });

        Checkbox animate = new Checkbox("Animate");
        animate.addValueChangeListener(event -> {
            progress.setAnimation(event.getValue());
        });

        Checkbox border = new Checkbox("Border");
        border.addValueChangeListener(event -> {
            progress.setBorder(event.getValue());
        });

        Select<Integer> delay = new Select<>();
        delay.setLabel("Delay");
        delay.setItems(5, 10, 15, 20);

        delay.addValueChangeListener(event -> {
            progress.setDelay(event.getValue());
        });

        TextField caption = new TextField("Caption");
        caption.addValueChangeListener(event -> {
            progress.setCaption(event.getValue());
        });

        add(progress, button, size, field, animate, border, delay, caption);

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

}
