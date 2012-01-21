
/**
 * DocumentSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
    package ws.cmpe275.document;
    /**
     *  DocumentSkeletonInterface java skeleton interface for the axisService
     */
    public interface DocumentSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param localDocQuery
         */

        
                public org.example.www.document.LocalDocQueryResponse localDocQuery
                (
                  org.example.www.document.LocalDocQuery localDocQuery
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param searchDocument
         */

        
                public org.example.www.document.SearchDocumentResponse searchDocument
                (
                  org.example.www.document.SearchDocument searchDocument
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getDocument
         */

        
                public org.example.www.document.GetDocumentResponse getDocument
                (
                  org.example.www.document.GetDocument getDocument
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param passInfo
         */

        
                public org.example.www.document.PassInfoResponse passInfo
                (
                  org.example.www.document.PassInfo passInfo
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param parallelInfo
         */

        
                public org.example.www.document.ParallelInfoResponse parallelInfo
                (
                  org.example.www.document.ParallelInfo parallelInfo
                 )
            ;
        
         }
    