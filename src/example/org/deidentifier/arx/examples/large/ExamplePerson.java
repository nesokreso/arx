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
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.DataSource;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.ARXConfiguration.SearchStepSemantics;
import org.deidentifier.arx.AttributeType.MicroAggregationFunction;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased.Order;
import org.deidentifier.arx.criteria.AverageReidentificationRisk;
import org.deidentifier.arx.criteria.EDDifferentialPrivacy;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.examples.Example;
import org.deidentifier.arx.metric.Metric;

/**
 * This class implements an example on how to use the API by directly providing
 * accessing the db (oracle).
 *
 * @author Nenad Jevdjenic
 */
public class ExamplePerson extends Example {
    /**
     * Definition of headers for input data
     */
    protected static final String ID = "ID";
    protected static final String ORGANISATION_NAME = "ORGANISATION_NAME";
    protected static final String ORGANISATION_ADDITIONAL_NAME = "ORGANISATION_ADDITIONAL_NAME";
    protected static final String DEPARTMENT = "DEPARTMENT";
    protected static final String OFFICIAL_NAME = "OFFICIAL_NAME";
    protected static final String ORIGINAL_NAME = "ORIGINAL_NAME";
    protected static final String FIRST_NAME = "FIRST_NAME";
    protected static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
    protected static final String PLACE_OF_ORIGIN_NAME = "PLACE_OF_ORIGIN_NAME";
    protected static final String SECOND_PLACE_OF_ORIGIN_NAME = "SECOND_PLACE_OF_ORIGIN_NAME";
    protected static final String PLACE_OF_BIRTH_COUNTRY = "PLACE_OF_BIRTH_COUNTRY";
    protected static final String SEX = "SEX";
    protected static final String LANGUAGE = "LANGUAGE";
    protected static final String NATIONALITY = "NATIONALITY";
    protected static final String COUNTRY_OF_ORIGIN = "COUNTRY_OF_ORIGIN";
    protected static final String DATE_OF_DEATH = "DATE_OF_DEATH";
    protected static final String REMARK = "REMARK";
    protected static final String LAST_MEDICAL_CHECKUP = "LAST_MEDICAL_CHECKUP";
    protected static final String NEXT_MEDICAL_CHECKUP = "NEXT_MEDICAL_CHECKUP";
    protected static final String PHONE_NUMBER = "PHONE_NUMBER";
    protected static final String CELL_NUMBER = "CELL_NUMBER";
    protected static final String EMAIL = "EMAIL";
    protected static final String GUARDIANSHIP = "GUARDIANSHIP";
    protected static final String CURRENT_TOWN = "CURRENT_TOWN";
    protected static final String CURRENT_ZIP_CODE = "CURRENT_ZIP_CODE";
    protected static final String MANDATOR = "MANDATOR";
    protected static DefaultData defaultData;
    protected static ARXAnonymizer anonymizer = new ARXAnonymizer();
    protected static ARXConfiguration config = ARXConfiguration.create();
    protected static ARXResult result;
    protected static final SimpleDateFormat arxFormat = new SimpleDateFormat("dd.MM.yyyy");
    protected static final String CSV_SMALL = "data/21_persons.csv";
    protected static final String CSV_LARGE = "data/257k_persons.csv";
    protected static final String ROWNUM = "100";
    protected static final String TABLE = "person_arx";
    protected static final String dbUrl = "jdbc:oracle:thin:@172.18.60.83:1521/IVZPDB";
    protected static final String dbUser = "ARX";
    protected static final String dbPw = "ARX";
    protected static boolean syntactic;
    
    
    protected static void dbInit8Attributes(Connection con) throws SQLException {
		defaultData = Data.create();
		defaultData.add(ID, ORGANISATION_NAME, ORGANISATION_ADDITIONAL_NAME, DEPARTMENT, OFFICIAL_NAME, ORIGINAL_NAME,
				FIRST_NAME, DATE_OF_BIRTH);
		Statement stmt = con.createStatement();
        System.out.println("-----Before select EXECUTION: " + LocalDateTime.now());
        ResultSet rs = stmt.executeQuery(
                "SELECT ID, ORGANISATION_NAME, ORGANISATION_ADDITIONAL_NAME, DEPARTMENT, OFFICIAL_NAME, ORIGINAL_NAME, FIRST_NAME, DATE_OF_BIRTH FROM "
                        + TABLE + " WHERE rownum <= " + ROWNUM);
        System.out.println("------After select EXECUTION: " + LocalDateTime.now());
		while (rs.next()) {
			defaultData.add(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), formatIvzDate(rs.getDate(8)));
		}
		printInput();
	}

    protected static void dbInit26Attributes(Connection con) throws SQLException {
		defaultData = Data.create();
		defaultData.add(ID, ORGANISATION_NAME, ORGANISATION_ADDITIONAL_NAME, DEPARTMENT, OFFICIAL_NAME, ORIGINAL_NAME,
				FIRST_NAME, DATE_OF_BIRTH, PLACE_OF_ORIGIN_NAME, SECOND_PLACE_OF_ORIGIN_NAME,
				PLACE_OF_BIRTH_COUNTRY, SEX, LANGUAGE, NATIONALITY, COUNTRY_OF_ORIGIN, DATE_OF_DEATH, REMARK,
				LAST_MEDICAL_CHECKUP, NEXT_MEDICAL_CHECKUP, PHONE_NUMBER, CELL_NUMBER, EMAIL, GUARDIANSHIP,
				CURRENT_TOWN, CURRENT_ZIP_CODE, MANDATOR);
		Statement stmt = con.createStatement();
        System.out.println("-----Before select EXECUTION: " + LocalDateTime.now());
        ResultSet rs = stmt.executeQuery(
                "SELECT ID, ORGANISATION_NAME, ORGANISATION_ADDITIONAL_NAME, DEPARTMENT, OFFICIAL_NAME, ORIGINAL_NAME, FIRST_NAME, DATE_OF_BIRTH, PLACE_OF_ORIGIN_NAME, "
                        + "SECOND_PLACE_OF_ORIGIN_NAME, PLACE_OF_BIRTH_COUNTRY, SEX, LANGUAGE, NATIONALITY, COUNTRY_OF_ORIGIN, DATE_OF_DEATH, REMARK, LAST_MEDICAL_CHECKUP, "
                        + "NEXT_MEDICAL_CHECKUP, PHONE_NUMBER, CELL_NUMBER, EMAIL, GUARDIANSHIP, CURRENT_TOWN, CURRENT_ZIP_CODE, MANDATOR FROM "
                        + TABLE + " WHERE rownum <= " + ROWNUM);
        System.out.println("------After select EXECUTION: " + LocalDateTime.now());
		while (rs.next()) {
			defaultData.add(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), formatIvzDate(rs.getDate(8)), rs.getString(9),
					rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
					rs.getString(15), formatIvzDate(rs.getDate(16)), rs.getString(17),
					formatIvzDate(rs.getDate(18)), formatIvzDate(rs.getDate(19)), rs.getString(20),
					rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25),
					rs.getString(26));
		}
		printInput();
	}
    
    protected static DataSource csvInit26Attributes() {
    	DataSource source;
    	// Small data input
    	//		source = DataSource.createCSVSource(CSV_SMALL, StandardCharsets.UTF_8, ';', true);
		// Large data input
		source = DataSource.createCSVSource(CSV_LARGE, StandardCharsets.UTF_8, ';', true);
		source.addColumn(ID, DataType.STRING);
		source.addColumn(ORGANISATION_NAME, DataType.STRING);
		source.addColumn(ORGANISATION_ADDITIONAL_NAME, DataType.STRING);
		source.addColumn(DEPARTMENT, DataType.STRING);
		source.addColumn(OFFICIAL_NAME, DataType.STRING);
		source.addColumn(ORIGINAL_NAME, DataType.STRING);
		source.addColumn(FIRST_NAME, DataType.STRING);
		source.addColumn(DATE_OF_BIRTH, DataType.STRING);
		source.addColumn(PLACE_OF_ORIGIN_NAME, DataType.STRING);
		source.addColumn(SECOND_PLACE_OF_ORIGIN_NAME, DataType.STRING);
		source.addColumn(PLACE_OF_BIRTH_COUNTRY, DataType.STRING);
		source.addColumn(SEX, DataType.STRING);
		source.addColumn(LANGUAGE, DataType.STRING);
		source.addColumn(NATIONALITY, DataType.STRING);
		source.addColumn(COUNTRY_OF_ORIGIN, DataType.STRING);
		source.addColumn(DATE_OF_DEATH, DataType.STRING);
		source.addColumn(REMARK, DataType.STRING);
		source.addColumn(LAST_MEDICAL_CHECKUP, DataType.STRING);
		source.addColumn(NEXT_MEDICAL_CHECKUP, DataType.STRING);
		source.addColumn(PHONE_NUMBER, DataType.STRING);
		source.addColumn(CELL_NUMBER, DataType.STRING);
		source.addColumn(EMAIL, DataType.STRING);
		source.addColumn(GUARDIANSHIP, DataType.STRING);
		source.addColumn(CURRENT_TOWN, DataType.STRING);
		source.addColumn(CURRENT_ZIP_CODE, DataType.STRING);
		source.addColumn(MANDATOR, DataType.STRING);
		return source;
	}
    
    protected static ARXConfiguration setKAnonymity() {
    	config = ARXConfiguration.create();
		config.addPrivacyModel(new KAnonymity(7));
		config.setSuppressionLimit(1d);
        config.setHeuristicSearchStepLimit(1000);
        config.setHeuristicSearchEnabled(true);
		return config;
    }
    
    protected static ARXConfiguration setAverageReidentificationRisk() {
    	config = ARXConfiguration.create();
    	config.addPrivacyModel(new AverageReidentificationRisk(0.99d));
		return config;
    }
    
    protected static ARXConfiguration setEDDifferentialPrivacy(Metric<?> metric, double epsilon, double searchBudget, double delta, int searchSteps) {
    	config = ARXConfiguration.create(1d, metric);
    	config.addPrivacyModel(new EDDifferentialPrivacy(epsilon, delta, null, true));
        config.setDPSearchBudget(searchBudget);
        config.setHeuristicSearchStepLimit(searchSteps, SearchStepSemantics.EXPANSIONS);
        defaultData.getDefinition().setResponseVariable(SEX, true);
        defaultData.getDefinition().setResponseVariable(OFFICIAL_NAME, true);
        defaultData.getDefinition().setResponseVariable(FIRST_NAME, true);
        return config;
    }
    
    protected static void runAnonymization(Data data) throws IOException {
    	System.out.println("---Before data ANONYMIZATION: " + LocalDateTime.now());
    	anonymizer = new ARXAnonymizer();
		result = anonymizer.anonymize(data, config);
		System.out.println("----After data ANONYMIZATION: " + LocalDateTime.now());
    }
    
    protected static HierarchyBuilderRedactionBased<?> createHierarchy(Data data, String attribute) {
        HierarchyBuilderRedactionBased<?> builder = HierarchyBuilderRedactionBased
                .create(Order.RIGHT_TO_LEFT, Order.RIGHT_TO_LEFT, ' ', generateRandomString());
        data.getDefinition().setAttributeType(attribute, builder);
        data.getDefinition().setDataType(attribute, DataType.STRING);
        return builder;
    }
    
    protected static void createDateAnonymizationSyntactic(Data data, String attribute) {
		createDateAnonymization(data, attribute);
		data.getDefinition().setAttributeType(attribute, MicroAggregationFunction.createArithmeticMean());
	}

    protected static void createDateAnonymization(Data data, String attribute) {
		data.getDefinition().setAttributeType(attribute, AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);
		data.getDefinition().setDataType(attribute, DataType.DATE);
	}
    
    protected static void printInput() {
		System.out.println("------------------Input data: ");
    	Iterator<String[]> inputIterator = defaultData.getHandle().iterator();
    	for(int i = 0; i < 20; i++) {
    		System.out.println(Arrays.toString(inputIterator.next()));
		}
	}
    
    protected static void printResults(Data data) {
		// Print info
		printResult(result, data);
		System.out.println();
		
		// Process results
		System.out.println("-------------Transformed data: ");
		Iterator<String[]> transformed = result.getOutput(false).iterator();
		for(int i = 0; i < 20; i++) {
			System.out.print(" ");
			System.out.println(Arrays.toString(transformed.next()));
		}
	}
    
    protected static char generateRandomString() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');
        return c;
    }

    protected static String formatIvzDate(Date date) {
        if (date == null) {
            return "null";
        } else {
            return arxFormat.format(date);
        }
    }

    protected static Date parseArxDate(String date) {
        try {
            return arxFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
