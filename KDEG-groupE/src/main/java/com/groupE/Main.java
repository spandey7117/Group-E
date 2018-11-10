package com.groupE;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.ontology.SymmetricProperty;
import org.apache.jena.ontology.TransitiveProperty;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.XSD;

public class Main {
	public static String firstDatasetOnline="http://data.geohive.ie/dumps/county/default.ttl";
	public static String firstDatasetLocal="county-default.ttl";
	public static String groupE_Ontology="resource\\groupE_Ontology.ttl";
	
	public static String link="http://group-e/ontology";
	public static String base="http://group-e/ontology";
	public static void main(String[] args) throws Exception {
		Files.copy( URI.create(firstDatasetOnline).toURL().openStream(), Paths.get(firstDatasetLocal),StandardCopyOption.REPLACE_EXISTING);
		createOntology();
		
		/*---------Amogh's UI will go down here-----------*/
	}
	private static void createOntology() throws Exception {
		OntModel model = ModelFactory.createOntologyModel();
		model.setNsPrefix("base", base);
		Ontology ontology = model.createOntology(link);
        ontology.addLabel("ontology describing different schools in Dublin", null);
        ontology.addLabel("ontology describing different schools in Dublin", "en");
        String comment = "Ontology describing different schools in Dublin";
        ontology.addComment( comment, "en");
        ontology.addComment( comment, null);
        ontology.addProperty(DCTerms.date, "23-Nov-2018");
        ontology.addProperty(DCTerms.contributor, "Aditya");
        ontology.addProperty(DCTerms.contributor, "Shubham");
        ontology.addProperty(DCTerms.contributor, "Amogh");
        ontology.addProperty(DCTerms.contributor, "Pavan");
        ontology.addProperty(DCTerms.contributor, "Paras");
        //Resource image = ontModel.createResource("https://drive.google.com/open?id=15B6U7PdfvPCvw7Kj8xIzpj4xdBl2-BL6");
        //ontology.addProperty(DCTerms.description, image);
        ontology.addProperty(DCTerms.description, comment);
        
        // GeoLocation Class
        OntClass location = model.createClass(base + "geoHive");
        location.addLabel("Locations", null);
        location.addComment("decribes counties in the country of Ireland", null);location.addComment("decribes counties in the country of Ireland", "en");
        location.addLabel("decribes counties in the country of Ireland", null);
        location.addLabel("decribes counties in the country of Ireland", "en");
        DatatypeProperty latitude = model.createDatatypeProperty(base + "latitude");
        DatatypeProperty longitude = model.createDatatypeProperty(base + "longitude");
        latitude.addLabel("latitude", null);latitude.addLabel("latitude", "en");
        longitude.addComment("longitude", null);longitude.addComment("longitude", "en");
        latitude.setDomain(location);
        latitude.setRange(XSD.xfloat);
        longitude.setDomain(location);
        longitude.setRange(XSD.xfloat);
        location.addSuperClass(model.createCardinalityRestriction(null, latitude, 1));
        location.addSuperClass(model.createCardinalityRestriction(null, longitude, 1));
        
        OntClass county = model.createClass(base + "County");
        county.addLabel("Ireland County", null);county.addLabel("Ireland County", "en");
        county.addComment("Ireland Counties", null);county.addComment("Ireland Counties", "en");
        DatatypeProperty countyArea = model.createDatatypeProperty(base + "area");
        countyArea.addLabel("area of county", null);countyArea.addLabel("area of county", "en");
        countyArea.addComment("area of a county", null);countyArea.addComment("area of a county", "en");
        countyArea.setDomain(county);
        countyArea.setRange(XSD.xfloat);
        county.addSuperClass(model.createCardinalityRestriction(null, countyArea, 1));
        SymmetricProperty nearby = model.createSymmetricProperty(base + "nearby");
        nearby.addLabel("nearby", null);nearby.addLabel("nearby", "en");
        nearby.addComment("nearby counties", null);nearby.addComment("nearby counties", "en");
        nearby.setDomain(county);
        nearby.setRange(county);
        TransitiveProperty biggerThan = model.createTransitiveProperty(base + "biggerThan");
        biggerThan.addLabel("biggerThan", null);biggerThan.addLabel("biggerThan", "en");
        biggerThan.addComment("conties having larger area than this one", null);biggerThan.addComment("conties having larger area than this one", "en");
        biggerThan.setDomain(county);
        biggerThan.setRange(county);
        TransitiveProperty smallerThan = model.createTransitiveProperty(base + "smallerThan");
        smallerThan.addLabel("smallerThan", null);smallerThan.addLabel("smallerThan", "en");
        smallerThan.addComment("conties having smallerThan area than this one", null);smallerThan.addComment("conties having smallerThan area than this one", "en");
        smallerThan.setDomain(county);
        smallerThan.setRange(county);
        biggerThan.addInverseOf(smallerThan);smallerThan.addInverseOf(biggerThan);
        /*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        OntClass primarySchoolType = model.createClass(base + "primary school type");
        primarySchoolType.addComment("primary school type",null);primarySchoolType.addComment("primary school type","en");
        primarySchoolType.addLabel("primary school type",null);primarySchoolType.addLabel("primary school type","en");
        OntClass secondarySchoolType = model.createClass(base + "secondary school type");
        secondarySchoolType.addComment("secondary school type",null);secondarySchoolType.addComment("secondary school type","en");
        secondarySchoolType.addLabel("secondary school type",null);secondarySchoolType.addLabel("secondary school type","en");
        OntClass communitySchoolType = model.createClass(base + "community school type");
        communitySchoolType.addComment("community school type",null);communitySchoolType.addComment("community school type","en");
        communitySchoolType.addLabel("community school type",null);communitySchoolType.addLabel("community school type","en");
        OntClass communityCollegeType = model.createClass(base + "community college type");
        communityCollegeType.addComment("community college type",null);communityCollegeType.addComment("community college type","en");
        communityCollegeType.addLabel("community college type",null);communityCollegeType.addLabel("community college type","en");
        OntClass type = model.createEnumeratedClass(base + "type of school", model.createList(new RDFNode[]{primarySchoolType, secondarySchoolType, communitySchoolType,communityCollegeType}));
        type.addLabel("type of school", null);type.addLabel("type of school", "en");
        type.addComment("type of school", null);type.addComment("type of school", "en");
        /*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        OntClass primarySchool = model.createClass(base + "primary school");
        primarySchool.addComment("primary school of Ireland",null);primarySchool.addComment("primary school of Ireland","en");
        primarySchool.addLabel("primary school of Ireland",null);primarySchool.addLabel("primary school of Ireland","en");
        OntClass secondarySchool = model.createClass(base + "secondary school");
        secondarySchool.addComment("secondary school of Ireland",null);secondarySchool.addComment("secondary school of Ireland","en");
        secondarySchool.addLabel("secondary school of Ireland",null);secondarySchool.addLabel("secondary school of Ireland","en");
        OntClass communitySchool = model.createClass(base + "community school");
        communitySchool.addComment("community school of Ireland",null);communitySchool.addComment("community school of Ireland","en");
        communitySchool.addLabel("community school of Ireland",null);communitySchool.addLabel("community school of Ireland","en");
        OntClass communityCollege = model.createClass(base + "community college");
        communityCollege.addComment("community college of Ireland",null);communityCollege.addComment("community college of Ireland","en");
        communityCollege.addLabel("community college of Ireland",null);communityCollege.addLabel("community college of Ireland","en");
        primarySchool.addDisjointWith(secondarySchool); primarySchool.addDisjointWith(communitySchool); primarySchool.addDisjointWith(communityCollege);
        secondarySchool.addDisjointWith(primarySchool); secondarySchool.addDisjointWith(communitySchool); secondarySchool.addDisjointWith(communityCollege);
        communitySchool.addDisjointWith(secondarySchool); communitySchool.addDisjointWith(primarySchool); communitySchool.addDisjointWith(communityCollege);
        communityCollege.addDisjointWith(secondarySchool); communityCollege.addDisjointWith(communitySchool); communityCollege.addDisjointWith(primarySchool);
        OntClass school = model.createClass(base + "school");
        school.addLabel("School", null);
        school.addComment("Ireland schools", null); school.addComment("Ireland schools", "en");
        primarySchool.addSuperClass(school);
        secondarySchool.addSuperClass(school);
        communitySchool.addSuperClass(school);
        communityCollege.addSuperClass(school);
        
        DatatypeProperty addressLine1 = model.createDatatypeProperty(base + "addressLine1");
        addressLine1.addLabel("address line 1", null);addressLine1.addLabel("address line 1", "en");
        addressLine1.addComment("address line 1", null);addressLine1.addComment("address line 1", "en");
        addressLine1.setDomain(school);
        addressLine1.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, addressLine1, 1));
        DatatypeProperty addressLine2 = model.createDatatypeProperty(base + "addressLine2");
        addressLine2.addLabel("address line 2", null);addressLine2.addLabel("address line 2", "en");
        addressLine2.addComment("address line 2", null);addressLine2.addComment("address line 2", "en");
        addressLine2.setDomain(school);
        addressLine2.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, addressLine2, 1));
        DatatypeProperty addressLine3 = model.createDatatypeProperty(base + "addressLine3");
        addressLine3.addLabel("address line 3", null);addressLine3.addLabel("address line 3", "en");
        addressLine3.addComment("address line 3", null);addressLine3.addComment("address line 3", "en");
        addressLine3.setDomain(school);
        addressLine3.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, addressLine3, 1));
        DatatypeProperty eirCode = model.createDatatypeProperty(base + "EIR code");
        eirCode.addLabel("EIR code", null);eirCode.addLabel("EIR code", "en");
        eirCode.addComment("EIR code", null);eirCode.addComment("EIR code", "en");
        eirCode.setDomain(school);
        eirCode.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, eirCode, 1));
        DatatypeProperty localAuthority = model.createDatatypeProperty(base + "Local Authority");
        localAuthority.addLabel("Local Authority", null);localAuthority.addLabel("Local Authority", "en");
        localAuthority.addComment("Local Authority", null);localAuthority.addComment("Local Authority", "en");
        localAuthority.setDomain(school);
        localAuthority.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, localAuthority, 1));
        DatatypeProperty rollNumber = model.createDatatypeProperty(base + "School Roll no");
        rollNumber.addLabel("School Roll no", null);rollNumber.addLabel("School Roll no", "en");
        rollNumber.addComment("School Roll no", null);rollNumber.addComment("School Roll no", "en");
        rollNumber.setDomain(school);
        rollNumber.setRange(XSD.xstring);
        school.addSuperClass(model.createCardinalityRestriction(null, rollNumber, 1));
        
        DatatypeProperty schoolType = model.createDatatypeProperty(base + "schoolType");
        schoolType.addLabel("type of school", null);schoolType.addLabel("type of school", "en");
        schoolType.addComment("type of school", null);schoolType.addComment("type of school", "en");
        schoolType.setDomain(school);
        schoolType.setRange(type);
        school.addSuperClass(model.createCardinalityRestriction(null, schoolType, 1));
        
        primarySchool.addSuperClass(model.createHasValueRestriction(null, schoolType, primarySchoolType));
        secondarySchool.addSuperClass(model.createHasValueRestriction(null, schoolType, secondarySchoolType));
        communityCollege.addSuperClass(model.createHasValueRestriction(null, schoolType, communityCollegeType));
        communitySchool.addSuperClass(model.createHasValueRestriction(null, schoolType, communitySchoolType));
        
        ObjectProperty hasSchools = model.createObjectProperty(base + "hasSchools");
        hasSchools.addLabel("county has this school", null);hasSchools.addLabel("county has this school", "en");
        hasSchools.addComment("county has this school", null);hasSchools.addComment("county has this school", "en");
        hasSchools.setDomain(county);
        hasSchools.setRange(school);
        ObjectProperty inCounty = model.createObjectProperty(base + "inCounty");
        inCounty.addLabel("this school is in this particular county area", null);inCounty.addLabel("this school is in this particular county area", "en");
        inCounty.addComment("this school is in this particular county area", null);inCounty.addComment("this school is in this particular county area", "en");
        inCounty.setDomain(school);
        inCounty.setRange(county);
        hasSchools.addInverseOf(inCounty);inCounty.addInverseOf(hasSchools);
        
        //Ontology completed -- write to file
        model.write(new FileWriter(groupE_Ontology), "TURTLE");
        System.out.println("Turtle file for groupE_Ontology created");
	}

}
