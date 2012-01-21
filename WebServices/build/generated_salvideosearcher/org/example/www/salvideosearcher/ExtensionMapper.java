
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:47 EDT)
 */

            package org.example.www.salvideosearcher;
            /**
            *  ExtensionMapper class
            */
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://www.example.org/SALVideoSearcher/".equals(namespaceURI) &&
                  "timeRange".equals(typeName)){
                   
                            return  org.example.www.salvideosearcher.TimeRange.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.example.org/SALVideoSearcher/".equals(namespaceURI) &&
                  "element".equals(typeName)){
                   
                            return  org.example.www.salvideosearcher.Element.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.example.org/SALVideoSearcher/".equals(namespaceURI) &&
                  "locationRange".equals(typeName)){
                   
                            return  org.example.www.salvideosearcher.LocationRange.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    