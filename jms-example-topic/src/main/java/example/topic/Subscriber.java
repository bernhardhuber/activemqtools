/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.topic;

import java.util.concurrent.CountDownLatch;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="http://www.christianposta.com/blog">Christian Posta</a>
 */
public class Subscriber implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

    private final CountDownLatch countDownLatch;

    public Subscriber(CountDownLatch latch) {
        countDownLatch = latch;
    }

    public static void main(String[] args) {
        final String user = Env.env("ACTIVEMQ_USER", "admin");
        final String password = Env.env("ACTIVEMQ_PASSWORD", "password");
        final String host = Env.env("ACTIVEMQ_HOST", "localhost");
        final int port = Integer.parseInt(Env.env("ACTIVEMQ_PORT", "61616"));
        final String topicName = Env.env("TOPIC", "test-topic");
        final boolean transacted = Boolean.parseBoolean(Env.env("TRANSACTED", "false"));

        String url = "tcp://" + host + ":" + port;
        if (args.length > 0) {
            url = args[0].trim();
        }

        logger.info(String.format("Running with:%n"
                + "url: %s, %n"
                + "user: %s, [ACTIVEMQ_USER]%n"
                + "password: %s, [ACTIVEMQ_PASSWORD]%n"
                + "host: %s, [ACTIVEMQ_HOST]%n"
                + "port: %d, [ACTIVEMQ_PORT]%n"
                + "topic: %s, [TOPIC]%n"
                + "transacted: %b, [TRANSACTED]%n",
                url, user, password,
                host, port,
                topicName,
                transacted
        ));

        logger.info("Waiting to receive messages... Either waiting for END message or press Ctrl+C to exit");
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        Connection connection = null;
        final CountDownLatch latch = new CountDownLatch(1);

        try {

            connection = connectionFactory.createConnection();
            connection.start();

            final Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            try {
                final Destination destination = session.createTopic(topicName);
                final MessageConsumer consumer = session.createConsumer(destination);
                try {
                    consumer.setMessageListener(new Subscriber(latch));

                    latch.await();
                } finally {
                    consumer.close();
                }
                if (transacted) {
                    session.commit();
                }
            } finally {
                session.close();
            }
        } catch (InterruptedException | JMSException e) {
            logger.warn("Caught exception!", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    logger.warn("Could not close an open connection...", e);
                }
            }
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                final String text = ((TextMessage) message).getText();
                if ("END".equalsIgnoreCase(text)) {
                    logger.info("Received END message!");
                    countDownLatch.countDown();
                } else {
                    logger.info("Received message:" + text);
                }
            }
        } catch (JMSException e) {
            logger.warn("onMessage got a JMS Exception!", e);
        }
    }

}
