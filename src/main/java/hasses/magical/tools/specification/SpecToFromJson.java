package hasses.magical.tools.specification;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class SpecToFromJson
{
   
   ObjectMapper mapper;
   public SpecToFromJson()
   {
      super();
      mapper = new ObjectMapper();
    
   }

   
     

   String toJson(Object whereSatisfies) throws JsonGenerationException, JsonMappingException, IOException {
      return mapper.writeValueAsString(whereSatisfies);
   }
   
   
   
 
   
   public ObjectMapper getObjectMapper() {
      return mapper;
   }




   public WhereSatisfies toWhereSatisfies(File file)
   {
      // TODO Auto-generated method stub
      return null;
   }
}
