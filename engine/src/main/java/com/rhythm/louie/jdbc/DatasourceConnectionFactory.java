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
package com.rhythm.louie.jdbc;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author cjohnson
 */
public class DatasourceConnectionFactory implements ConnectionFactory {

    private final String datasource;

    public DatasourceConnectionFactory(String datasource) {
        this.datasource = datasource;
    }

    @Override
    public Connection createConnection() throws Exception {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup(datasource);
        return ds.getConnection();
    }
}
