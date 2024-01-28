package com.nullchefo.restaurantbookings.views;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.User;

import com.nullchefo.restaurantbookings.views.informational.about.AboutViewV2;
import com.nullchefo.restaurantbookings.views.informational.businessToBusiness.B2BView;
import com.nullchefo.restaurantbookings.views.informational.contacts.ContactView;
import com.nullchefo.restaurantbookings.views.informational.cookiesInfo.CookiesInfoView;
import com.nullchefo.restaurantbookings.views.informational.developerDisclaimer.DeveloperDisclaimerView;
import com.nullchefo.restaurantbookings.views.informational.disclamer.DisclaimerView;
import com.nullchefo.restaurantbookings.views.informational.faq.FaqView;
import com.nullchefo.restaurantbookings.views.informational.home.HomeView;
import com.nullchefo.restaurantbookings.views.informational.jobs.JobsView;
import com.nullchefo.restaurantbookings.views.informational.legalInformation.LegalInformationView;
import com.nullchefo.restaurantbookings.views.informational.privacyPolicy.PrivacyPolicyView;
import com.nullchefo.restaurantbookings.views.informational.termsOfService.TermsOfServiceView;
import com.nullchefo.restaurantbookings.views.starterProjectNotInUse.admindashboard.AdminDashboardView;
import com.nullchefo.restaurantbookings.views.starterProjectNotInUse.restaurantdashboard.RestaurantDashboardView;
import com.nullchefo.restaurantbookings.views.starterProjectNotInUse.restaurantview.RestaurantViewView;
import com.nullchefo.restaurantbookings.views.starterProjectNotInUse.supportchat.SupportChatView;
import com.nullchefo.restaurantbookings.views.user.editUser.EditUserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

	private H2 viewTitle;

	private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;

	public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
		this.authenticatedUser = authenticatedUser;
		this.accessChecker = accessChecker;
		// setPrimarySection(Section.DRAWER);
		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();

	}

	private void addHeaderContent() {
		DrawerToggle toggle = new DrawerToggle();
		toggle.setAriaLabel("Menu toggle");

		viewTitle = new H2();
		viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		addToNavbar(true, toggle, viewTitle);

	}

	private void addDrawerContent() {
		H1 appName = new H1("Restaurant booking");
		appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
		Header header = new Header(appName);

		Scroller scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	private SideNav createNavigation() {
		SideNav nav = new SideNav();

		//		// TODO add home, restorants, user, orders, addresses
		//		if (accessChecker.hasAccess(HelloWorldView.class)) {
		//			nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
		//
		//		}



		if (accessChecker.hasAccess(HomeView.class)) {
			nav.addItem(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.HOME_SOLID.create()));

		}


//		if (accessChecker.hasAccess(RestaurantsView.class)) {
//			nav.addItem(new SideNavItem("Restaurants", RestaurantsView.class, LineAwesomeIcon.FILTER_SOLID.create()));
//
//		}
//		if (accessChecker.hasAccess(RestaurantAddressView.class)) {
//			nav.addItem(new SideNavItem("Restaurant Address", RestaurantAddressView.class,
//										LineAwesomeIcon.MAP_MARKER_SOLID.create()));
//
//		}
//		if (accessChecker.hasAccess(CheckoutFormView.class)) {
//			nav.addItem(new SideNavItem("Checkout Form", CheckoutFormView.class, LineAwesomeIcon.CREDIT_CARD.create()));
//
//		}
//		if (accessChecker.hasAccess(CreditCardFormView.class)) {
//			nav.addItem(new SideNavItem("Credit Card Form", CreditCardFormView.class,
//										LineAwesomeIcon.CREDIT_CARD.create()));
//
//		}
//		if (accessChecker.hasAccess(RestaurantViewView.class)) {
//			nav.addItem(new SideNavItem("Restaurant View", RestaurantViewView.class,
//										LineAwesomeIcon.TH_LIST_SOLID.create()));
//
//		}
		if (accessChecker.hasAccess(AdminDashboardView.class)) {
			nav.addItem(new SideNavItem("Admin Dashboard", AdminDashboardView.class,
										LineAwesomeIcon.CHART_AREA_SOLID.create()));

		}
//		if (accessChecker.hasAccess(RestaurantDashboardView.class)) {
//			nav.addItem(new SideNavItem("Restaurant Dashboard", RestaurantDashboardView.class,
//										LineAwesomeIcon.CHART_AREA_SOLID.create()));
//
//		}
//		if (accessChecker.hasAccess(OrdersDetailView.class)) {
//			nav.addItem(
//					new SideNavItem("Orders Detail", OrdersDetailView.class, LineAwesomeIcon.COLUMNS_SOLID.create()));
//
//		}
		if (accessChecker.hasAccess(SupportChatView.class)) {
			nav.addItem(new SideNavItem("Support Chat", SupportChatView.class, LineAwesomeIcon.COMMENTS.create()));

		}
//		if (accessChecker.hasAccess(MyCustomViewView.class)) {
//			nav.addItem(new SideNavItem("My Custom View", MyCustomViewView.class,
//										LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
//
//		}

//		if (accessChecker.hasAccess(AboutView.class)) {
//			nav.addItem(new SideNavItem("About", AboutView.class,
//										LineAwesomeIcon.INFO_SOLID.create()));
//
//		}

		if (accessChecker.hasAccess(ContactView.class)) {
			nav.addItem(new SideNavItem("Contacts", ContactView.class,
										LineAwesomeIcon.ADDRESS_CARD.create()));
		}

		if (accessChecker.hasAccess(CookiesInfoView.class)) {
			nav.addItem(new SideNavItem("Cookies information", CookiesInfoView.class,
										LineAwesomeIcon.COOKIE_SOLID.create()));
		}

		if (accessChecker.hasAccess(DeveloperDisclaimerView.class)) {
			nav.addItem(new SideNavItem("Developer disclaimer", DeveloperDisclaimerView.class,
										LineAwesomeIcon.EXCLAMATION_SOLID.create()));
		}

		if (accessChecker.hasAccess(DisclaimerView.class)) {
			nav.addItem(new SideNavItem("Disclaimer", DisclaimerView.class,
										LineAwesomeIcon.EXCLAMATION_SOLID.create()));
		}

		if (accessChecker.hasAccess(FaqView.class)) {
			nav.addItem(new SideNavItem("FAQ", FaqView.class,
										LineAwesomeIcon.QUESTION_SOLID.create()));
		}

		if (accessChecker.hasAccess(LegalInformationView.class)) {
			nav.addItem(new SideNavItem("Legal information", LegalInformationView.class,
										LineAwesomeIcon.BALANCE_SCALE_SOLID.create()));
		}

		if (accessChecker.hasAccess(PrivacyPolicyView.class)) {
			nav.addItem(new SideNavItem("Privacy policy", PrivacyPolicyView.class,
										LineAwesomeIcon.USER_SECRET_SOLID.create()));
		}

		if (accessChecker.hasAccess(TermsOfServiceView.class)) {
			nav.addItem(new SideNavItem("Terms of service ", TermsOfServiceView.class,
										LineAwesomeIcon.BOOK_SOLID.create()));
		}

		if (accessChecker.hasAccess(AboutViewV2.class)) {
			nav.addItem(new SideNavItem("About", AboutViewV2.class, LineAwesomeIcon.FILE.create()));

		}

		if (accessChecker.hasAccess(JobsView.class)) {
			nav.addItem(new SideNavItem("Jobs", JobsView.class, LineAwesomeIcon.USER_NINJA_SOLID.create()));

		}

		if (accessChecker.hasAccess(B2BView.class)) {
			nav.addItem(new SideNavItem("B2B", B2BView.class, LineAwesomeIcon.EMPIRE.create()));

		}


		return nav;
	}

	private Footer createFooterForMainLayout(){
		Footer footer = new Footer();
		footer.addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.XSMALL);

		Span span = new Span("© 2021 Restaurant booking. All rights reserved.");
		span.addClassNames(LumoUtility.TextColor.TERTIARY, LumoUtility.FontSize.XSMALL);

		footer.add(span);
		return footer;
	}



	private Footer createFooter() {
		Footer layout = new Footer();

		Optional<User> maybeUser = authenticatedUser.get();
		if (maybeUser.isPresent()) {
			User user = maybeUser.get();
			String userNames = user.getFirstName() + " " + user.getLastName();

			Avatar avatar = new Avatar(userNames);
			StreamResource resource = new StreamResource(
					"profile-pic",
					() -> new ByteArrayInputStream(user.getProfilePicture()));
			avatar.setImageResource(resource);
			avatar.setThemeName("xsmall");
			avatar.getElement().setAttribute("tabindex", "-1");

			MenuBar userMenu = new MenuBar();
			userMenu.setThemeName("tertiary-inline contrast");

			MenuItem userName = userMenu.addItem("");
			Div div = new Div();
			div.add(avatar);
			div.add(userNames);
			div.add(new Icon("lumo", "dropdown"));
			div.getElement().getStyle().set("display", "flex");
			div.getElement().getStyle().set("align-items", "center");
			div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
			userName.add(div);
			// submenu

			userName.getSubMenu().addItem("Edit user", e -> {
				// See if works
				UI.getCurrent().navigate(EditUserView.class);
			});


			userName.getSubMenu().addItem("Sign out", e -> {
				authenticatedUser.logout();
			});

			layout.add(userMenu);
		} else {

			Anchor loginLink = new Anchor("login", "Sign in");
			Anchor registerLink = new Anchor("register", "Sign up");

			VerticalLayout verticalLayout = new VerticalLayout();

			verticalLayout.setSpacing(false);
			verticalLayout.setPadding(false);

			verticalLayout.add(loginLink);
			verticalLayout.add(registerLink);

			layout.add(verticalLayout);
		}

		return layout;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		viewTitle.setText(getCurrentPageTitle());
	}

	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}
}
