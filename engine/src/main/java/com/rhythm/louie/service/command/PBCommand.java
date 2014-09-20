/*
 * PBCommand.java
 * 
 * Copyright (c) 2011 Rhythm & Hues Studios. All rights reserved.
 */

package com.rhythm.louie.service.command;

import java.util.List;

import com.google.protobuf.Message;

import com.rhythm.louie.request.RequestContext;
import com.rhythm.louie.request.data.Result;

/**
 * @author cjohnson
 * Created: Mar 17, 2011 1:53:01 PM
 */
public interface PBCommand<A,R extends Message> {
    public List<PBParamType> getArguments();
    public String getReturnType();
    public String getCommandName();
    public Result<A,R> execute(RequestContext request) throws Exception;
    public String getDescription();
    public boolean isDeprecated();
    public boolean returnList();
    public boolean isUpdate();
    public String getGroup();
    public int getGroupOrder();
    public boolean isInternal();
    public boolean isStreaming();
}