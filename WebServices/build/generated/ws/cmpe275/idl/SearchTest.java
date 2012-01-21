

/**
 * SearchTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.idl;

    /*
     *  SearchTest Junit test case
    */

    public class SearchTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testgetItem() throws java.lang.Exception{

        ws.cmpe275.idl.SearchStub stub =
                    new ws.cmpe275.idl.SearchStub();//the default implementation should point to the right endpoint

           org.example.www.demo.GetItem getItem80=
                                                        (org.example.www.demo.GetItem)getTestObject(org.example.www.demo.GetItem.class);
                    // TODO : Fill in the getItem80 here
                
                        assertNotNull(stub.getItem(
                        getItem80));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testparallelInfo() throws java.lang.Exception{

        ws.cmpe275.idl.SearchStub stub =
                    new ws.cmpe275.idl.SearchStub();//the default implementation should point to the right endpoint

           org.example.www.demo.ParallelInfo parallelInfo82=
                                                        (org.example.www.demo.ParallelInfo)getTestObject(org.example.www.demo.ParallelInfo.class);
                    // TODO : Fill in the parallelInfo82 here
                
                        assertNotNull(stub.parallelInfo(
                        parallelInfo82));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testpassInfo() throws java.lang.Exception{

        ws.cmpe275.idl.SearchStub stub =
                    new ws.cmpe275.idl.SearchStub();//the default implementation should point to the right endpoint

           org.example.www.demo.PassInfo passInfo84=
                                                        (org.example.www.demo.PassInfo)getTestObject(org.example.www.demo.PassInfo.class);
                    // TODO : Fill in the passInfo84 here
                
                        assertNotNull(stub.passInfo(
                        passInfo84));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testregisterImage() throws java.lang.Exception{

        ws.cmpe275.idl.SearchStub stub =
                    new ws.cmpe275.idl.SearchStub();//the default implementation should point to the right endpoint

           org.example.www.demo.RegisterImage registerImage86=
                                                        (org.example.www.demo.RegisterImage)getTestObject(org.example.www.demo.RegisterImage.class);
                    // TODO : Fill in the registerImage86 here
                
                        assertNotNull(stub.registerImage(
                        registerImage86));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testsearch() throws java.lang.Exception{

        ws.cmpe275.idl.SearchStub stub =
                    new ws.cmpe275.idl.SearchStub();//the default implementation should point to the right endpoint

           org.example.www.demo.Search search88=
                                                        (org.example.www.demo.Search)getTestObject(org.example.www.demo.Search.class);
                    // TODO : Fill in the search88 here
                
                        assertNotNull(stub.search(
                        search88));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    