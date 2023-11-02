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
package org.huberb.apacheactivemq.examples.jms.picocli;

import org.huberb.apacheactivemq.examples.jms.picocli.Main0;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

/**
 *
 * @author berni3
 */
public class Main0Test {

    public Main0Test() {
    }

    Main0 app;
    CommandLine cmd;
    StringWriter swOut;
    StringWriter swErr;

    @BeforeEach
    public void setUp() {
        app = new Main0();
        cmd = new CommandLine(app);

        swOut = new StringWriter();
        cmd.setOut(new PrintWriter(swOut));

        swErr = new StringWriter();
        cmd.setErr(new PrintWriter(swErr));
        //---
    }

    @ParameterizedTest
    @ValueSource(strings = {"--help", "-h"})
    public void testCommandLine_help(String helpOption) {
        //---
        final int exitCode = cmd.execute(helpOption);
        assertEquals(0, exitCode);
        assertEquals("", swErr.toString(), "stderr");
        final String swOutAsString = normalizeStripAnsiColorGraphics(swOut.toString());
        final String m = String.format("stdout helpOption %s, stdout: %s", helpOption, swOutAsString);
        assertNotEquals(0, swOutAsString, m);
        assertTrue(swOutAsString.contains("Usage:"), m);
        assertTrue(swOutAsString.contains("--command="), m);
        assertTrue(swOutAsString.contains("-c"), m);
        assertTrue(swOutAsString.contains("-h"), m);
        assertTrue(swOutAsString.contains("--help"), m);
        assertTrue(swOutAsString.contains("-V"), m);
        assertTrue(swOutAsString.contains("--version"), m);
    }

    String normalizeStripAnsiColorGraphics(String s) {
        // ESC[1;34;{...}m
        // ESC[ + digits + m
        String normalized = s.replaceAll("\\x1B\\[[0-9;]+m", "");
        return normalized;
    }

}
