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
package org.frameworkset.spi.interceptor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.frameworkset.spi.assemble.SynchronizedMethod;
import org.frameworkset.spi.assemble.Transactions;

import com.frameworkset.orm.transaction.TransactionManager;
import com.frameworkset.proxy.Interceptor;
/**
 * 
 * 
 * <p>Title: TransactionInterceptor.java</p>
 *
 * <p>Description: 事务管理拦截器，对声明式的事务进行拦截处理</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>bboss workgroup</p>
 * @Date Aug 11, 2008 4:57:34 PM
 * @author biaoping.yin,尹标平
 * @version 1.0
 */
public final class TransactionInterceptor implements Interceptor {
	private static Logger log = Logger.getLogger(TransactionInterceptor.class);

	private TransactionManager tm;
	private Transactions txs;
	
	
	public TransactionInterceptor(Transactions txs) {		
		this.txs = txs;
	}
	
	

	public void after(Method method, Object[] args) throws Throwable {
	
		if(tm == null)
			return ;
		SynchronizedMethod  synmethod = txs.isTransactionMethod(method);
		if(synmethod != null)
		{
			try
			{
				tm.commit();
			}
			catch(Exception e)
			{
				log.error(e);
				throw e;
			}
		}
		
		
		

	}

	public void afterFinally(Method method, Object[] args) throws Throwable {

		this.tm = null;


	}
	
	public void afterThrowing(Method method, Object[] args) throws Throwable {
		afterThrowing(method, args, null);
	}

	public void afterThrowing(Method method, Object[] args, Throwable throwable) throws Throwable {
		if(tm == null)
			return ;
		try
		{
			SynchronizedMethod  synmethod = txs.isTransactionMethod(method);
			if(synmethod != null )
			{
				if(synmethod.isRollbackException(throwable))
				{
					try
					{
						tm.rollback();
					}
					catch(Exception e)
					{
						throw e;
					}
				}
				else
				{
					try
					{
						tm.commit();
					}
					catch(Exception e)
					{
						throw e;
					}
				}
			}
			else if(tm != null)
			{
				log.debug("Error afterThrowing state when call method " + method.getName() + " tm is not null.but this method is not a tx method." );
				tm.rollback();
			}
		}
		catch(Throwable e)
		{
			log.error(e);
			throw e;
		}

	}

	public void before(Method method, Object[] args) throws Throwable {
		SynchronizedMethod  synmethod = txs.isTransactionMethod(method);
		if(synmethod != null)
		{
			try
			{
				tm = new TransactionManager();
				tm.begin(synmethod.getTxtype());
			}
			catch(Throwable t)
			{
				log.error(t);				
				throw t;
			}
		}
		


	}

}
