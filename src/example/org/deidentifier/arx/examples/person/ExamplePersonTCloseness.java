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

import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.AttributeType.Hierarchy.DefaultHierarchy;
import org.deidentifier.arx.criteria.EqualDistanceTCloseness;
import org.deidentifier.arx.criteria.HierarchicalDistanceTCloseness;
import org.deidentifier.arx.criteria.OrderedDistanceTCloseness;

/**
 * This class represents an example for person data anonymized with the T-Closeness privacy model.
 * 
 * @author Nenad Jevdjenic
 */
public class ExamplePersonTCloseness extends ExamplePersonKAnonymity {
	/**
	 * Entry point.
	 */
	public static void main(String[] args) {
		try {
			Data data = csvInit26AttrLarge();
			data = prepareAttributesKAnonymity(data);
			setKAnonymity();

			DefaultHierarchy countryHierarchy = createHierarchyCountry(data, COUNTRY_OF_ORIGIN);
	        data.getDefinition().setAttributeType(COUNTRY_OF_ORIGIN, AttributeType.SENSITIVE_ATTRIBUTE);
	        config.addPrivacyModel(new HierarchicalDistanceTCloseness(COUNTRY_OF_ORIGIN, 0.5, countryHierarchy));
			data.getDefinition().setAttributeType(PHONE_NUMBER, AttributeType.SENSITIVE_ATTRIBUTE);
			config.addPrivacyModel(new OrderedDistanceTCloseness(PHONE_NUMBER, 0.5));
			data.getDefinition().setAttributeType(PLACE_OF_BIRTH_COUNTRY, AttributeType.SENSITIVE_ATTRIBUTE);
			config.addPrivacyModel(new EqualDistanceTCloseness(PLACE_OF_BIRTH_COUNTRY, 0.5));

			runAnonymization(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
