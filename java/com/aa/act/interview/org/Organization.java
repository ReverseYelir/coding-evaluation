package com.aa.act.interview.org;

import java.util.Collection;
import java.util.Optional;

public abstract class Organization {

	private Position root;
	private int numEmployees = 1;
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		// search for position in the organization
		Position position = findPosition(this.root, title);
		// If position was found
		if(position != null) {
			Optional<Employee> newEmployee = Optional.of(new Employee(this.numEmployees++, person));
			position.setEmployee(newEmployee);
			return Optional.of(position);
		}
		// Only returns when desired title does not exist in our organization
		return Optional.empty();
	}
	
	/*
	 * Traverses all possible positions to find our desired position
	 * @param root - The starting position
	 * @param title - The title of the position we are searching for
	 */
	private Position findPosition(Position root, String title) {
		// If current position is our desired position
		if(root.getTitle().equals(title)) {
			return root;
		} else {
			// Traverse all the direct reporters of the current position
			for(Position position : root.getDirectReports()) {
				// Position matches title
				if(position.getTitle().equals(title)) {
					return position;
				} else {
					// Recurse to find position
					Position foundPos = findPosition(position, title);
					// Desired position was not found in subtree
					if(foundPos == null) {
						continue;
					} else {
						return foundPos;
					}
				}
			}
		}
		// Returns when the desired position was not found in current subtree
		return null;
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
