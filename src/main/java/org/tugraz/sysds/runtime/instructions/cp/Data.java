/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.tugraz.sysds.runtime.instructions.cp;

import java.io.Serializable;

import org.tugraz.sysds.common.Types.DataType;
import org.tugraz.sysds.common.Types.ValueType;
import org.tugraz.sysds.runtime.DMLRuntimeException;
import org.tugraz.sysds.runtime.matrix.MatrixCharacteristics;
import org.tugraz.sysds.runtime.matrix.MetaData;


public abstract class Data implements Serializable 
{
	private static final long serialVersionUID = 9176228330268046168L;

	protected final DataType dataType;
	protected final ValueType valueType;
	
	protected Data(DataType dt, ValueType vt) {
		dataType = dt;
		valueType = vt;
	}
	

	public abstract String getDebugName();
	
	public DataType getDataType() {
		return dataType;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setMetaData(MetaData md) {
		throw new DMLRuntimeException("This method in the base class should never be invoked.");
	}
	
	public MetaData getMetaData() {
		throw new DMLRuntimeException("This method in the base class should never be invoked.");
	}

	public void removeMetaData() {
		throw new DMLRuntimeException("This method in the base class should never be invoked.");
	}

	public void updateMatrixCharacteristics(MatrixCharacteristics mc) {
		throw new DMLRuntimeException("This method in the base class should never be invoked.");
	}
}
