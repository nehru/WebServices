

/**
 * SALVideoSearcherTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.salvideosearcher;

    /*
     *  SALVideoSearcherTest Junit test case
    */

    public class SALVideoSearcherTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testquery() throws java.lang.Exception{

        ws.cmpe275.salvideosearcher.SALVideoSearcherStub stub =
                    new ws.cmpe275.salvideosearcher.SALVideoSearcherStub();//the default implementation should point to the right endpoint

           org.example.www.salvideosearcher.Query query32=
                                                        (org.example.www.salvideosearcher.Query)getTestObject(org.example.www.salvideosearcher.Query.class);
                    // TODO : Fill in the query32 here
                
                        assertNotNull(stub.query(
                        query32));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testget() throws java.lang.Exception{

        ws.cmpe275.salvideosearcher.SALVideoSearcherStub stub =
                    new ws.cmpe275.salvideosearcher.SALVideoSearcherStub();//the default implementation should point to the right endpoint

           org.example.www.salvideosearcher.Get get34=
                                                        (org.example.www.salvideosearcher.Get)getTestObject(org.example.www.salvideosearcher.Get.class);
                    // TODO : Fill in the get34 here
                
                        assertNotNull(stub.get(
                        get34));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    