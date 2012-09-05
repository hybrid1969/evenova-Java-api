package com.tlabs.eve.api.character;

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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class TrainingQueue implements Iterable<SkillInTraining>, Serializable {

	private static final long serialVersionUID = 7093542650285855582L;

	private final List<SkillInTraining> training;
	
	public TrainingQueue() {
		super();
		this.training = new LinkedList<SkillInTraining>();		
	}
	
	public final int getSkillLevel(final long skillID) {
		final int size = this.training.size();
		for (int i = size - 1; i >= 0; i--) {
			SkillInTraining s = this.training.get(i);
			if (s.getSkillID() == skillID) {
				return s.getSkillLevel();
			}
		}
		return 0;		
	}
	
	public final SkillInTraining firstActive() {		
		final long now = System.currentTimeMillis();
		for (SkillInTraining t: this.training) {
			if (t.getEndTime() > now) {
				return t;
			}
		}
		return null;
	}
		
	public final SkillInTraining first(final long skillID) {
		return first(skillID, false);
	}
		
	public final SkillInTraining first(final long skillID, boolean activeOnly) {
		return (activeOnly) ? getSkillInTraining(skillID, true) : getSkillImpl(skillID, true);
	}
	
	public final SkillInTraining last(final long skillID) {
		return last(skillID, false);
	}
	
	public final SkillInTraining last(final long skillID, boolean activeOnly) {
		return (activeOnly) ? getSkillInTraining(skillID, false) : getSkillImpl(skillID, false);
	}
	
	public final SkillInTraining getSkill(final long skillID, int skillLevel) {
		return getSkill(skillID, skillLevel, false);
	}
	
	public final SkillInTraining getSkill(final long skillID, int skillLevel, boolean activeOnly) {
		return (activeOnly) ? getSkillInTraining(skillID, skillLevel) : getSkillImpl(skillID, skillLevel);
	}
	
	public final SkillInTraining getActive() {
		final long now = System.currentTimeMillis();
		for (SkillInTraining t: this.training) {
			if ((t.getStartTime() <= now) && (t.getEndTime() >= now)) {
				return t;
			}
		}
		return null;
	}
	
	public final long getStartTime() {
		SkillInTraining l = first();
		return (null == l) ? 0 : l.getStartTime();
	}
	
	public final long getEndTime() {
		SkillInTraining l = last();
		return (null == l) ? 0 : l.getEndTime();
	}

	public synchronized final boolean remove(final SkillInTraining t) {
		if (!contains(t)) {
			return false;
		}
		return this.training.remove(t);
	}
	
	public synchronized final boolean add(final SkillInTraining t) {
		if (contains(t)) {
			return false;
		}
		return this.training.add(t);
	}
	
	public synchronized final void addAll(final Collection<SkillInTraining> l) {
		for (SkillInTraining t: l) {
			add(t);
		}
	}
	
	public final void swap(final int from, final int to) {
		final SkillInTraining fromTraining = get(from);
		final long fStartTime = fromTraining.getStartTime();
		final long fEndTime = fromTraining.getEndTime();
		
		final SkillInTraining toTraining = get(to);
		fromTraining.setStartTime(toTraining.getStartTime());
		fromTraining.setEndTime(toTraining.getEndTime());			
		toTraining.setStartTime(fStartTime);
		toTraining.setEndTime(fEndTime);
		
		this.training.set(from, toTraining);
		this.training.set(to, fromTraining);
		
	}
	
	public synchronized final SkillInTraining remove(final int index) {
		return this.training.remove(index);
	}
	
	public synchronized final void add(final int index, final SkillInTraining t) {
		if (!contains(t)) {			
			this.training.add(index, t);
		}
	}
	
	public synchronized final boolean remove(final long skillId, final int level) {
		return removeImpl(new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				SkillInTraining t = (SkillInTraining)object;
				return (t.getSkillID() != skillId) || (t.getSkillLevel() < level);
			}			
		}) != 0;
	}
		
	public synchronized final void clear() {
		this.training.clear();
	}
	
	
	private final int removeImpl(final Predicate p) {
		final int size = this.training.size();
		CollectionUtils.filter(this.training, p);
		return Math.abs(size - this.training.size());
	}
	
	public final SkillInTraining last() {
		return (this.training.size() == 0) ? null : this.training.get(this.training.size() - 1);
	}
	
	public final SkillInTraining first() {
		return (this.training.size() == 0) ? null : this.training.get(0);
	}
	
	public final SkillInTraining get(int position) {
		return this.training.get(position);
	}
	
	public final int size() {
		return this.training.size();
	}
	
	public final List<SkillInTraining> list() {
		return Collections.unmodifiableList(this.training);
	}
	

	public final List<SkillInTraining> list(boolean activeOnly) {
		if (!activeOnly) {
			return this.list();
		}
		
		final long now = System.currentTimeMillis();
		final List<SkillInTraining> r = new ArrayList<SkillInTraining>(this.training.size());		
		CollectionUtils.filter(r, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				SkillInTraining t = (SkillInTraining)object;				
				return t.getEndTime() > now;
			}			
		});
		return r;
	}
	
	public final int getActiveCount() {
		final long now = System.currentTimeMillis();
		int count = 0;
		for (SkillInTraining t: this.training) {
			if (t.getEndTime() > now) {
				count = count + 1;
			}
		}
		return count;
	}
	
	@Override
	public Iterator<SkillInTraining> iterator() {
		return this.training.iterator();
	}
	
	public boolean contains(final SkillInTraining t) {
		for (SkillInTraining s: this.training) {
			if (
				(s.getSkillID() == t.getSkillID()) &&
				(s.getSkillLevel() == t.getSkillLevel())) {
				return true;
			}
		}
		return false;
	}
	
	private final SkillInTraining getSkillImpl(final long skillID, int skillLevel) {
		if (this.training.size() == 0) {
			return null;
		}		
		for (SkillInTraining skill: this.training) {
			if ((skill.getSkillID() == skillID) && (skill.getSkillLevel() == skillLevel)) {
				return skill;
			}
		}
		return null;			
	}
	
	private final SkillInTraining getSkillImpl(final long skillID, final boolean asc) {
		if (asc) {
			for (int i = 1; i < 6; i++) {
				SkillInTraining skill = getSkillImpl(skillID, i);
				if (null != skill) {
					return skill;
				}
			}
		}
		else {
			for (int i = 5; i > 0; i--) {
				SkillInTraining skill = getSkillImpl(skillID, i);
				if (null != skill) {
					return skill;
				}
			}
		}
		return null;
	}
	
	private final SkillInTraining getSkillInTraining(final long skillID, int skillLevel) {
		if (this.training.size() == 0) {
			return null;
		}
		long now = System.currentTimeMillis();
		
		for (SkillInTraining skill: this.training) {
			if (
				((skill.getSkillID() == skillID) && (skill.getSkillLevel() == skillLevel)) &&
				((skill.getEndTime() == 0) || (skill.getEndTime() >= now))) {
					return skill;					
			}
		}
		return null;
	}

	//returns the lowest/highest level of SkillInTraining, or null
	private final SkillInTraining getSkillInTraining(final long skillID, final boolean asc) {
		if (asc) {
			for (int i = 1; i < 6; i++) {
				SkillInTraining skill = getSkillInTraining(skillID, i);
				if (null != skill) {
					return skill;
				}
			}
		}
		else {
			for (int i = 5; i > 0; i--) {
				SkillInTraining skill = getSkillInTraining(skillID, i);
				if (null != skill) {
					return skill;
				}
			}
		}
		return null;
	}
	
}