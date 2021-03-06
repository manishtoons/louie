/* 
 * Copyright 2015 Rhythm & Hues Studios.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rhythm.louie.request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhythm.louie.exception.LouieRouteException;
import com.rhythm.louie.services.auth.UnauthorizedSessionException;

/**
 *
 * @author cjohnson
 */
public class HttpProcessor {

    private final Logger LOGGER = LoggerFactory.getLogger(HttpProcessor.class);
    private final String localIp;
    private final ProtoProcess processor;

    public HttpProcessor() {
        processor = new ProtoProcessor();

        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            LOGGER.error("Error retrieving localhost IP", ex);
            ip = "UNKNOWN_ADDRESS";
        }
        localIp = ip;
    }
    
    public void processRequest(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getContentType() == null || !req.getContentType().startsWith("application/x-protobuf")) {
                throw new Exception("This servlet is not configured to deal with content of type: " + req.getContentType());
            }
            
            resp.setContentType("application/x-protobuf");
            RequestProperties props = RequestProperties.fromHttpRequest(req, localIp);
            processor.processRequest(req.getInputStream(), resp.getOutputStream(), props);
        } catch (LouieRouteException ex) {
            LOGGER.error(ex.toString());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
        } catch (UnauthorizedSessionException ex) {
            LOGGER.error(ex.toString());
            resp.sendError(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED,"Invalid Session Key");
        } catch(Exception e) {
            String errorMessage = e.getMessage()==null ? e.toString(): e.getMessage();
            LOGGER.error("Error Processing Request",e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,errorMessage);
        }
    }
}
