/*
 * TestService.java
 * 
 * Copyright (c) 2014 Rhythm & Hues Studios. All rights reserved.
 */

package com.rhythm.louie.testservice.jms;

import com.rhythm.louie.Service;

/**
 *
 * @author cjohnson
 */
@Service
public interface JmsTestService {
    /**
     * Generates a message using the configured JMS adapter,
     * and the same delegate should receive that message
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    public String messageTest(String message) throws Exception;
}

