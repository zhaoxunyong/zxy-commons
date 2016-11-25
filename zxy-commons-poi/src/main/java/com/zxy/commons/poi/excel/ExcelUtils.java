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

import static com.google.common.base.Preconditions.checkArgument;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/** 
 * ExcelUtils
 * 
 * <p>
 * <a href="ExcelUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public final class ExcelUtils {
    
    private ExcelUtils() {}
    
    /**
     * export excel
     * 
     * @param sheetName sheet name
     * @param table table
     * @return Workbook
    */
    @SuppressWarnings("PMD.ShortVariable")
    private static Workbook exportExcel(String sheetName, Table<Integer, String, String> table) {
        Set<Integer> tableRows = table.rowKeySet();
        Set<String> tableColumns = table.columnKeySet();
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(sheetName);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        /*for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }*/

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f1 = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f1.setFontHeightInPoints((short) 10);
        f1.setColor(IndexedColors.BLACK.getIndex());
        f1.setBold(true);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // Font f3=wb.createFont();
        // f3.setFontHeightInPoints((short) 10);
        // f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f1);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setAlignment(HorizontalAlignment.CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(BorderStyle.THIN);
        cs2.setBorderRight(BorderStyle.THIN);
        cs2.setBorderTop(BorderStyle.THIN);
        cs2.setBorderBottom(BorderStyle.THIN);
        cs2.setAlignment(HorizontalAlignment.CENTER);
        // 设置列名
        int i=0;
        for(String tableColumn : tableColumns) {
            Cell cell = row.createCell(i);
            cell.setCellValue(tableColumn);
            cell.setCellStyle(cs);
            i++;
        }
        // 设置每行每列的值
        for(Integer tableRow:tableRows){
         // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            checkArgument(tableRow > 0, "Row index must be greater than zero!");
            Row row1 = sheet.createRow(tableRow);
            // 在row行上创建一个方格
            Map<String,String> item = table.row(tableRow);
            int j = 0;
            for(Map.Entry<String, String> entry : item.entrySet()) {
//            for(String v:item.keySet()){
//                System.out.println(tableRow + "-" + v + "-" + item.get(v));
                Cell cell = row1.createCell(j);
                cell.setCellValue(entry.getValue());
                cell.setCellStyle(cs2);
                j ++;
            }
        }
        return wb;
    }
    
    /**
     * export
     * 
     * @param table table
     * @param outputPath output path
     * @throws IOException IOException
     */
    public static void export(Table<Integer, String, String> table, String outputPath) throws IOException {
        export("Sheet0", table, outputPath);
    }
    
    /**
     * export
     * 
     * @param sheetName sheet name
     * @param table table
     * @param outputPath output path
     * @throws IOException IOException
     */
    public static void export(String sheetName, Table<Integer, String, String> table, String outputPath) throws IOException {
        Workbook wb = ExcelUtils.exportExcel(sheetName, table);
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputPath);
            wb.write(out);
        } finally {
            if(out != null) {
                out.close();
            }
        }
    }
    
    /**
     * export
     * 
     * @param multimap table
     * @param outoutPath output path
     * @throws IOException IOException
     */
    public static void export(Multimap<String, String> multimap, String outoutPath) throws IOException {
        export("Sheet0", multimap, outoutPath);
    }
    
    /**
     * export excel
     * 
     * @param sheetName sheet name
     * @param multimap table
     * @param outoutPath output path
     * @throws IOException IOException
     */
    public static void export(String sheetName, Multimap<String, String> multimap, String outoutPath) throws IOException {
        Table<Integer, String, String> table = map2table(multimap);
        export(sheetName, table, outoutPath);
    }
    
    /**
     * 读取Excel的第一个sheet内容
     * 
     * @param inputPath 读取数据的源Excel
     * @return 读出的Excel中数据的内容
     * @throws IOException IOException
     */
    @SuppressWarnings("PMD.AvoidBranchingStatementAsLastInLoop")
    public static Multimap<String, String> read2map(String inputPath) throws IOException {
        Map<String, Multimap<String, String>> tables = readAll2map(inputPath);
        checkArgument(tables!=null && !tables.isEmpty(), "Table must not be empty!");
        Multimap<String, String> map = null;
        for(Map.Entry<String, Multimap<String, String>> entry : tables.entrySet()) {
            map = entry.getValue();
            break;
        }
        return map;
    }
    
    /**
     * 读取Excel的第一个sheet内容
     * 
     * @param inputPath 读取数据的源Excel
     * @return 读出的Excel中数据的内容
     * @throws IOException IOException
     */
    @SuppressWarnings("PMD.AvoidBranchingStatementAsLastInLoop")
    public static Table<Integer, String, String> read2table(String inputPath) throws IOException {
        Map<String, Table<Integer, String, String>> tables = readAll2table(inputPath);
        checkArgument(tables!=null && !tables.isEmpty(), "Table must not be empty!");
        Table<Integer, String, String> table = null;
        for(Map.Entry<String, Table<Integer, String, String>> entry : tables.entrySet()) {
            table = entry.getValue();
            break;
        }
        return table;
    }
    
    /**
     * 读取Excel的第一个sheet内容
     * 
     * @param inputPath 读取数据的源Excel
     * @return 读出的Excel中数据的内容
     * @throws IOException IOException
     */
    public static Map<String, Multimap<String, String>> readAll2map(String inputPath) throws IOException {
        Map<String, Table<Integer, String, String>> tables = readAll2table(inputPath);
        checkArgument(tables!=null && !tables.isEmpty(), "Table must not be empty!");
        Map<String, Multimap<String, String>> multimaps = Maps.newLinkedHashMap();
        for(Map.Entry<String, Table<Integer, String, String>> entry : tables.entrySet()) {
            multimaps.put(entry.getKey(), table2map(entry.getValue()));
        }
        return multimaps;
    }
    
    /**
     * 读取Excel的所有的sheet内容
     * 
     * @param inputPath 读取数据的源Excel
     * @return 读出的Excel中数据的内容
     * @throws IOException IOException
     */
    public static Map<String, Table<Integer, String, String>> readAll2table(String inputPath) throws IOException {
        Map<String, Table<Integer, String, String>> tables = Maps.newLinkedHashMap();
        FileInputStream inputStream = null;
        HSSFWorkbook wb = null;
        try {
            inputStream = new FileInputStream(inputPath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            // 打开HSSFWorkbook
            POIFSFileSystem fs = new POIFSFileSystem(bufferedInputStream);
            wb = new HSSFWorkbook(fs);
            List<String> columnNames = Lists.newLinkedList();
            for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
                Table<Integer, String, String> table = TreeBasedTable.create();
                HSSFSheet st = wb.getSheetAt(sheetIndex);
                String sheetName = st.getSheetName();
                for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
                    HSSFRow row = st.getRow(rowIndex);
                    for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                        HSSFCell cell = row.getCell(columnIndex);
                        if (cell != null) {
                            if(rowIndex == 0) { // 第一行为标题
                                columnNames.add(cell.getStringCellValue());
                            } else {
                                String value = cell.getStringCellValue();
                                table.put(rowIndex, columnNames.get(columnIndex), value);
                            }
                        }
                    }
                }
                tables.put(sheetName, table);
            }
            return tables;
        } finally {
            if(wb != null) {
                wb.close();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
    }
    
    /**
     * multimap转换为table
     * 
     * @param multimap multimap
     * @return table
    */
    public static Table<Integer, String, String> map2table(Multimap<String, String> multimap) {
        Table<Integer, String, String> table = TreeBasedTable.create();
        Set<String> cloumns = multimap.keySet();
        for(String cloumn : cloumns) {
            Collection<String> values = multimap.get(cloumn);
            int rowIndex = 1;
            for(String value : values) {
                table.put(rowIndex, cloumn, value);
                rowIndex++;
            }
        }
        return table;
    }
    
    /**
     * table转换为multimap
     * 
     * @param table table
     * @return multimap
    */
    public static Multimap<String, String> table2map(Table<Integer, String, String> table) {
        Multimap<String, String> multimap = LinkedHashMultimap.create();

        Set<Integer> rowNames = table.rowKeySet();
        // Set<String> columnNames = table.columnKeySet();

        for (Integer rowName : rowNames) {
            Map<String, String> items = table.row(rowName);
            for (Map.Entry<String, String> entry : items.entrySet()) {
                String columnName = entry.getKey();
                String value = entry.getValue();
                multimap.put(columnName, value);
            }
        }
        return multimap;
    }
}