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

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.AttributeType.Hierarchy.DefaultHierarchy;
import org.deidentifier.arx.DataType;

/**
 * This class represents a test for an oracle db with 26 attributes. 
 * To run this example a db must be availlable an the data/257k_persons.sql script be running before. The load time for 
 * this script can take up to 5 hours. So it is possible to minimize the amount of persons in the script.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersonDb26Attributes extends ExamplePerson {
	/**
	 * Entry point.
	 * 
	 * @param args the arguments
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		try {
			Data data = dbInit26Attributes();
			System.out.print("------After data PREPARATION: " + LocalDateTime.now());
			
//			setAverageReidentificationRisk();
			
			data.getDefinition().setAttributeType(ID, AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setDataType(ID, DataType.INTEGER);

			createHierarchy(data, FIRST_NAME);
			createHierarchy(data, OFFICIAL_NAME);
			createHierarchy(data, ORIGINAL_NAME);
			createHierarchy(data, ORGANISATION_NAME);
			createHierarchy(data, DEPARTMENT);
			createHierarchy(data, NATIONALITY);

			createDateAnonymization(data, DATE_OF_BIRTH);
			createDateAnonymization(data, DATE_OF_DEATH);
			createDateAnonymization(data, LAST_MEDICAL_CHECKUP);
			createDateAnonymization(data, NEXT_MEDICAL_CHECKUP);

			DefaultHierarchy sex = Hierarchy.create();
			sex.add("MALE", "FEMALE");
			sex.add("FEMALE", "MALE");
			sex.add("null", "MALE");
			sex.add("NULL", "MALE");
			data.getDefinition().setAttributeType(SEX, sex);
			data.getDefinition().setDataType(SEX, DataType.STRING);
			data.getDefinition().setHierarchy(SEX, sex);

			runAnonymization(data);
		} catch (Exception e) {
			System.out.println(e);
		} 
	}

}
