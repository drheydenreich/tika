/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.parser.pkg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Parser for various packaging and compression formats. Package entries will
 * be written to the XHTML event stream as &lt;div class="package-entry"&gt;
 * elements that contain the (optional) entry name as a &lt;h1&gt; element
 * and the full structured body content of the parsed entry.
 */
public class PackageParser extends AbstractParser {

    /** Serial version UID */
    private static final long serialVersionUID = -5331043266963888708L;

    private static final Set<MediaType> SUPPORTED_TYPES =
        Collections.unmodifiableSet(new HashSet<MediaType>(Arrays.asList(
                MediaType.application("x-archive"),
                MediaType.application("x-bzip"),
                MediaType.application("x-bzip2"),
                MediaType.application("x-cpio"),
                MediaType.application("x-gtar"),
                MediaType.application("x-gzip"),
                MediaType.application("x-tar"),
                MediaType.application("zip"))));

    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return SUPPORTED_TYPES;
    }

    public void parse(
            InputStream stream, ContentHandler handler,
            Metadata metadata, ParseContext context)
            throws IOException, SAXException, TikaException {
        new PackageExtractor(handler, metadata, context).parse(stream);
    }

}
