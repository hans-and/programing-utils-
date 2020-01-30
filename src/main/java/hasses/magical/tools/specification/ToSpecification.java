package hasses.magical.tools.specification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "satisfies")
@JsonSubTypes({
      @JsonSubTypes.Type(value = TheCondition.class ,name = "the"),      
      @JsonSubTypes.Type(value = AllOfThe.class,name="all-of-the"),
      @JsonSubTypes.Type(value = AnyOfThe.class,name="any-of-the"),
      @JsonSubTypes.Type(value = ListContains.class,name="list-contains")
      })

public abstract class ToSpecification {
   public  abstract ISpecification<Object> toSpecification();
}
