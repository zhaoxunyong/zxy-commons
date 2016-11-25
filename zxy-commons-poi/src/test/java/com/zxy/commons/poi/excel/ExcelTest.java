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
package com.zxy.commons.poi.excel;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/** 
 * ExcelTest
 * 
 * <p>
 * <a href="ExcelTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class ExcelTest {

    private final static String TEST_FILE = "/tmp/test.xls";
    /**
     * setUp
     */
    @Before
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void setUp() {
    }

    /**
     * tearDown
     */
    @After
    public void tearDown() {
        FileUtils.deleteQuietly(new File(TEST_FILE));
    }
    
    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void exportBymap() throws IOException {
        LinkedHashMultimap<String, String> multimap = LinkedHashMultimap.create();
        // key为column， value为cell
        multimap.put("栏位1", "a1");
        multimap.put("栏位1", "a2");
        multimap.put("栏位1", "a3");
        
        multimap.put("栏位2", "b1");
        multimap.put("栏位2", "b2");
        multimap.put("栏位2", "b3");
        
        ExcelUtils.export(multimap, TEST_FILE);
        
        Assert.assertEquals(true, new File(TEST_FILE).exists());
    }
    
    @Test
    public void exportByTable() throws IOException {
        Table<Integer, String, String> table = TreeBasedTable.create();
        // rowKey为row index， columnKey为column，value为cell
        // rowKey必须从1开始
        table.put(1, "栏位1", "a1");
        table.put(1, "栏位2", "b1");

        table.put(2, "栏位1", "a2");
        table.put(2, "栏位2", "b2");

        table.put(3, "栏位1", "a3");
        table.put(3, "栏位2", "b3");

        ExcelUtils.export(table, TEST_FILE);
        
        Assert.assertEquals(true, new File(TEST_FILE).exists());
    }
    
    @Test
    public void read2table() throws IOException {
        exportByTable();
        Multimap<String, String> map = ExcelUtils.read2map(TEST_FILE);
        Assert.assertTrue(!map.isEmpty());
    }
    
    @Test
    public void read2map() throws IOException {
        exportBymap();
        Table<Integer, String, String> table = ExcelUtils.read2table(TEST_FILE);
        Assert.assertTrue(!table.isEmpty());
    }
}
