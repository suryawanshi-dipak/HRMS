package com.webforj.hrms.views;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route(value = "/settings", outlet = MainLayout.class)
@FrameTitle("Settings")
public class SettingsView extends Composite<FlexLayout> {

  private static final String CURRENT_PASSWORD = "password";

  private FlexLayout self = getBoundComponent();

  public SettingsView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setStyle("padding", "var(--dwc-space-l)");
    self.setStyle("gap", "var(--dwc-space-l)");

    H1 title = new H1("My Profile & Settings");
    title.addClassName("page-title");

    self.add(title, buildProfileCard(), buildAttendanceSummaryCard(), buildAccountSettingsCard());
  }

  private FlexLayout buildProfileCard() {
    FlexLayout card = new FlexLayout();
    card.setDirection(FlexDirection.COLUMN);
    card.addClassName("profile-card");

    Div cardHeader = new Div();
    cardHeader.setText("Personal & Role Information");
    cardHeader.addClassName("profile-card-header");

    FlexLayout body = new FlexLayout();
    body.addClassName("profile-card-body");
    body.setStyle("gap", "var(--dwc-space-l)");
    body.setWrap(FlexWrap.WRAP);
    body.setAlignment(FlexAlignment.CENTER);

    FlexLayout leftCol = new FlexLayout();
    leftCol.setDirection(FlexDirection.COLUMN);
    leftCol.setAlignment(FlexAlignment.CENTER);
    leftCol.setStyle("gap", "var(--dwc-space-s)");

    Div avatar = new Div();
    avatar.addClassName("profile-avatar");

    H2 name = new H2("Dipak Suryawanshi");
    name.setStyle("margin", "0");
    name.setStyle("font-size", "1.1rem");

    Div idDiv = new Div();
    idDiv.setText("ID: 29");
    idDiv.setStyle("font-size", "0.85rem");
    idDiv.setStyle("color", "var(--dwc-color-default-text)");

    Div roleBadge = new Div();
    roleBadge.setText("EMPLOYEE");
    roleBadge.addClassName("role-badge");

    leftCol.add(avatar, name, idDiv, roleBadge);

    FlexLayout rightCol = new FlexLayout();
    rightCol.setDirection(FlexDirection.COLUMN);
    rightCol.setStyle("gap", "var(--dwc-space-s)");
    rightCol.setStyle("flex", "1");

    rightCol.add(
      buildInfoRow("Email:", "d.suryawanshi@vitec.co.in", false),
      buildInfoRow("Phone:", "9130766562", true),
      buildInfoRow("Address:", "Navi Mumbai", true),
      buildInfoRow("Emergency Contact:", "9130766562", true),
      buildInfoRow("Department:", "IT", false),
      buildInfoRow("Joined:", "17/10/2002", false)
    );

    body.add(leftCol, rightCol);
    card.add(cardHeader, body);
    return card;
  }

  private FlexLayout buildInfoRow(String label, String value, boolean editable) {
    FlexLayout row = new FlexLayout();
    row.setAlignment(FlexAlignment.CENTER);
    row.setStyle("gap", "var(--dwc-space-s)");

    Div labelDiv = new Div();
    labelDiv.setText(label);
    labelDiv.setStyle("min-width", "160px");
    labelDiv.setStyle("color", "var(--dwc-color-default-text)");
    labelDiv.setStyle("font-size", "0.9rem");

    Div valueDiv = new Div();
    valueDiv.setText(value);
    valueDiv.setStyle("font-size", "0.9rem");
    valueDiv.setStyle("flex", "1");

    row.add(labelDiv, valueDiv);

    if (editable) {
      TextField editField = new TextField();
      editField.setText(value);
      editField.setStyle("flex", "1");
      editField.setVisible(false);

      Button editBtn = new Button("Edit");
      editBtn.addClassName("edit-link");

      Button saveBtn = new Button("Save");
      saveBtn.addClassName("edit-link");
      saveBtn.setVisible(false);

      editBtn.onClick(ev -> {
        valueDiv.setVisible(false);
        editField.setVisible(true);
        editBtn.setVisible(false);
        saveBtn.setVisible(true);
      });

      saveBtn.onClick(ev -> {
        String newVal = editField.getText() != null ? editField.getText().trim() : "";
        if (!newVal.isEmpty()) {
          valueDiv.setText(newVal);
        }
        valueDiv.setVisible(true);
        editField.setVisible(false);
        editBtn.setVisible(true);
        saveBtn.setVisible(false);
        Toast.show("Updated successfully", 2000, Theme.SUCCESS, Toast.Placement.TOP_RIGHT);
      });

      row.add(editField, editBtn, saveBtn);
    }

    return row;
  }

  private FlexLayout buildAttendanceSummaryCard() {
    FlexLayout card = new FlexLayout();
    card.setDirection(FlexDirection.COLUMN);
    card.addClassName("form-card");
    card.setStyle("gap", "var(--dwc-space-m)");

    H2 heading = new H2("Work & Attendance Summary");
    heading.addClassName("card-title");

    FlexLayout statusRow = new FlexLayout();
    statusRow.setJustifyContent(FlexJustifyContent.BETWEEN);
    statusRow.setAlignment(FlexAlignment.CENTER);
    statusRow.setStyle("padding", "var(--dwc-space-s) 0");
    statusRow.setStyle("border-bottom", "1px solid var(--dwc-color-default-alt)");

    Div statusLabel = new Div();
    statusLabel.setText("Today's Status");

    Div presentBadge = new Div();
    presentBadge.setText("PRESENT");
    presentBadge.addClassName("present-badge");

    statusRow.add(statusLabel, presentBadge);

    FlexLayout hoursRow = new FlexLayout();
    hoursRow.setJustifyContent(FlexJustifyContent.BETWEEN);
    hoursRow.setAlignment(FlexAlignment.CENTER);
    hoursRow.setStyle("padding", "var(--dwc-space-s) 0");
    hoursRow.setStyle("border-bottom", "1px solid var(--dwc-color-default-alt)");

    Div hoursLabel = new Div();
    hoursLabel.setText("Monthly Hours");
    hoursLabel.addClassName("summary-label");

    Div hoursValue = new Div();
    hoursValue.setText("825.00 hrs");
    hoursValue.addClassName("summary-value");

    hoursRow.add(hoursLabel, hoursValue);

    Div summaryTitle = new Div();
    summaryTitle.setText("Monthly Attendance Summary");
    summaryTitle.addClassName("summary-label");
    summaryTitle.setStyle("padding-top", "var(--dwc-space-s)");

    FlexLayout grid = new FlexLayout();
    grid.setStyle("gap", "var(--dwc-space-m)");
    grid.setWrap(FlexWrap.WRAP);

    grid.add(
      buildSummaryBox("116", "Present"),
      buildSummaryBox("0", "Absent"),
      buildSummaryBox("9", "Late")
    );

    card.add(heading, statusRow, hoursRow, summaryTitle, grid);
    return card;
  }

  private FlexLayout buildSummaryBox(String number, String label) {
    FlexLayout box = new FlexLayout();
    box.setDirection(FlexDirection.COLUMN);
    box.setAlignment(FlexAlignment.CENTER);
    box.addClassName("summary-box");

    H3 num = new H3(number);
    num.setStyle("margin", "0");

    Div lbl = new Div();
    lbl.setText(label);
    lbl.setStyle("font-size", "0.85rem");
    lbl.setStyle("color", "var(--dwc-color-default-text)");

    box.add(num, lbl);
    return box;
  }

  private FlexLayout buildAccountSettingsCard() {
    FlexLayout card = new FlexLayout();
    card.setDirection(FlexDirection.COLUMN);
    card.addClassName("form-card");
    card.setStyle("gap", "var(--dwc-space-m)");

    FlexLayout headerRow = new FlexLayout();
    headerRow.setJustifyContent(FlexJustifyContent.BETWEEN);
    headerRow.setAlignment(FlexAlignment.CENTER);

    FlexLayout left = new FlexLayout();
    left.setDirection(FlexDirection.COLUMN);
    left.setStyle("gap", "4px");

    H2 heading = new H2("Account Settings");
    heading.addClassName("card-title");
    heading.setStyle("margin", "0");

    Div sub = new Div();
    sub.setText("Manage your password and session.");
    sub.addClassName("summary-label");

    left.add(heading, sub);

    Button changePasswordBtn = new Button("Change Password");
    changePasswordBtn.setTheme(ButtonTheme.OUTLINED_DEFAULT);

    headerRow.add(left, changePasswordBtn);

    // Inline password change form (hidden until button is clicked)
    FlexLayout pwdForm = new FlexLayout();
    pwdForm.setDirection(FlexDirection.COLUMN);
    pwdForm.setStyle("gap", "var(--dwc-space-m)");
    pwdForm.setStyle("padding-top", "var(--dwc-space-m)");
    pwdForm.setStyle("border-top", "1px solid var(--dwc-color-default-alt)");
    pwdForm.setVisible(false);

    PasswordField currentPwd = new PasswordField("Current Password");
    PasswordField newPwd = new PasswordField("New Password");
    PasswordField confirmPwd = new PasswordField("Confirm New Password");

    FlexLayout btnRow = new FlexLayout();
    btnRow.setJustifyContent(FlexJustifyContent.END);
    btnRow.setStyle("gap", "var(--dwc-space-s)");

    Button cancelBtn = new Button("Cancel");
    cancelBtn.setTheme(ButtonTheme.OUTLINED_DEFAULT);

    Button saveBtn = new Button("Update Password");
    saveBtn.setTheme(ButtonTheme.PRIMARY);

    btnRow.add(cancelBtn, saveBtn);
    pwdForm.add(currentPwd, newPwd, confirmPwd, btnRow);

    changePasswordBtn.onClick(ev -> pwdForm.setVisible(true));

    cancelBtn.onClick(ev -> {
      pwdForm.setVisible(false);
      currentPwd.setText("");
      newPwd.setText("");
      confirmPwd.setText("");
    });

    saveBtn.onClick(ev -> {
      String current = currentPwd.getText() != null ? currentPwd.getText() : "";
      String newP = newPwd.getText() != null ? newPwd.getText() : "";
      String confirm = confirmPwd.getText() != null ? confirmPwd.getText() : "";

      if (current.isEmpty() || newP.isEmpty() || confirm.isEmpty()) {
        Toast.show("Please fill in all fields", 2000, Theme.WARNING, Toast.Placement.TOP_RIGHT);
        return;
      }
      if (!newP.equals(confirm)) {
        Toast.show("New passwords do not match", 2000, Theme.DANGER, Toast.Placement.TOP_RIGHT);
        return;
      }
      if (!CURRENT_PASSWORD.equals(current)) {
        Toast.show("Current password is incorrect", 2000, Theme.DANGER, Toast.Placement.TOP_RIGHT);
        return;
      }
      pwdForm.setVisible(false);
      currentPwd.setText("");
      newPwd.setText("");
      confirmPwd.setText("");
      Toast.show("Password updated successfully", 2000, Theme.SUCCESS, Toast.Placement.TOP_RIGHT);
    });

    card.add(headerRow, pwdForm);
    return card;
  }
}
