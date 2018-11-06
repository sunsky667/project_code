package com.sunsky.spring.xmlRules;

/**
 *
 * ioc XML file configuration rule
 * xml file define a bean need use this rules
 *
 */
public enum IocRules {

    BEAN_RULE("bean","id","class"),
    SCAN_RULE("component-scan","base-package","null"),
    /**set inject rules**/
    SET_INJECT("property","name","value"),
    /**construct inject rules, use construct inject must define order**/
    CONSTRUCT_INJECT("constructor-arg","value","index");

    private String type;
    private String name;
    private String value;

    IocRules(String property,String name,String value){
        this.type = property;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
