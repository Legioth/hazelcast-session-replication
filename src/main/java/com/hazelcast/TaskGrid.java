package com.hazelcast;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;

public class TaskGrid extends Grid<Task> {

    private final List<Task> tasks = new ArrayList<>();

    public TaskGrid() {
        super(Task.class);
        ListDataProvider<Task> provider = new ListDataProvider<>(tasks);
        setDataProvider(provider);
        setColumnOrder(idColumn(), labelColumn(), doneColumn(), createdColumn());
        configureHeader();
    }

    private void configureHeader() {
        HeaderRow rowHeader = appendHeaderRow();
        TextField labelField = new TextField();
        rowHeader.getCell(labelColumn()).setComponent(labelField);
        Button addButton = new Button("Add");
        rowHeader.getCell(doneColumn()).setComponent(addButton);
        addButton.addClickListener(addTask(labelField));
    }

    private ComponentEventListener<ClickEvent<Button>> addTask(TextField field) {
        return event -> {
            Task task = new Task(field.getValue());
            field.clear();
            tasks.add(task);
            getDataProvider().refreshAll();
            VaadinSession.getCurrent().getSession().setAttribute("grids", this);
        };
    }

    private Column<Task> idColumn() {
        return getColumnByKey("id");
    }

    private Column<Task> labelColumn() {
        return getColumnByKey("label");
    }

    private Column<Task> doneColumn() {
        return getColumnByKey("done");
    }

    private Column<Task> createdColumn() {
        return getColumnByKey("created");
    }
}