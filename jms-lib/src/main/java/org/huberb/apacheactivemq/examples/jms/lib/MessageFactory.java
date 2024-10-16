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
package org.huberb.apacheactivemq.examples.jms.lib;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author pi
 */
public class MessageFactory {

    /**
     * Create a function for creating a {@link TextMessage} from a
     * {@link Session} when applied.
     *
     * @param textMessageAsString
     * @param messagePropertyMap
     * @return
     */
    public Function<Session, Message> createTextMessageFromSession(
            final String textMessageAsString,
            final Map<String, Object> messagePropertyMap) {
        return (Session session) -> {
            try {
                    final TextMessage textMessage = session.createTextMessage(textMessageAsString);
                    final MessagePropertyFactory messagePropertyFactory = new MessagePropertyFactory();
                    messagePropertyFactory.populateMessagePropertyWith(textMessage, messagePropertyMap);
                    return textMessage;
                } catch (JMSException jmsex) {
                    throw new AutoCloseableSupport.JMSRuntimeException(jmsex);
                }
        };
    }

    /**
     * Create a function for creating a {@link Iterator<Message>} from a
     * {@link Session} when applied.
     *
     * @param textMessagesList
     * @param messagePropertyMap
     * @return
     */
    public Function<Session, Iterator<Message>> createTextMessageIteratorFromSession(
            final List<String> textMessagesList,
            final Map<String, Object> messagePropertyMap) {

        return (Session session) -> {
            final Iterator<String> textMessageIterator = textMessagesList.iterator();
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return textMessageIterator.hasNext();
                }

                @Override
                public Message next() {
                    final String messageText = textMessageIterator.next();
                    return createTextMessageFromSession(messageText, messagePropertyMap).apply(session);
                }
            };
        };
    }

}
