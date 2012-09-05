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


import java.io.Serializable;

public class MarketOrder extends Object implements Serializable  {
	
	private static final long serialVersionUID = -5368560761567594668L;
	
	//Valid states: 0 = open/active, 1 = closed, 2 = expired (or fulfilled), 3 = cancelled, 4 = pending, 5 = character deleted.
	public static final int STATE_ACTIVE = 0;
	public static final int STATE_CLOSED = 1;
	public static final int STATE_EXPIRED = 2;
	public static final int STATE_CANCELLED = 3;
	public static final int STATE_PENDING = 4;
	public static final int STATE_DELETED = 5;
	
	//-1 = station, 0 = solar system, 1 = 1 jump, 2 = 2 jumps, ..., 32767 = region.
	public static final int RANGE_STATION = -1;
	public static final int RANGE_SYSTEM = 0;
	public static final int RANGE_REGION = 32767;
	
	private long orderID;
	
	private long itemID;	
	private long characterID;
	
	private long stationID;
	
	private long minVolume;
	private long initialVolume;
	private long remainingVolume;
		
	private int state;
	private int range;
	private int duration;//in days
	
	private float marketEscrow;
	private float price;
	
	private boolean buyOrder;
	
	private long issueDate;
	
	private long accountKey;

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}
	
	public long getItemID() {
		return itemID;
	}

	public void setItemID(long itemID) {
		this.itemID = itemID;
	}

	
	public long getCharacterID() {
		return characterID;
	}

	public void setCharacterID(long characterID) {
		this.characterID = characterID;
	}
	
	public long getStationID() {
		return stationID;
	}

	public void setStationID(long stationID) {
		this.stationID = stationID;
	}


	public long getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(long minVolume) {
		this.minVolume = minVolume;
	}


	public long getInitialVolume() {
		return initialVolume;
	}

	public void setInitialVolume(long initialVolume) {
		this.initialVolume = initialVolume;
	}

	public long getRemainingVolume() {
		return remainingVolume;
	}

	public void setRemainingVolume(long remainingVolume) {
		this.remainingVolume = remainingVolume;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public void setState(String state) {
		this.state = Integer.parseInt(state);
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public void setRange(String range) {
		this.range = Integer.parseInt(range);
	}

	//days
	public long getDurationInDays() {
		return duration;
	}

	
	public void setDurationInDays(int duration) {
		this.duration = duration;
	}

	public float getMarketEscrow() {
		return marketEscrow;
	}

	public void setMarketEscrow(float marketEscrow) {
		this.marketEscrow = marketEscrow;
	}

	public void setMarketEscrow(String marketEscrow) {
		this.marketEscrow = Float.parseFloat(marketEscrow);
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setPrice(String price) {
		this.price = Float.parseFloat(price);
	}
	
	public boolean getIsBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(boolean buyOrder) {
		this.buyOrder = buyOrder;
	}
	
	public void setBuyOrder(String buyOrder) {
		this.buyOrder = "1".equalsIgnoreCase(buyOrder);
	}
	
	public long getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(long issueDate) {
		this.issueDate = issueDate;
	}

	public long getEndDate() {
		return this.issueDate + this.duration * 24l * 3600000l;
	}
	
	public long getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(long accountKey) {
		this.accountKey = accountKey;
	}
	
	/*orderID	 int	Unique order ID for this order. Note that these are not guaranteed to be unique forever, they can recycle. But they are unique for the purpose of one data pull.
	charID	 int	ID of the character that physically placed this order.
	stationID	 int	ID of the station the order was placed in.
	volEntered	 int	Quantity of items required/offered to begin with.
	volRemaining	 int	Quantity of items still for sale or still desired.
	minVolume	 int	For bids (buy orders), the minimum quantity that must be sold in one sale in order to be accepted by this order.
	orderState	 byte	Valid states: 0 = open/active, 1 = closed, 2 = expired (or fulfilled), 3 = cancelled, 4 = pending, 5 = character deleted.
	typeID	 short	ID of the type (references the invTypes table) of the items this order is buying/selling.
	range	 short	The range this order is good for. For sell orders, this is always 32767. For buy orders, allowed values are: -1 = station, 0 = solar system, 1 = 1 jump, 2 = 2 jumps, ..., 32767 = region.
	accountKey	 short	Which division this order is using as its account. Always 1000 for characters, but in the range 1000 to 1006 for corporations.
	duration	 short	How many days this order is good for. Expiration is issued + duration in days.
	escrow	 decimal	How much ISK is in escrow. Valid for buy orders only (I believe).
	price	 decimal	The cost per unit for this order.
	bid	 bool	If true, this order is a bid (buy order). Else, sell order.
	issued	 datetime	When this order was issued.*/
	
	
}