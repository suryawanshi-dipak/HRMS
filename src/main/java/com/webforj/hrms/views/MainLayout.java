package com.webforj.hrms.views;

import com.webforj.Page;
import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.applayout.AppLayout.DrawerPlacement;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.event.NavigateEvent;
import com.webforj.router.history.Location;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainLayout extends Composite<AppLayout> implements DidEnterObserver {

  private static MainLayout currentInstance;

  private AppLayout self = getBoundComponent();
  private Div pageLabel = new Div();
  private final Map<String, Button> navButtons = new LinkedHashMap<>();

  private static final String[][] NAV_ITEMS = {
    {"/",           "Dashboard"},
    {"/attendance", "Attendance"},
    {"/leaves",     "Leaves"},
    {"/reports",    "Reports"},
    {"/settings",   "Settings"},
  };

  public MainLayout() {
    currentInstance = this;
    setupSidebar();
    setupHeader();
    Router.getCurrent().onNavigate(this::onNavigate);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String loggedIn = Page.getCurrent().getAttribute("loggedIn");
    if (!"true".equals(loggedIn)) {
      Router.getCurrent().navigate(new Location("/login"));
    }
  }

  private void setupSidebar() {
    self.setDrawerPlacement(DrawerPlacement.LEFT);

    FlexLayout sidebar = new FlexLayout();
    sidebar.setDirection(FlexDirection.COLUMN);
    sidebar.setJustifyContent(FlexJustifyContent.BETWEEN);
    sidebar.addClassName("sidebar");

    // Logo
    FlexLayout logoArea = new FlexLayout();
    logoArea.addClassName("sidebar-logo-area");
    H1 logo = new H1("HRMS");
    logo.addClassName("sidebar-logo");
    logoArea.add(logo);

    // Nav
    FlexLayout navArea = new FlexLayout();
    navArea.setDirection(FlexDirection.COLUMN);
    navArea.addClassName("sidebar-nav");

    for (String[] item : NAV_ITEMS) {
      String path = item[0];
      String label = item[1];
      Button btn = new Button(label);
      btn.addClassName("nav-btn");
      btn.onClick(ev -> Router.getCurrent().navigate(new Location(path)));
      navButtons.put(path, btn);
      navArea.add(btn);
    }

    FlexLayout top = new FlexLayout();
    top.setDirection(FlexDirection.COLUMN);
    top.add(logoArea, navArea);

    // Logout
    Button logoutBtn = new Button("Logout");
    logoutBtn.addClassName("sidebar-logout");
    logoutBtn.setTheme(ButtonTheme.DANGER);
    logoutBtn.onClick(ev -> {
      Page.getCurrent().setAttribute("loggedIn", "");
      Router.getCurrent().navigate(new Location("/login"));
    });

    sidebar.add(top, logoutBtn);
    self.addToDrawer(sidebar);
  }

  private void setupHeader() {
    Toolbar toolbar = new Toolbar();
    toolbar.addClassName("hrms-toolbar");

    H3 company = new H3("Vivekanand Technologies");
    company.addClassName("header-company");
    toolbar.addToTitle(company);

    FlexLayout userArea = new FlexLayout();
    userArea.setAlignment(FlexAlignment.CENTER);
    userArea.addClassName("header-user-area");

    FlexLayout userInfo = new FlexLayout();
    userInfo.setDirection(FlexDirection.COLUMN);
    userInfo.setAlignment(FlexAlignment.END);

    Div nameDiv = new Div();
    nameDiv.setText("Dipak Suryawanshi");
    nameDiv.addClassName("header-user-name");

    pageLabel.addClassName("header-page-label");

    userInfo.add(nameDiv, pageLabel);

    Div avatar = new Div();
    avatar.setText("D");
    avatar.addClassName("header-avatar");

    userArea.add(userInfo, avatar);
    toolbar.addToEnd(userArea);

    self.addToHeader(toolbar);
  }

  private void onNavigate(NavigateEvent ev) {
    if (this != currentInstance) return;
    String path = ev.getLocation().getFullURI();
    if (path.isEmpty()) path = "/";
    if ("/login".equals(path)) return;

    navButtons.forEach((p, btn) -> btn.removeClassName("nav-btn-active"));

    String activeKey = path.equals("/dashboard") ? "/" : path;
    Button active = navButtons.get(activeKey);
    if (active == null) {
      for (Map.Entry<String, Button> e : navButtons.entrySet()) {
        if (!e.getKey().equals("/") && path.startsWith(e.getKey())) {
          active = e.getValue();
          break;
        }
      }
    }
    if (active != null) active.addClassName("nav-btn-active");

    // Update page label from FrameTitle annotation
    Set<Component> components = ev.getContext().getAllComponents();
    Component view = components.stream()
        .filter(c -> c.getClass().getSimpleName().endsWith("View"))
        .findFirst().orElse(null);
    if (view != null) {
      FrameTitle ft = view.getClass().getAnnotation(FrameTitle.class);
      pageLabel.setText(ft != null ? ft.value().toUpperCase() : "");
    }
  }
}
