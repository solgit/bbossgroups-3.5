/*
 *  Copyright 2008 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.frameworkset.orm.transaction;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.frameworkset.common.poolman.util.SQLUtil;

/**
 * 
 * <p>Title: TXDatasource.java</p>
 *
 * <p>Description: ��ȡ����������Դ</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-4-14 ����08:52:55
 * @author biaoping.yin
 * @version 1.0
 */
public class TXDataSource implements DataSource{
	
	private DataSource datasource;
	public TXDataSource(DataSource datasource) {
		this.datasource = datasource;
    }

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return SQLUtil.getConectionFromDatasource(datasource);
	}

	/**
	 * ���������߱�������
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return datasource.getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return datasource.getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return datasource.getLoginTimeout();
	}

}