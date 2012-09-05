package com.tlabs.eve.api;

/*
 * #%L
 * This source code is part of the Evanova Android application:
 * https://play.google.com/store/apps/details?id=com.tlabs.android.evanova
 * %%
 * Copyright (C) 2010 - 2012 Evanova (Traquenard Labs)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**@since Eve API V3 (30 Aug 2011*/
public class CallListResponse extends EveResponse {
	private static final Log LOG = LogFactory.getLog("EveAPI");
	
	private Map<Integer, CallGroup> callGroups = new HashMap<Integer, CallGroup>();
	
	public CallListResponse() {
		super();
	}
	
	public void addGroup(CallGroup g) {
		this.callGroups.put(g.getGroupID(), g);
	}
	
	public void addEntry(CallEntry e) {
		CallGroup g = this.callGroups.get(e.getGroupID());
		if (null == g) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("CallGroup with id '" + e.getGroupID() + "' not found.");					
			}
		}
		else {
			g.addEntry(e);
		}
	}
}