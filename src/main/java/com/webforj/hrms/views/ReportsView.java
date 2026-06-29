package com.webforj.hrms.views;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route(value = "/reports", outlet = MainLayout.class)
@FrameTitle("Reports")
public class ReportsView extends Composite<FlexLayout> {

  public ReportsView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN);
    self.setStyle("padding", "var(--dwc-space-l)");
    self.setStyle("gap", "var(--dwc-space-m)");

    H1 title = new H1("Reports");
    title.addClassName("page-title");

    Div placeholder = new Div();
    placeholder.setText("Reports coming soon.");
    placeholder.addClassName("summary-label");

    self.add(title, placeholder);
  }
}
