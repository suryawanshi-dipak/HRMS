package com.webforj.hrms.views;

import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.Location;

@Route(value = "/login")
@FrameTitle("Sign In")
public class LoginView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField email = new TextField();
  private PasswordField password = new PasswordField();
  private Button signInBtn = new Button("Sign in");

  private static final String VALID_USER = "admin";
  private static final String VALID_PASSWORD = "password";

  public LoginView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.setJustifyContent(FlexJustifyContent.CENTER);
    self.addClassName("login-page");

    FlexLayout card = new FlexLayout();
    card.setDirection(FlexDirection.COLUMN);
    card.addClassName("login-card");

    Div company = new Div();
    company.setText("Vivekanand Technologies");
    company.addClassName("login-company");

    H2 heading = new H2("Sign in");
    heading.addClassName("login-heading");

    Div subtitle = new Div();
    subtitle.setText("Admins, managers, HR, and employees");
    subtitle.addClassName("login-subtitle");

    email.setPlaceholder("Email address");
    email.addClassName("login-field");

    password.setPlaceholder("Password");
    password.addClassName("login-field");

    signInBtn.setTheme(ButtonTheme.PRIMARY);
    signInBtn.addClassName("login-btn");

    card.add(company, heading, subtitle, email, password, signInBtn);
    self.add(card);

    signInBtn.onClick(ev -> handleLogin());
  }

  private void handleLogin() {
    String user = email.getText() != null ? email.getText().trim() : "";
    String pass = password.getText() != null ? password.getText() : "";

    if (VALID_USER.equals(user) && VALID_PASSWORD.equals(pass)) {
      Page.getCurrent().setAttribute("loggedIn", "true");
      Page.getCurrent().setAttribute("loggedUser", "Dipak Suryawanshi");
      Router.getCurrent().navigate(new Location("/"));
    } else {
      password.setText("");
      password.focus();
      Toast.show("Invalid credentials", 2000, Theme.DANGER, Toast.Placement.TOP_RIGHT);
    }
  }
}
