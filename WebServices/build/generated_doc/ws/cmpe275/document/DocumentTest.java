

/**
 * DocumentTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.document;

    /*
     *  DocumentTest Junit test case
    */

    public class DocumentTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testlocalDocQuery() throws java.lang.Exception{

        ws.cmpe275.document.DocumentStub stub =
                    new ws.cmpe275.document.DocumentStub();//the default implementation should point to the right endpoint

           org.example.www.document.LocalDocQuery localDocQuery80=
                                                        (org.example.www.document.LocalDocQuery)getTestObject(org.example.www.document.LocalDocQuery.class);
                    // TODO : Fill in the localDocQuery80 here
                
                        assertNotNull(stub.localDocQuery(
                        localDocQuery80));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testsearchDocument() throws java.lang.Exception{

        ws.cmpe275.document.DocumentStub stub =
                    new ws.cmpe275.document.DocumentStub();//the default implementation should point to the right endpoint

           org.example.www.document.SearchDocument searchDocument82=
                                                        (org.example.www.document.SearchDocument)getTestObject(org.example.www.document.SearchDocument.class);
                    // TODO : Fill in the searchDocument82 here
                
                        assertNotNull(stub.searchDocument(
                        searchDocument82));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testgetDocument() throws java.lang.Exception{

        ws.cmpe275.document.DocumentStub stub =
                    new ws.cmpe275.document.DocumentStub();//the default implementation should point to the right endpoint

           org.example.www.document.GetDocument getDocument84=
                                                        (org.example.www.document.GetDocument)getTestObject(org.example.www.document.GetDocument.class);
                    // TODO : Fill in the getDocument84 here
                
                        assertNotNull(stub.getDocument(
                        getDocument84));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testpassInfo() throws java.lang.Exception{

        ws.cmpe275.document.DocumentStub stub =
                    new ws.cmpe275.document.DocumentStub();//the default implementation should point to the right endpoint

           org.example.www.document.PassInfo passInfo86=
                                                        (org.example.www.document.PassInfo)getTestObject(org.example.www.document.PassInfo.class);
                    // TODO : Fill in the passInfo86 here
                
                        assertNotNull(stub.passInfo(
                        passInfo86));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testparallelInfo() throws java.lang.Exception{

        ws.cmpe275.document.DocumentStub stub =
                    new ws.cmpe275.document.DocumentStub();//the default implementation should point to the right endpoint

           org.example.www.document.ParallelInfo parallelInfo88=
                                                        (org.example.www.document.ParallelInfo)getTestObject(org.example.www.document.ParallelInfo.class);
                    // TODO : Fill in the parallelInfo88 here
                
                        assertNotNull(stub.parallelInfo(
                        parallelInfo88));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    