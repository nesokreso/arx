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

package org.deidentifier.arx.examples.large;

import java.io.IOException;
import java.time.LocalDateTime;

import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataType;

/**
 * This class represents a test for an oracle db with 26 attributes.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersonKAnonymity26Attributes extends ExamplePerson {
	/**
	 * Entry point.
	 * 
	 * @param args the arguments
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try {
			// Create data object
			Data data = csvInit26Attributes();
			System.out.println("------After data PREPARATION: " + LocalDateTime.now());

			data.getDefinition().setAttributeType(ID, AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setDataType(ID, DataType.INTEGER);

			createHierarchy(data, ORGANISATION_NAME);
			createHierarchy(data, ORGANISATION_ADDITIONAL_NAME);
			createHierarchy(data, DEPARTMENT);
			createHierarchy(data, OFFICIAL_NAME);
			createHierarchy(data, ORIGINAL_NAME);
			createHierarchy(data, FIRST_NAME);
			createHierarchy(data, PLACE_OF_ORIGIN_NAME);
			createHierarchy(data, SECOND_PLACE_OF_ORIGIN_NAME);
			createHierarchy(data, PLACE_OF_BIRTH_COUNTRY);
			createHierarchy(data, SEX);
			createHierarchy(data, LANGUAGE);
			createHierarchy(data, NATIONALITY);
			createHierarchy(data, COUNTRY_OF_ORIGIN);
			createHierarchy(data, REMARK);
			createHierarchy(data, PHONE_NUMBER);
			createHierarchy(data, CELL_NUMBER);
			createHierarchy(data, EMAIL);
			createHierarchy(data, GUARDIANSHIP);
			createHierarchy(data, CURRENT_TOWN);
			createHierarchy(data, MANDATOR);

			createHierarchy(data, CURRENT_ZIP_CODE);
			createDateAnonymizationSyntactic(data, DATE_OF_BIRTH);
			createDateAnonymizationSyntactic(data, DATE_OF_DEATH);
			createDateAnonymizationSyntactic(data, LAST_MEDICAL_CHECKUP);
			createDateAnonymizationSyntactic(data, NEXT_MEDICAL_CHECKUP);

//			setEDDifferentialPrivacy(Metric.createClassificationMetric(), 2d, 1d, 1E-5d, 5);
//			setEDDifferentialPrivacy(Metric.createClassificationMetric(), 2d, 1d, 0.1, 5);
//			setAverageReidentificationRisk();
			setKAnonymity();
			runAnonymization(data);
			printResults(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
