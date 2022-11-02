package org.vaadin.addons.tatu;

import java.util.concurrent.atomic.AtomicInteger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

@Push
@Route("")
public class View extends VerticalLayout implements AppShellConfigurator {

    CircularProgressBar progress = new CircularProgressBar();

    public View() {
        progress.setWidth("200px");
        progress.setHeight("200px");
        progress.setPercent(0);
        progress.setCaption("Loading...");

        add(progress);
        Runnable runnable = () -> {
            AtomicInteger percent = new AtomicInteger(5);
            while (percent.get() <= 100) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                getUI().ifPresent(ui -> ui.access(() -> {
                    progress.setPercent(percent.getAndAdd(5));
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
    }

}
