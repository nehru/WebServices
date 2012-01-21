

/**
 * SiteTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.site;

    /*
     *  SiteTest Junit test case
    */

    public class SiteTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testpassInfo() throws java.lang.Exception{

        ws.cmpe275.site.SiteStub stub =
                    new ws.cmpe275.site.SiteStub();//the default implementation should point to the right endpoint

           org.example.www.site.PassInfo passInfo64=
                                                        (org.example.www.site.PassInfo)getTestObject(org.example.www.site.PassInfo.class);
                    // TODO : Fill in the passInfo64 here
                
                        assertNotNull(stub.passInfo(
                        passInfo64));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testsearchSite() throws java.lang.Exception{

        ws.cmpe275.site.SiteStub stub =
                    new ws.cmpe275.site.SiteStub();//the default implementation should point to the right endpoint

           org.example.www.site.SearchSite searchSite66=
                                                        (org.example.www.site.SearchSite)getTestObject(org.example.www.site.SearchSite.class);
                    // TODO : Fill in the searchSite66 here
                
                        assertNotNull(stub.searchSite(
                        searchSite66));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testgetSite() throws java.lang.Exception{

        ws.cmpe275.site.SiteStub stub =
                    new ws.cmpe275.site.SiteStub();//the default implementation should point to the right endpoint

           org.example.www.site.GetSite getSite68=
                                                        (org.example.www.site.GetSite)getTestObject(org.example.www.site.GetSite.class);
                    // TODO : Fill in the getSite68 here
                
                        assertNotNull(stub.getSite(
                        getSite68));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testparallelInfo() throws java.lang.Exception{

        ws.cmpe275.site.SiteStub stub =
                    new ws.cmpe275.site.SiteStub();//the default implementation should point to the right endpoint

           org.example.www.site.ParallelInfo parallelInfo70=
                                                        (org.example.www.site.ParallelInfo)getTestObject(org.example.www.site.ParallelInfo.class);
                    // TODO : Fill in the parallelInfo70 here
                
                        assertNotNull(stub.parallelInfo(
                        parallelInfo70));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    