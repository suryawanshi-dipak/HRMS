package com.webforj.hrms.views;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route(value = "/attendance", outlet = MainLayout.class)
@FrameTitle("Attendance")
public class AttendanceView extends Composite<FlexLayout> {

  public AttendanceView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN);
    self.setStyle("padding", "var(--dwc-space-l)");
    self.setStyle("gap", "var(--dwc-space-m)");

    H1 title = new H1("Attendance");
    title.addClassName("page-title");

    Div placeholder = new Div();
    placeholder.setText("Attendance tracking coming soon.");
    placeholder.addClassName("summary-label");

    self.add(title, placeholder);
  }
}
