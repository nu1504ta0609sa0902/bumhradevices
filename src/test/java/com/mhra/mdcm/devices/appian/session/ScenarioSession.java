package com.mhra.mdcm.devices.appian.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * For each scenario this will be reset 
 * 
 * Share data between steps and pass to page objects
 * @author TPD_Auto
 *
 */
@Component
@Scope("cucumber-glue")
public class ScenarioSession {

    private Map<String, Object> sessionData = new HashMap<String, Object>();

    public void putData(String key, Object value) {
        sessionData.put(key, value);
    }

    public Object getData(String key) {
        return sessionData.get(key);
    }
}