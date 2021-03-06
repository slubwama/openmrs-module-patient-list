/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.patientlist.api.util;

import org.openmrs.Privilege;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.compatibility.PrivilegeConstantsCompatibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Constants class for module privilege constants.
 */
public class PrivilegeConstants {

	private static PrivilegeConstantsCompatibility privilegeConstantsCompatibility;

	public static final String APP_VIEW_PATIENT_LIST_APP = "App: View Patient List App";
	public static final String TASK_MANAGE_PATIENT_LIST_METADATA = "Task: Manage Patient List Metadata";
	public static final String TASK_VIEW_PATIENT_LIST = "Task: View Patient List";

	public static final String[] PRIVILEGE_NAMES =
	        new String[] { APP_VIEW_PATIENT_LIST_APP, TASK_MANAGE_PATIENT_LIST_METADATA, TASK_VIEW_PATIENT_LIST };

	@Autowired
	protected PrivilegeConstants(PrivilegeConstantsCompatibility privilegeConstantsCompatibility) {
		PrivilegeConstants.privilegeConstantsCompatibility = privilegeConstantsCompatibility;
	}

	/**
	 * Gets all the privileges defined by the module.
	 * @return The module privileges.
	 */
	public static Set<Privilege> getModulePrivileges() {
		Set<Privilege> privileges = new HashSet<Privilege>(PRIVILEGE_NAMES.length);

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		for (String name : PRIVILEGE_NAMES) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;
	}

	/**
	 * Gets the default privileges needed to fully use the module.
	 * @return A set containing the default set of privileges.
	 */
	public static Set<Privilege> getDefaultPrivileges() {
		Set<Privilege> privileges = getModulePrivileges();

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		List<String> names = new ArrayList<String>();

		names.add(org.openmrs.util.PrivilegeConstants.EDIT_PATIENT_IDENTIFIERS);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_ADMIN_FUNCTIONS);
		names.add(privilegeConstantsCompatibility.GET_CONCEPTS);
		names.add(privilegeConstantsCompatibility.GET_LOCATIONS);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_NAVIGATION_MENU);
		names.add(privilegeConstantsCompatibility.GET_USERS);
		names.add(privilegeConstantsCompatibility.GET_ROLES);

		for (String name : names) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;
	}
}
