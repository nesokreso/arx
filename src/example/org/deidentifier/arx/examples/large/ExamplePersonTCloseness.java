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
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.AttributeType.Hierarchy.DefaultHierarchy;
import org.deidentifier.arx.criteria.EntropyLDiversity;
import org.deidentifier.arx.criteria.HierarchicalDistanceTCloseness;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.criteria.OrderedDistanceTCloseness;
import org.deidentifier.arx.criteria.RecursiveCLDiversity;
import org.deidentifier.arx.metric.Metric;

/**
 * This class represents an example for person data anonymized with T-Closeness.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersonTCloseness extends ExamplePersonKAnonymity {
	/**
	 * Entry point.
	 */
	public static void main(String[] args) {
		try {
			// Data input can be initialized by DB or by CSV depending on the function in ExamplePerson
			Data data = csvInit26Attributes();
			System.out.println("------After data PREPARATION: " + LocalDateTime.now());
			
			data = setInsensitiveAttr(data);
			data = setQuasiIdentifierNames(data);
			createDateAnonymizationSyntactic(data, DATE_OF_BIRTH);
			
	        data.getDefinition().setAttributeType(COUNTRY_OF_ORIGIN, AttributeType.SENSITIVE_ATTRIBUTE);
//	        setKAnonymity();
	        //config.addPrivacyModel(new OrderedDistanceTCloseness(COUNTRY_OF_ORIGIN, 0.3751));
//	        config.setSuppressionLimit(0d);
	        
	        DefaultHierarchy countryOfOrigin = Hierarchy.create();
	        countryOfOrigin.add("GB","JAP","SCR","CH");
	        countryOfOrigin.add("DE","BLR","POR","SLO");
	        countryOfOrigin.add("GB","JAP","SCR","CRO");
	        countryOfOrigin.add("ITA","FR","POR","SRB");
	        countryOfOrigin.add("CH","SPA","USA","FR");
	        countryOfOrigin.add("SPA","SPA","POR","USA");
	        countryOfOrigin.add("USA","CAN","MEX","CUB");
	        countryOfOrigin.add("","CAN","MEX","CUB");
	        config = ARXConfiguration.create();
	        config.addPrivacyModel(new KAnonymity(3));
	        config.addPrivacyModel(new HierarchicalDistanceTCloseness(COUNTRY_OF_ORIGIN, 0.6d, countryOfOrigin));
	        config.setSuppressionLimit(0d);
	        config.setQualityModel(Metric.createEntropyMetric());
	        
	        runAnonymization(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
