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
package com.nerodesk.om.aws;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jcabi.log.Logger;
import com.jcabi.s3.Bucket;
import com.jcabi.s3.Ocket;
import com.nerodesk.om.Doc;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * AWS-based version of Doc.
 *
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 0.2
 */
@ToString
@EqualsAndHashCode
public final class AwsDoc implements Doc {

    /**
     * Bucket.
     */
    private final transient Bucket bucket;

    /**
     * URN of user.
     */
    private final transient String user;

    /**
     * Doc name.
     */
    private final transient String label;

    /**
     * Ctor.
     * @param bkt Bucket
     * @param urn User URN
     * @param doc Name of document
     */
    public AwsDoc(final Bucket bkt, final String urn, final String doc) {
        this.bucket = bkt;
        this.user = urn;
        this.label = doc;
    }

    @Override
    public boolean exists() throws IOException {
        return this.ocket().exists();
    }

    @Override
    public void delete() throws IOException {
        this.bucket.remove(this.ocket().key());
    }

    @Override
    public void read(@NotNull final OutputStream output) throws IOException {
        this.ocket().read(output);
        Logger.info(this, "%s read", this.label);
    }

    @Override
    public void write(final InputStream input) throws IOException {
        this.ocket().write(input, new ObjectMetadata());
        Logger.info(this, "%s written", this.label);
    }

    /**
     * Get ocket.
     *
     * <p>Name of the resource will always start with a "slash", that's
     * why this concatenation will always produce unique names of
     * objects in S3 bucket.
     *
     * @return Ocket
     */
    private Ocket ocket() {
        return this.bucket.ocket(
            String.format("/%s%s", this.user, this.label)
        );
    }

}
