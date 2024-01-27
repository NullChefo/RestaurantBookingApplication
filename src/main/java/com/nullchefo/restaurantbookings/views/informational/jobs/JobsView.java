package com.nullchefo.restaurantbookings.views.informational.jobs;


import java.util.ArrayList;
import java.util.List;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@PageTitle("Jobs")
@Route(value = "jobs", layout = MainLayout.class)
@AnonymousAllowed
public class JobsView extends VerticalLayout {

	public JobsView() {
		addClassName("jobs-page");

		// Header
		H1 title = new H1("Explore Job Opportunities");
		title.addClassName("jobs-title");

		// Jobs Grid
		Grid<Job> jobsGrid = new Grid<>(Job.class);
		jobsGrid.setColumns("title", "company", "location", "salary");

		// Dummy job data (replace this with your actual job data)
		List<Job> jobList = createDummyJobs();
		ListDataProvider<Job> dataProvider = new ListDataProvider<>(jobList);
		jobsGrid.setDataProvider(dataProvider);

		// Add components to the layout
		add(title, jobsGrid);
	}

	// TODO make it live in db
	private List<Job> createDummyJobs() {
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job("Software Engineer", "Tech Company", "Remote", "$100,000"));
		jobs.add(new Job("Data Analyst", "Data Solutions Inc.", "Remote", "$80,000"));
		jobs.add(new Job("UX Designer", "Design Studio", "Remote", "$90,000"));
		return jobs;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Job {
		private String title;
		private String company;
		private String location;
		private String salary;

	}
}
