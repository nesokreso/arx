/*
 * ARX: Powerful Data Anonymization
 * Copyright 2020 Fabian Prasser and contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deidentifier.arx.examples.person;

import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.criteria.AverageReidentificationRisk;

/**
 * This class represents a test for an oracle db with 26 attributes.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersonRiskBasedAnonymization extends ExamplePerson {
	/**
	 * Entry point.
	 */
	public static void main(String[] args) {
		try {
			// Create data object
			Data data = csvInit26Attributes();

			data = setInsensitiveAttr(data);
			data = setQuasiIdentifierNames(data);

			createHierarchy(data, CURRENT_ZIP_CODE);
			createDateAnonymizationSyntactic(data, DATE_OF_BIRTH);
			createDateAnonymizationSyntactic(data, DATE_OF_DEATH);
			createDateAnonymizationSyntactic(data, LAST_MEDICAL_CHECKUP);
			createDateAnonymizationSyntactic(data, NEXT_MEDICAL_CHECKUP);

			config = ARXConfiguration.create();
			config.addPrivacyModel(new AverageReidentificationRisk(0.4d));
			runAnonymization(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
