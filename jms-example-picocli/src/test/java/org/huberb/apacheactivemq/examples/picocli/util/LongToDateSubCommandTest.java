/*
 * Copyright 2021 berni3.
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
package org.huberb.apacheactivemq.examples.picocli.util;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Calendar;
import org.huberb.apacheactivemq.examples.picocli.util.MainUtil.LongToDateSubCommand;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class LongToDateSubCommandTest {

    @Test
    public void testConvertLongToDate() {
        final LongToDateSubCommand instance = new LongToDateSubCommand();
        assertEquals("1970-01-01 01:00:00,1", instance.convertLongToDate(1L));

        {
            final long epochMilliSeconds = LocalDateTime.of(2021, Month.DECEMBER, 4, 22, 23, 0)
                    .toEpochSecond(ZoneOffset.UTC)
                    * 1000L;
            assertAll(
                    () -> assertEquals(1638656580000L, epochMilliSeconds),
                    () -> assertEquals("2021-12-04 23:23:00,0", instance.convertLongToDate(epochMilliSeconds))
            );
        }
        {
            final long epochMiliSeconds = new Calendar.Builder()
                    .setDate(2021, Calendar.DECEMBER, 4)
                    .setTimeOfDay(22, 23, 0, 0)
                    .build()
                    .getTime()
                    .getTime();
            assertAll(
                    () -> assertEquals(1638652980000L, epochMiliSeconds),
                    () -> assertEquals("2021-12-04 22:23:00,0", instance.convertLongToDate(epochMiliSeconds))
            );
        }
    }
}
