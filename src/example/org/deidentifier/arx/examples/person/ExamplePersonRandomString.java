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

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.ARXPopulationModel.Region;
import org.deidentifier.arx.criteria.KMap;

/**
 * This class represents an example for person data anonymized with K-Map which
 * is based on K-Anonymity.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersonRandomString extends ExamplePerson {
	/**
	 * Entry point.
	 */
	public static void main(String[] args) {
		try {
			Random r = new Random();
			char c = (char) (r.nextInt(260) + 'a');
			System.out.println(c);
			byte[] array = new byte[7]; // length is bounded by 7
			new Random().nextBytes(array);
			String generatedString = new String(array, Charset.forName("UTF-8"));

			System.out.println(generatedString);
			int length = 10;
			boolean useLetters = true;
			boolean useNumbers = false;
			String generatedString2 = RandomStringUtils.random(length, useLetters, useNumbers);
			System.out.println(generatedString2);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
