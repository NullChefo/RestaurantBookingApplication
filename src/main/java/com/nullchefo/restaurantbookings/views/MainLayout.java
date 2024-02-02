/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.views;

import java.util.Optional;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.UserService;
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
import com.nullchefo.restaurantbookings.views.order.list.ListOrderView;
import com.nullchefo.restaurantbookings.views.reservation.list.ListReservationsView;
import com.nullchefo.restaurantbookings.views.restaurant.RestaurantView;
import com.nullchefo.restaurantbookings.views.starterProjectNotInUse.admindashboard.AdminDashboardView;
import com.nullchefo.restaurantbookings.views.starterProjectNotInUse.supportchat.SupportChatView;
import com.nullchefo.restaurantbookings.views.user.editUser.EditUserDialog;
import com.nullchefo.restaurantbookings.views.user.listUsers.ListUserView;
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
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

	private final UserService userService;
	private H2 viewTitle;
	private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;

	public MainLayout(
			AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker,
			UserService userService) {
		this.authenticatedUser = authenticatedUser;
		this.accessChecker = accessChecker;
		this.userService = userService;
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

		if (accessChecker.hasAccess(HomeView.class)) {
			nav.addItem(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.HOME_SOLID.create()));

		}

		if (accessChecker.hasAccess(RestaurantView.class)) {
			nav.addItem(new SideNavItem(
					"Restaurants",
					RestaurantView.class,
					LineAwesomeIcon.STORE_ALT_SOLID.create()));

		}

		if (accessChecker.hasAccess(ListReservationsView.class)) {
			nav.addItem(new SideNavItem("Reservations", ListReservationsView.class, LineAwesomeIcon.CALENDAR.create()));

		}

		if (accessChecker.hasAccess(ListUserView.class)) {
			nav.addItem(new SideNavItem("Users", ListUserView.class, LineAwesomeIcon.USER.create()));

		}

		if (accessChecker.hasAccess(ListOrderView.class)) {
			nav.addItem(new SideNavItem("Orders", ListOrderView.class, LineAwesomeIcon.SHOPPING_BASKET_SOLID.create()));

		}

		if (accessChecker.hasAccess(AdminDashboardView.class)) {
			nav.addItem(new SideNavItem("Admin Dashboard", AdminDashboardView.class,
										LineAwesomeIcon.CHART_AREA_SOLID.create()));

		}

		if (accessChecker.hasAccess(SupportChatView.class)) {
			nav.addItem(new SideNavItem("Support Chat", SupportChatView.class, LineAwesomeIcon.COMMENTS.create()));

		}

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

	private Footer createFooterForMainLayout() {
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

			avatar.setImage(user.getPictureURL());

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

				EditUserDialog editUserDialog = new EditUserDialog(userService, user);
				editUserDialog.addSaveClickListener(ll -> {
					editUserDialog.close();
				});
				editUserDialog.open();

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
