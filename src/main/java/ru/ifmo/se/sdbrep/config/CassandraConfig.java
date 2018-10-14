/*
 * MIT License
 *
 * Copyright (c) 2018 seniorkot
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.ifmo.se.sdbrep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used for Spring Data Cassandra configuration.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.contact-points}")
    private String PROPERTY_CONTACT_POINTS;

    @Value("${cassandra.port}")
    private int PROPERTY_PORT;

    @Value("${cassandra.keyspace}")
    private String PROPERTY_KEYSPACE;

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getContactPoints() {
        return PROPERTY_CONTACT_POINTS;
    }

    @Override
    protected int getPort() {
        return PROPERTY_PORT;
    }

    @Override
    protected String getKeyspaceName() {
        return PROPERTY_KEYSPACE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"ru.ifmo.se.sdbrep.domain"};
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(getKeyspaceName()).ifNotExists();
        return Collections.singletonList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
//        DropKeyspaceSpecification specification =
//                DropKeyspaceSpecification.dropKeyspace(getKeyspaceName()).ifExists();
//        return Collections.singletonList(specification);
        return Collections.singletonList(null);
    }

    @Override
    protected List<String> getStartupScripts() {
        List<String> scripts = new ArrayList<>();
        scripts.add("CREATE TABLE IF NOT EXISTS " + getKeyspaceName() +
                ".logs(log_id uuid PRIMARY KEY, action_text text, " +
                "log_time timestamp, profile_id text, project_id text);");
        return scripts;
    }
}
