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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.DataType;

/**
 * This class represents a test for an oracle db with 8 attributes.
 * To run this example it is neccessary to have a database and run the data/257k_persons.sql script be before. The load time for 
 * this script can take up to 2 hours. So it is possible to minimize the amount of persons in the script.
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePersonDb8Attributes extends ExamplePerson {
	/**
	 * Entry point.
	 * 
	 * @param args the arguments
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPw);
		try {
			dbInit8Attributes(con);
			System.out.println("------After data PREPARATION: " + LocalDateTime.now());
			
			defaultData.getDefinition().setAttributeType(ID, AttributeType.INSENSITIVE_ATTRIBUTE);
			defaultData.getDefinition().setDataType(ID, DataType.INTEGER);
			createHierarchy(defaultData, FIRST_NAME);
			createHierarchy(defaultData, OFFICIAL_NAME);
			createHierarchy(defaultData, ORIGINAL_NAME);
			createHierarchy(defaultData, ORGANISATION_NAME);
			createHierarchy(defaultData, DEPARTMENT);
			createDateAnonymizationSyntactic(defaultData, DATE_OF_BIRTH);
			
			setKAnonymity();
			runAnonymization(defaultData);
			printResults(defaultData);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			con.close();
		}
	}

}
