package com.in28minutes.rest.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

//필드 이름까지 바꿔야하는 단점
//@JsonIgnoreProperties({"field1", "field2"}) // This annotation can be used to ignore specific fields globally
@JsonFilter("SomeBeanFilter") // This annotation is used to filter fields dynamically
public class SomeBean {
    private String field1;
//    @JsonIgnore //JsonIgnoreProperties 보다 선호됨
    private String field2;
    private String field3;

    public SomeBean(
            String field1,
            String field2,
            String field3
    ) {
        super();
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField3() {
        return field3;
    }

    public String getField2() {
        return field2;
    }

    public String getField1() {
        return field1;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                '}';
    }
}
