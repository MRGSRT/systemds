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

package org.tugraz.sysds.lops;

 
import org.tugraz.sysds.lops.LopProperties.ExecType;

import org.tugraz.sysds.common.Types.DataType;
import org.tugraz.sysds.common.Types.ValueType;

public class WeightedCrossEntropy extends Lop 
{
	public static final String OPCODE = "mapwcemm";
	public static final String OPCODE_CP = "wcemm";
	private int _numThreads = 1;

	public enum WCeMMType {
		BASIC,			//(sum(X*log(U%*%t(V))))
		BASIC_EPS;		//(sum(X*log(U%*%t(V) + eps)))
		
		public boolean hasFourInputs() {
			return (this == BASIC_EPS);
		}
	}
	
	private WCeMMType _wcemmType = null;
	
	public WeightedCrossEntropy(Lop input1, Lop input2, Lop input3, Lop input4, DataType dt, ValueType vt, WCeMMType wt, ExecType et) {
		super(Lop.Type.WeightedCeMM, dt, vt);
		addInput(input1); //X
		addInput(input2); //U
		addInput(input3); //V
		addInput(input4); // optional
		input1.addOutput(this); 
		input2.addOutput(this);
		input3.addOutput(this);
		input4.addOutput(this);
		
		//setup mapmult parameters
		_wcemmType = wt;
		setupLopProperties(et);
	}

	@Override
	public String toString() {
		return "Operation = WeightedCrossEntropy";
	}
	
	@Override
	public String getInstructions(int input1, int input2, int input3, int input4, int output)
	{
		return getInstructions(
				String.valueOf(input1),
				String.valueOf(input2),
				String.valueOf(input3),
				String.valueOf(input4),
				String.valueOf(output));
	}

	@Override
	public String getInstructions(String input1, String input2, String input3, String input4, String output)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(getExecType());
		
		sb.append(Lop.OPERAND_DELIMITOR);
		if( getExecType() == ExecType.CP )
			sb.append(OPCODE_CP);
		else
			sb.append(OPCODE);
		
		sb.append(Lop.OPERAND_DELIMITOR);
		sb.append( getInputs().get(0).prepInputOperand(input1));
		
		sb.append(Lop.OPERAND_DELIMITOR);
		sb.append( getInputs().get(1).prepInputOperand(input2));
		
		sb.append(Lop.OPERAND_DELIMITOR);
		sb.append( getInputs().get(2).prepInputOperand(input3));
		
		sb.append(Lop.OPERAND_DELIMITOR);
		sb.append( getInputs().get(3).prepInputOperand(input4));
		
		sb.append(Lop.OPERAND_DELIMITOR);
		sb.append( prepOutputOperand(output));
		
		sb.append(Lop.OPERAND_DELIMITOR);
		sb.append(_wcemmType);
		
		//append degree of parallelism
		if( getExecType()==ExecType.CP ) {
			sb.append( OPERAND_DELIMITOR );
			sb.append( _numThreads );
		}
		
		return sb.toString();
	}
	
	public void setNumThreads(int k) {
		_numThreads = k;
	}
}
