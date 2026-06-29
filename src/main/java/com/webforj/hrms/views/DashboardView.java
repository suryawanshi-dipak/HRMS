package com.webforj.hrms.views;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.annotation.RouteAlias;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Route(value = "/", outlet = MainLayout.class)
@RouteAlias(value = "/dashboard")
@FrameTitle("Dashboard")
public class DashboardView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();

  private boolean checkedIn = true;
  private final String checkInTime = "09:26";
  private H2 statusH2;
  private Div detailsDiv;
  private Button statusIndicatorBtn;
  private Button actionBtn;

  public DashboardView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setStyle("padding", "var(--dwc-space-l)");
    self.setStyle("gap", "var(--dwc-space-l)");

    H1 title = new H1("Dashboard Overview");
    title.addClassName("page-title");

    self.add(title, buildStatsRow(), buildNotificationsSection());
  }

  private FlexLayout buildStatsRow() {
    FlexLayout row = new FlexLayout();
    row.setWrap(FlexWrap.WRAP);
    row.setStyle("gap", "var(--dwc-space-l)");

    row.add(buildStatusCard(), buildLeaveCard(), buildAttendanceCard());
    return row;
  }

  private FlexLayout buildStatusCard() {
    FlexLayout card = new FlexLayout();
    card.setDirection(FlexDirection.COLUMN);
    card.addClassName("stat-card");
    card.setStyle("gap", "var(--dwc-space-s)");

    Div label = new Div();
    label.setText("TODAY'S STATUS");
    label.addClassName("stat-card-label");

    statusH2 = new H2("Present");
    statusH2.setStyle("margin", "0");

    detailsDiv = new Div();
    detailsDiv.setText("In: " + checkInTime + "\nHours: 00:00");
    detailsDiv.setStyle("white-space", "pre-line");
    detailsDiv.setStyle("color", "var(--dwc-color-default-text)");
    detailsDiv.setStyle("font-size", "0.875rem");

    statusIndicatorBtn = new Button("Checked In");
    statusIndicatorBtn.addClassName("btn-checkin");

    actionBtn = new Button("Check Out");
    actionBtn.addClassName("btn-checkin");
    actionBtn.onClick(ev -> toggleCheckInOut());

    card.add(label, statusH2, detailsDiv, statusIndicatorBtn, actionBtn);
    return card;
  }

  private void toggleCheckInOut() {
    String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    if (checkedIn) {
      checkedIn = false;
      statusH2.setText("Checked Out");
      detailsDiv.setText("In: " + checkInTime + "\nOut: " + now + "\nHours: 08:34");
      statusIndicatorBtn.setText("Checked Out");
      actionBtn.setText("Check In");
      Toast.show("Checked out at " + now, 2000, Theme.SUCCESS, Toast.Placement.TOP_RIGHT);
    } else {
      checkedIn = true;
      statusH2.setText("Present");
      detailsDiv.setText("In: " + now + "\nHours: 00:00");
      statusIndicatorBtn.setText("Checked In");
      actionBtn.setText("Check Out");
      Toast.show("Checked in at " + now, 2000, Theme.SUCCESS, Toast.Placement.TOP_RIGHT);
    }
  }

  private FlexLayout buildLeaveCard() {
    FlexLayout card = new FlexLayout();
    card.setDirection(FlexDirection.COLUMN);
    card.addClassName("stat-card");
    card.setStyle("gap", "var(--dwc-space-s)");

    Div label = new Div();
    label.setText("LEAVE BALANCE");
    label.addClassName("stat-card-label");

    H2 balance = new H2("5/12");
    balance.setStyle("margin", "0");

    Div remaining = new Div();
    remaining.setText("Days Remaining");
    remaining.setStyle("color", "var(--dwc-color-default-text)");
    remaining.setStyle("font-size", "0.875rem");

    FlexLayout breakdown = new FlexLayout();
    breakdown.setJustifyContent(FlexJustifyContent.BETWEEN);
    breakdown.setStyle("margin-top", "var(--dwc-space-m)");

    breakdown.add(
      buildLeaveBreakdown("Planned", "-1/6"),
      buildLeaveBreakdown("Casual", "3/3"),
      buildLeaveBreakdown("Sick", "3/3")
    );

    card.add(label, balance, remaining, breakdown);
    return card;
  }

  private FlexLayout buildLeaveBreakdown(String type, String value) {
    FlexLayout col = new FlexLayout();
    col.setDirection(FlexDirection.COLUMN);
    col.setStyle("align-items", "center");
    col.setStyle("gap", "4px");

    Div typeDiv = new Div();
    typeDiv.setText(type);
    typeDiv.setStyle("font-size", "0.8rem");
    typeDiv.setStyle("color", "var(--dwc-color-default-text)");

    Div valueDiv = new Div();
    valueDiv.setText(value);
    valueDiv.setStyle("font-weight", "600");

    col.add(typeDiv, valueDiv);
    return col;
  }

  private FlexLayout buildAttendanceCard() {
    FlexLayout card = new FlexLayout();
    card.setDirection(FlexDirection.COLUMN);
    card.addClassName("stat-card");
    card.setStyle("gap", "var(--dwc-space-s)");

    Div label = new Div();
    label.setText("Attendance Rate");
    label.addClassName("stat-card-label");

    H2 rate = new H2("97%");
    rate.setStyle("margin", "0");

    Div subtext = new Div();
    subtext.setText("Based on last 30 days");
    subtext.setStyle("color", "var(--dwc-color-default-text)");
    subtext.setStyle("font-size", "0.875rem");

    Div progressBar = new Div();
    progressBar.addClassName("progress-bar-track");
    Div progress = new Div();
    progress.addClassName("progress-bar-fill");
    progress.setStyle("width", "97%");
    progressBar.add(progress);

    card.add(label, rate, subtext, progressBar);
    return card;
  }

  private FlexLayout buildNotificationsSection() {
    FlexLayout section = new FlexLayout();
    section.setDirection(FlexDirection.COLUMN);
    section.addClassName("notifications-section");
    section.setStyle("gap", "var(--dwc-space-s)");

    Div title = new Div();
    title.setText("Recent Notifications");
    title.addClassName("section-title");

    String[][] notifications = {
      {"Nikhil More's sick leave request from 2026-06-23 to 2026-06-23 has been approved.", "24/6/2026, 5:33:31 am"},
      {"Paul Sebastian's sick leave request from 2026-06-18 to 2026-06-18 has been approved.", "19/6/2026, 4:27:07 am"},
      {"Divesh Bari's paid leave request from 2026-06-25 to 2026-06-26 has been approved.", "18/6/2026, 7:08:42 am"},
      {"Divesh Bari's paid leave request from 2026-06-25 to 2026-06-26 has been approved.", "18/6/2026, 7:08:35 am"},
      {"Paul Sebastian's sick leave request from 2026-06-17 to 2026-06-17 has been approved.", "18/6/2026, 4:26:15 am"},
      {"Dhruv Sardesai's casual leave request from 2026-06-15 to 2026-06-15 has been approved.", "14/6/2026, 6:59:42 pm"},
    };

    FlexLayout list = new FlexLayout();
    list.setDirection(FlexDirection.COLUMN);
    list.addClassName("notification-list");

    for (String[] n : notifications) {
      FlexLayout item = new FlexLayout();
      item.setDirection(FlexDirection.COLUMN);
      item.addClassName("notification-item");

      Div message = new Div();
      message.setText(n[0]);
      message.addClassName("notification-message");

      Div time = new Div();
      time.setText(n[1]);
      time.addClassName("notification-time");

      item.add(message, time);
      list.add(item);
    }

    section.add(title, list);
    return section;
  }
}
