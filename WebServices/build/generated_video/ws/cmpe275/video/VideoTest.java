

/**
 * VideoTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.video;

    /*
     *  VideoTest Junit test case
    */

    public class VideoTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testgetVideo() throws java.lang.Exception{

        ws.cmpe275.video.VideoStub stub =
                    new ws.cmpe275.video.VideoStub();//the default implementation should point to the right endpoint

           org.example.www.video.GetVideo getVideo64=
                                                        (org.example.www.video.GetVideo)getTestObject(org.example.www.video.GetVideo.class);
                    // TODO : Fill in the getVideo64 here
                
                        assertNotNull(stub.getVideo(
                        getVideo64));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testparallelInfo() throws java.lang.Exception{

        ws.cmpe275.video.VideoStub stub =
                    new ws.cmpe275.video.VideoStub();//the default implementation should point to the right endpoint

           org.example.www.video.ParallelInfo parallelInfo66=
                                                        (org.example.www.video.ParallelInfo)getTestObject(org.example.www.video.ParallelInfo.class);
                    // TODO : Fill in the parallelInfo66 here
                
                        assertNotNull(stub.parallelInfo(
                        parallelInfo66));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testpassInfo() throws java.lang.Exception{

        ws.cmpe275.video.VideoStub stub =
                    new ws.cmpe275.video.VideoStub();//the default implementation should point to the right endpoint

           org.example.www.video.PassInfo passInfo68=
                                                        (org.example.www.video.PassInfo)getTestObject(org.example.www.video.PassInfo.class);
                    // TODO : Fill in the passInfo68 here
                
                        assertNotNull(stub.passInfo(
                        passInfo68));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testsearchVideo() throws java.lang.Exception{

        ws.cmpe275.video.VideoStub stub =
                    new ws.cmpe275.video.VideoStub();//the default implementation should point to the right endpoint

           org.example.www.video.SearchVideo searchVideo70=
                                                        (org.example.www.video.SearchVideo)getTestObject(org.example.www.video.SearchVideo.class);
                    // TODO : Fill in the searchVideo70 here
                
                        assertNotNull(stub.searchVideo(
                        searchVideo70));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    