

/**
 * ParallelProcTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.parallelproc;

    /*
     *  ParallelProcTest Junit test case
    */

    public class ParallelProcTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testparallelQuery() throws java.lang.Exception{

        ws.cmpe275.parallelproc.ParallelProcStub stub =
                    new ws.cmpe275.parallelproc.ParallelProcStub();//the default implementation should point to the right endpoint

           org.example.www.parallelproc.ParallelQuery parallelQuery32=
                                                        (org.example.www.parallelproc.ParallelQuery)getTestObject(org.example.www.parallelproc.ParallelQuery.class);
                    // TODO : Fill in the parallelQuery32 here
                
                        assertNotNull(stub.parallelQuery(
                        parallelQuery32));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testgetItem() throws java.lang.Exception{

        ws.cmpe275.parallelproc.ParallelProcStub stub =
                    new ws.cmpe275.parallelproc.ParallelProcStub();//the default implementation should point to the right endpoint

           org.example.www.parallelproc.GetItem getItem34=
                                                        (org.example.www.parallelproc.GetItem)getTestObject(org.example.www.parallelproc.GetItem.class);
                    // TODO : Fill in the getItem34 here
                
                        assertNotNull(stub.getItem(
                        getItem34));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    