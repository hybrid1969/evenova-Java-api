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


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
public class CertificateTreeTest extends EveApiTest {

	@Test(timeout=10000)
	public void testCertificateTreeParser() {
		CertificateTreeResponse r = apiCall(new CertificateTreeRequest());
		assertNotNull("Null response", r);
		
		CertificateTree tree = r.getCertificateTree();
		assertNotNull("Null CertificateTree", tree);
				
		assertTrue("No CertificateTree.Category.", tree.getCategories().size() > 0);
		
		Map<Long, Certificate> all = tree.getCertificates();
		assertTrue("No CertificateTree.Certificate", all.size() > 0);
		
		/*for (long id: all.keySet()) {
			System.out.println(
					ToStringBuilder.reflectionToString(all.get(id), ToStringStyle.SIMPLE_STYLE));
		}*/
	}
}