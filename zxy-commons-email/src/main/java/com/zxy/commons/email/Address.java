/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.zxy.commons.email;

/**
 * This class represents a single e-mail address.
 * 
 * <p>
 * <a href="Address.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class Address {
    // 原始的地址
    private final String srcAddress;
    private final String name;
    @SuppressWarnings("PMD.AvoidFieldNameMatchingTypeName")
    private final String address;

    public Address() {
        this("", "", "");
    }

    /**
     * Creates new address
     * 
     * @param srcAddress source address
     * @param name The name of the email address. May be <code>null</code>.
     * @param address Email address <i>localPart@domain.com</i>. May be
     *            <code>null</code>.
     */
    public Address(String srcAddress, String name, String address) {
        this.srcAddress = srcAddress;
        this.name = name;
        this.address = address;
    }

    /**
     * @return Returns the name of the mailbox or <code>null</code> if it does not have
     * a name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Returns the e-mail address ("user@example.com").
     */
    public String getAddress() {
        return this.address;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    @Override
    public String toString() {
        boolean includeAngleBrackets = (name != null);

        StringBuilder sb = new StringBuilder();

        if (name != null) {
            sb.append(name).append(' ');
        }

        if (includeAngleBrackets) {
            sb.append('<');
        }

        sb.append(address);

        if (includeAngleBrackets) {
            sb.append('>');
        }

        return sb.toString();
    }

}
