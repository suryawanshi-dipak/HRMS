package com.webforj.hrms.views;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.table.Table;
import com.webforj.component.toast.Toast;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "/leaves", outlet = MainLayout.class)
@FrameTitle("Leaves")
public class LeavesView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();

  record LeaveRequest(String type, String startDate, String endDate, String days, String reason, String status) {}

  private final ChoiceBox leaveType = new ChoiceBox("Leave Type");
  private final ChoiceBox leaveDuration = new ChoiceBox("Leave Duration");
  private final TextField startDate = new TextField("Start Date");
  private final TextField endDate = new TextField("End Date");
  private final TextField reason = new TextField("Reason");
  private final Button submitBtn = new Button("SUBMIT REQUEST");

  private final List<LeaveRequest> requests = new ArrayList<>(List.of(
    new LeaveRequest("Planned", "18/06/2026", "19/06/2026", "2", "Hi, I would like to request leave on 18 June and 1...", "approved"),
    new LeaveRequest("Planned", "23/04/2026", "24/04/2026", "2", "I would like to request leave on 23rd and 24th Apr...", "approved"),
    new LeaveRequest("Planned", "23/04/2026", "24/04/2026", "2", "I would like to request leave from 23rd and 24th o...", "pending")
  ));

  private Table<LeaveRequest> historyTable = new Table<>();

  public LeavesView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setStyle("padding", "var(--dwc-space-l)");
    self.setStyle("gap", "var(--dwc-space-l)");

    H1 title = new H1("Leave Management");
    title.addClassName("page-title");

    self.add(title, buildApplySection(), buildHistorySection());
  }

  private FlexLayout buildApplySection() {
    FlexLayout section = new FlexLayout();
    section.setDirection(FlexDirection.COLUMN);
    section.addClassName("form-card");
    section.setStyle("gap", "var(--dwc-space-m)");

    H2 heading = new H2("Apply for Leave");
    heading.addClassName("card-title");

    leaveType.add("Sick Leave");
    leaveType.add("Planned Leave");
    leaveType.add("Casual Leave");
    leaveType.selectIndex(0);
    leaveType.setStyle("flex", "1");

    leaveDuration.add("Full Day");
    leaveDuration.add("Half Day - Morning");
    leaveDuration.add("Half Day - Afternoon");
    leaveDuration.selectIndex(0);
    leaveDuration.setStyle("flex", "1");

    FlexLayout typeRow = new FlexLayout();
    typeRow.setStyle("gap", "var(--dwc-space-m)");
    typeRow.setWrap(FlexWrap.WRAP);
    typeRow.add(leaveType, leaveDuration);

    startDate.setPlaceholder("dd-mm-yyyy");
    startDate.setStyle("flex", "1");
    endDate.setPlaceholder("dd-mm-yyyy");
    endDate.setStyle("flex", "1");

    FlexLayout dateRow = new FlexLayout();
    dateRow.setStyle("gap", "var(--dwc-space-m)");
    dateRow.setWrap(FlexWrap.WRAP);
    dateRow.add(startDate, endDate);

    Div uploadLabel = new Div();
    uploadLabel.setText("Upload Document");
    uploadLabel.setStyle("font-size", "0.875rem");
    uploadLabel.setStyle("color", "var(--dwc-color-default-text)");

    Button chooseFile = new Button("Choose File");
    chooseFile.setTheme(ButtonTheme.OUTLINED_PRIMARY);

    final Div noFile = new Div();
    noFile.setText("No file chosen");

    chooseFile.onClick(ev -> {
      noFile.setText("supporting_document.pdf");
      Toast.show("File selected: supporting_document.pdf", 1500, Theme.SUCCESS, Toast.Placement.TOP_RIGHT);
    });
    noFile.setStyle("color", "var(--dwc-color-default-text)");
    noFile.setStyle("font-size", "0.875rem");
    noFile.setStyle("margin-left", "var(--dwc-space-s)");

    FlexLayout uploadRow = new FlexLayout();
    uploadRow.setStyle("align-items", "center");
    uploadRow.setStyle("gap", "var(--dwc-space-s)");
    uploadRow.add(chooseFile, noFile);

    reason.setPlaceholder("Please provide a valid reason...");

    submitBtn.setTheme(ButtonTheme.PRIMARY);
    submitBtn.addClassName("submit-btn");
    submitBtn.onClick(ev -> handleSubmit());

    FlexLayout submitRow = new FlexLayout();
    submitRow.setJustifyContent(FlexJustifyContent.END);
    submitRow.add(submitBtn);

    section.add(heading, typeRow, dateRow, uploadLabel, uploadRow, reason, submitRow);
    return section;
  }

  private FlexLayout buildHistorySection() {
    FlexLayout section = new FlexLayout();
    section.setDirection(FlexDirection.COLUMN);
    section.addClassName("form-card");
    section.setStyle("gap", "var(--dwc-space-m)");

    H2 heading = new H2("Request History");
    heading.addClassName("card-title");

    historyTable.addColumn("TYPE", LeaveRequest::type);
    historyTable.addColumn("START DATE", LeaveRequest::startDate);
    historyTable.addColumn("END DATE", LeaveRequest::endDate);
    historyTable.addColumn("DAYS", LeaveRequest::days);
    historyTable.addColumn("REASON", LeaveRequest::reason);
    historyTable.addColumn("STATUS", LeaveRequest::status);

    refreshTable();

    section.add(heading, historyTable);
    return section;
  }

  private void handleSubmit() {
    String start = startDate.getText() != null ? startDate.getText().trim() : "";
    String end = endDate.getText() != null ? endDate.getText().trim() : "";
    String reasonText = reason.getText() != null ? reason.getText().trim() : "";

    if (start.isEmpty() || end.isEmpty() || reasonText.isEmpty()) {
      Toast.show("Please fill in all required fields", 2000, Theme.WARNING, Toast.Placement.TOP_RIGHT);
      return;
    }

    String type = leaveType.getSelectedItem() != null ? leaveType.getSelectedItem().getText() : "Planned";
    requests.add(0, new LeaveRequest(type, start, end, "1", reasonText, "pending"));
    refreshTable();

    startDate.setText("");
    endDate.setText("");
    reason.setText("");
    Toast.show("Leave request submitted successfully", 2000, Theme.SUCCESS, Toast.Placement.TOP_RIGHT);
  }

  private void refreshTable() {
    CollectionRepository<LeaveRequest> repo = new CollectionRepository<>(requests);
    historyTable.setRepository(repo);
    repo.commit();
  }
}
