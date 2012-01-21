

/**
 * LookupTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.lookup;

    /*
     *  LookupTest Junit test case
    */

    public class LookupTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testsearchLookup() throws java.lang.Exception{

        ws.cmpe275.lookup.LookupStub stub =
                    new ws.cmpe275.lookup.LookupStub();//the default implementation should point to the right endpoint

           org.example.www.lookup.SearchLookup searchLookup16=
                                                        (org.example.www.lookup.SearchLookup)getTestObject(org.example.www.lookup.SearchLookup.class);
                    // TODO : Fill in the searchLookup16 here
                
                        assertNotNull(stub.searchLookup(
                        searchLookup16));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    