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

import java.time.LocalDateTime;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.metric.Metric;

/**
 * This class represents an example for person data anonymized with K-Anonymity.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersonKAnonymity extends ExamplePerson {
	/**
	 * Entry point.
	 */
	public static void main(String[] args) {
		try {
			// Create data object
			Data data = dbInit26Attributes();
			System.out.println("------After data PREPARATION: " + LocalDateTime.now());

			data = setInsensitiveAttr(data);
			data = setQuasiIdentifierNames(data);

			createHierarchy(data, CURRENT_ZIP_CODE);
			createDateAnonymizationSyntactic(data, DATE_OF_BIRTH);
			createDateAnonymizationSyntactic(data, DATE_OF_DEATH);
			createDateAnonymizationSyntactic(data, LAST_MEDICAL_CHECKUP);
			createDateAnonymizationSyntactic(data, NEXT_MEDICAL_CHECKUP);

			setKAnonymity();
			runAnonymization(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	protected static ARXConfiguration setKAnonymity() {
		config = ARXConfiguration.create();
		config.addPrivacyModel(new KAnonymity(2));
		config.setSuppressionLimit(1d);
		config.setQualityModel(Metric.createEntropyMetric());
		return config;
	}
}
