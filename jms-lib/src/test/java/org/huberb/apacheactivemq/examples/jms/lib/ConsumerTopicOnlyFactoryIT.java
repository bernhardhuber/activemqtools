/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.huberb.apacheactivemq.examples.jms.lib;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pi
 */
// use junit4 @Rule
@EnableRuleMigrationSupport
public class ConsumerTopicOnlyFactoryIT {

    // use junit4 @Rule
    // use public access otherwise broker.begin/end is *not* invoked!
    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();
    //---
    ConnectionFactory connectionFactory;
    ConsumerTopicOnlyFactory instance;
    final int maxWaittimeSeconds = 10;

    public ConsumerTopicOnlyFactoryIT() {
    }

    @BeforeEach
    public void setUp() {
        assertTrue(this.broker.getBrokerService().isStarted());
        this.connectionFactory = this.broker.createConnectionFactory();
        this.instance = new ConsumerTopicOnlyFactory(connectionFactory);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of consumeTopicMessage method, of class ConsumerTopicOnlyFactory.
     */
    @Test
    public void testConsumeTopicMessage_4args() {
        final Map<String, Object> m = Collections.emptyMap();
        final String topicName = "consumeTopic_testConsumeTopicMessage_4args";
        final String jmsMessageSelector = "";
        Optional<Message> messageOpt = instance.consumeTopicMessage(m, topicName, jmsMessageSelector, 0, maxWaittimeSeconds);
        assertFalse(messageOpt.isPresent());

        assertEquals(0L, this.broker.getMessageCount(topicName));
    }

    /**
     * Test of consumeTopicMessage method, of class ConsumerTopicOnlyFactory.
     *
     * @throws javax.jms.JMSException
     */
    @Test
    public void testConsumeTopicMessage_4args_1_from_1message() throws ExecutionException, InterruptedException, JMSException {
        final Map<String, Object> m = Collections.emptyMap();
        final String topicName = "consumeTopic_testConsumeTopicMessage_4args_1_from_1message";
        final String jmsMessageSelector = "";

        final Callable<Integer> callablePushMessages = () -> {
            this.broker.pushMessage("topic://" + topicName, "message1");
            return 1;
        };

        final Callable<Optional<Message>> callableConsumer = () -> {
            return instance.consumeTopicMessage(m, topicName, jmsMessageSelector, 1, maxWaittimeSeconds);
        };
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final Future<Optional<Message>> futureOptionalMessagee = executorService.submit(callableConsumer);
        final Future<Integer> futureInteger = executorService.submit(callablePushMessages);
        for (int i = 0; i < 10; i++) {
            try {
                if (!futureOptionalMessagee.isDone()) {
                    final Optional<Message> messageOpt = futureOptionalMessagee.get(100, TimeUnit.MILLISECONDS);
                    assertTrue(messageOpt.isPresent());
                    assertEquals("message1", ((TextMessage) messageOpt.get()).getText());
                }
                if (!futureInteger.isDone()) {
                    final int countOfPushedMessages = futureInteger.get(100, TimeUnit.MILLISECONDS);
                    assertEquals(1, countOfPushedMessages);
                }
                if (futureInteger.isDone() && futureOptionalMessagee.isDone()) {
                    break;
                }
            } catch (TimeoutException toex) {
            }
        }

        assertEquals(0L, this.broker.getMessageCount(topicName));
    }

    /**
     * Test of consumeTopicMessage method, of class ConsumerTopicOnlyFactory.
     *
     * @throws javax.jms.JMSException
     */
    @Test
    public void testConsumeTopicMessage_4args_1_from_2message() throws ExecutionException, InterruptedException, JMSException{
        final Map<String, Object> m = Collections.emptyMap();
        final String topicName = "consumeTopic_testConsumeTopicMessage_4args_1_from_2message";
        final String jmsMessageSelector = "";

        final Callable<Integer> callablePushMessages = () -> {
            this.broker.pushMessage("topic://" + topicName, "message1");
            this.broker.pushMessage("topic://" + topicName, "message2");
            return 2;
        };
        final Callable<Optional<Message>> callableConsumer = () -> {
            return instance.consumeTopicMessage(m, topicName, jmsMessageSelector, 1, maxWaittimeSeconds);
        };
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final Future<Optional<Message>> futureOptionalMessagee = executorService.submit(callableConsumer);
        final Future<Integer> futureInteger = executorService.submit(callablePushMessages);
        for (int i = 0; i < 10; i++) {
            try {
                if (!futureOptionalMessagee.isDone()) {
                    final Optional<Message> messageOpt = futureOptionalMessagee.get(100, TimeUnit.MILLISECONDS);
                    assertTrue(messageOpt.isPresent());
                    assertEquals("message1", ((TextMessage) messageOpt.get()).getText());
                }
                if (!futureInteger.isDone()) {
                    final int countOfPushedMessages = futureInteger.get(100, TimeUnit.MILLISECONDS);
                    assertEquals(2, countOfPushedMessages);
                }
                if (futureInteger.isDone() && futureOptionalMessagee.isDone()) {
                    break;
                }
            } catch (TimeoutException toex) {
            }
        }
        assertEquals(0L, this.broker.getMessageCount(topicName));
    }

    /**
     * Test of consumeTopicMessage method, of class ConsumerTopicOnlyFactory.
     *
     * @throws javax.jms.JMSException
     */
    @Test
    public void testConsumeTopicMessage_4args_2_from_2message() throws ExecutionException, InterruptedException, JMSException {
        final Map<String, Object> m = Collections.emptyMap();
        final String topicName = "consumeTopic_testConsumeTopicMessage_4args_2_from_2message";
        final String jmsMessageSelector = "";
        final Callable<Integer> callablePushMessages = () -> {
            this.broker.pushMessage("topic://" + topicName, "message1");
            this.broker.pushMessage("topic://" + topicName, "message2");
            return 2;
        };
        final Callable<Optional<Message>> callableConsumer = () -> {
            return instance.consumeTopicMessage(m, topicName, jmsMessageSelector, 2, maxWaittimeSeconds);
        };
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final Future<Optional<Message>> futureOptionalMessagee = executorService.submit(callableConsumer);
        final Future<Integer> futureInteger = executorService.submit(callablePushMessages);
        for (int i = 0; i < 10; i++) {
            try {
                if (!futureOptionalMessagee.isDone()) {
                    final Optional<Message> messageOpt = futureOptionalMessagee.get(100, TimeUnit.MILLISECONDS);
                    assertTrue(messageOpt.isPresent());
                    assertEquals("message1", ((TextMessage) messageOpt.get()).getText());
                }
                if (!futureInteger.isDone()) {
                    final int countOfPushedMessages = futureInteger.get(100, TimeUnit.MILLISECONDS);
                    assertEquals(2, countOfPushedMessages);
                }
                if (futureInteger.isDone() && futureOptionalMessagee.isDone()) {
                    break;
                }
            } catch (TimeoutException toex) {
            }
        }

        assertEquals(0L, this.broker.getMessageCount(topicName));
    }

    /**
     * Test of consumeTopicMessage method, of class ConsumerTopicOnlyFactory.
     */
    @Test
    public void testConsumeTopicMessage_5args() {
    }

    /**
     * Test of peekTransactedFrom method, of class ConsumerTopicOnlyFactory.
     */
    @Test
    public void testPeekTransactedFrom() {
    }

}
