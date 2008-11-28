/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id$
 *
 * This file is part of JBadboy.
 * 
 * JBadboy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JBadboy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JBadboy.  If not, see <http://www.gnu.org/licenses/>.
 */



/**
 * This is a GENERATED FILE - DO NOT EDIT
 */
package com.badboy.jbadboy.model;

import com.badboy.jbadboy.item.*;
		
public class DataSourceItem extends Playable {

    
    
	
    private Integer loadType = 0;

    @Exportable
    public Integer getLoadType() {
        return this.loadType;
    }
    public void setLoadType(Integer loadType) {
        this.loadType = loadType;
    }

    private String tableName = "";

    @Exportable
    public String getTableName() {
        return this.tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private String loadString = "";

    @Exportable
    public String getLoadString() {
        return this.loadString;
    }
    public void setLoadString(String loadString) {
        this.loadString = loadString;
    }

    private String dataSourceName = "";

    @Exportable
    public String getDataSourceName() {
        return this.dataSourceName;
    }
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    private String connectString = "";

    @Exportable
    public String getConnectString() {
        return this.connectString;
    }
    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    private Integer mapType = 0;

    @Exportable
    public Integer getMapType() {
        return this.mapType;
    }
    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }
}
