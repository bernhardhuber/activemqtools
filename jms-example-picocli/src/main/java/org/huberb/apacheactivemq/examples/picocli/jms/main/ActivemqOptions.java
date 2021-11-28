/*
 * Copyright 2021 pi.
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
package org.huberb.apacheactivemq.examples.picocli.jms.main;

import picocli.CommandLine;

/**
 *
 * @author pi
 */
public class ActivemqOptions {
    //--- activemq

    @CommandLine.Option(
            names = {"--activemq-userName"},
            paramLabel = "USERNAME",
            defaultValue = "admin",
            description = "activemq userName, default value: '${DEFAULT-VALUE}'")
    private String userName;
    @CommandLine.Option(
            names = {"--activemq-password"},
            paramLabel = "PASSWORD",
            defaultValue = "password",
            description = "activemq password, default value: '${DEFAULT-VALUE}'")
    private String password;
    @CommandLine.Option(
            names = {"--activemq-brokerURL"},
            paramLabel = "BROKER_URL",
            defaultValue = "tcp://localhost:61616",
            description = "activemq brokerURL, format tcp://{host}:{port}, eg. `tcp://localost:61616', default value: '${DEFAULT-VALUE}'")
    private String brokerURL;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

}
