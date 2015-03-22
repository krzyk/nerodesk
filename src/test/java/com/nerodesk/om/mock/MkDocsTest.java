/**
 * Copyright (c) 2015, nerodesk.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the nerodesk.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.nerodesk.om.mock;

import java.io.File;
import org.apache.commons.io.IOUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Tests for {@code MkDocs}.
 * @author Yuriy Alevohin (alevohin@mail.ru)
 * @version $Id$
 * @since 0.2
 */
public final class MkDocsTest {

    /**
     * Temporary folder.
     * @checkstyle VisibilityModifierCheck (5 lines)
     */
    @Rule
    public final transient TemporaryFolder temp = new TemporaryFolder();

    /**
     * MkDocs can return a list of docs.
     * @throws Exception If fails.
     * @todo 90:30min MkDocs creates files in directory
     *  `new File(this.dir, this.user)` (see MkDoc). Method `names`
     *  receives file's list from another directory (just dir). Don't
     *  forget remove @Ignore after fix.
     */
    @Test
    @Ignore
    public void returnsNames() throws Exception {
        final String[] files = new String[] {"a.txt", "b.txt"};
        final File root = this.temp.newFolder();
        final MkDocs docs = new MkDocs(root, "user1");
        for (final String file : files) {
            docs.doc(file).write(IOUtils.toInputStream("content1"));
        }
        MatcherAssert.assertThat(
            docs.names(),
            Matchers.contains(files)
        );
    }

    /**
     * MkDocs can return existed Doc.
     * @throws Exception If fails.
     */
    @Test
    public void returnsExistedDoc() throws Exception {
        final File root = this.temp.newFolder();
        final MkDocs docs = new MkDocs(root, "user2");
        final String filename = "test2";
        docs.doc(filename).write(IOUtils.toInputStream("content2"));
        MatcherAssert.assertThat(
            docs.doc(filename).exists(),
            Matchers.is(true)
        );
    }
}
