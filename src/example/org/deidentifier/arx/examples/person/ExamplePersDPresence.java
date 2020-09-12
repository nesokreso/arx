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
import org.deidentifier.arx.DataSubset;
import org.deidentifier.arx.criteria.DPresence;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.metric.Metric;

/**
 * This class represents an example for person data anonymized with δ-Presence.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersDPresence extends ExamplePerson {
	/**
	 * Entry point.
	 */
	public static void main(String[] args) {
		try {
			Data data = csvInit26AttrLarge();
			
			data = setInsensitiveAttr(data);
			data = setQuasiIdentifierNames(data);
			createDateAnonymizationSyntactic(data, DATE_OF_BIRTH);
			
			Data subsetData = csvInit26AttrSmall();
			// Define research subset
			subsetData = setInsensitiveAttr(subsetData);
			subsetData = setQuasiIdentifierNames(subsetData);
			createDateAnonymizationSyntactic(subsetData, DATE_OF_BIRTH);
			DataSubset subset = DataSubset.create(data, subsetData);
	        
	        config = ARXConfiguration.create(1d, Metric.createEntropyMetric());
	        config.addPrivacyModel(new KAnonymity(2));
	        config.addPrivacyModel(new DPresence(1d / 2d, 2d / 3d, subset));
	        runAnonymization(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
