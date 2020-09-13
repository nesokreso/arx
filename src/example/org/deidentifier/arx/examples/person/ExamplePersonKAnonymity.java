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
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased.Order;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.metric.Metric;

/**
 * This class represents an example for person data anonymized with K-Anonymity privacy model.
 * 
 * @author Nenad Jevdjenic
 */
public class ExamplePersonKAnonymity extends ExamplePerson {
	/**
	 * Entry point.
	 */
	public static void main(String[] args) {
		try {
			Data data = csvInit26AttrSmall();
			data = setInsensitiveAttr(data);
			createHierarchy(data, ORGANISATION_NAME, DataType.STRING);
			createHierarchy(data, ORGANISATION_ADDITIONAL_NAME, DataType.STRING);
			createHierarchy(data, DEPARTMENT, DataType.STRING);
			createHierarchy(data, OFFICIAL_NAME, DataType.STRING);
			createHierarchy(data, ORIGINAL_NAME, DataType.STRING);
			createHierarchy(data, FIRST_NAME, DataType.STRING);
//			createHierarchy(data, DATE_OF_BIRTH, DataType.DATE, true);
			createHierarchy(data, PLACE_OF_ORIGIN_NAME, DataType.STRING);
			createHierarchy(data, SECOND_PLACE_OF_ORIGIN_NAME, DataType.STRING);
			createHierarchy(data, PLACE_OF_BIRTH_COUNTRY, DataType.STRING);
			createHierarchy(data, SEX, DataType.STRING);
			createHierarchy(data, LANGUAGE, DataType.STRING);
			createHierarchy(data, NATIONALITY, DataType.STRING);
			createHierarchy(data, COUNTRY_OF_ORIGIN, DataType.STRING);
//			createHierarchy(data, DATE_OF_DEATH, DataType.DATE, true);
			createHierarchy(data, REMARK, DataType.STRING);
//			createHierarchy(data, LAST_MEDICAL_CHECKUP, DataType.DATE, true);
//			createHierarchy(data, NEXT_MEDICAL_CHECKUP, DataType.DATE, true);
			createHierarchy(data, PHONE_NUMBER, DataType.STRING);
			createHierarchy(data, CELL_NUMBER, DataType.STRING);
			createHierarchy(data, EMAIL, DataType.STRING);
			createHierarchy(data, GUARDIANSHIP, DataType.STRING);
			createHierarchy(data, CURRENT_TOWN, DataType.STRING);
//			createHierarchy(data, CURRENT_ZIP_CODE, DataType.INTEGER, true);
			createHierarchy(data, MANDATOR, DataType.STRING);
//			data = setQuasiIdentifierNames(data);
//			createHierarchy(data, OFFICIAL_NAME, DataType.STRING);
//			createHierarchySex(data);
			createHierarchyCountry(data, COUNTRY_OF_ORIGIN);
			createHierarchyCountry(data, NATIONALITY);
//			createHierarchyLanguage(data, LANGUAGE);
			data = setQuasiIdentifierDates(data);
			config = ARXConfiguration.create();
			config.addPrivacyModel(new KAnonymity(4));
	        config.setSuppressionLimit(0d);
	        config.setQualityModel(Metric.createEntropyMetric());
			runAnonymization(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static HierarchyBuilderRedactionBased<?> createHierarchy() {
        HierarchyBuilderRedactionBased<?> builderOfficialName = HierarchyBuilderRedactionBased
                .create(Order.RIGHT_TO_LEFT, Order.RIGHT_TO_LEFT, ' ', generateRandomString());
        return builderOfficialName;
    }
}
