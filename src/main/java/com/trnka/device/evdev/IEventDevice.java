package com.trnka.device.evdev;

import java.util.List;
import java.util.Map;

/*
 * Copyright (C) 2009 Giacomo Ferrari
 * This file is part of evdev-java.
 *  evdev-java is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  evdev-java is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with evdev-java.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Represents a connection to a Linux Evdev device.
 * For additional info, see input/input.txt and input/input-programming.txt in the Linux kernel Documentation.
 * IMPORTANT: If you want higher-level access for your joystick/pad/whatever, check com.dgis.input.evdev.devices
 * for useful drivers to make your life easier!
 * Copyright (C) 2009 Giacomo Ferrari
 * @author Giacomo Ferrari
 */

interface IEventDevice {
	
	public static final int MAJOR_VER = 0;
	public static final int MINOR_VER = 5;
	
	/**
	 * @return The version of Evdev reported by the kernel.
	 */
	int getEvdevVersion();
	
	/**
	 * @return the bus ID of the attached device.
	 */
	short getBusID();
	/**
	 * @return the vendor ID of the attached device.
	 */
	 short getVendorID();
	/**
	 * @return the product ID of the attached device.
	 */
	 short getProductID();
	/**
	 * @return the version ID of the attached device.
	 */
	 short getVersionID();
	/**
	 * @return the name of the attached device.
	 */
	 String getDeviceName();
	/**
	 * @return A mapping from device supported event types to list of supported event codes.
	 */
	 Map<Integer, List<Integer>> getSupportedEvents();
	
	/**
	 * Obtains the configurable parameters of an absolute axis (value, min, max, fuzz, flatspot) from the device. 
	 * @param axis The axis number (an event code under event type 3 (abs)).
	 * @return The parameters, or null if there was an error. Modifications to this object will be reflected in the device.
	 */
	 InputAxisParameters getAxisParameters(int axis);
	
	/**
	 * Adds an event listener to this device.
	 * When an event is received from Evdev, all InputListeners registered
	 * will be notified by a call to event().
	 * If the listener is already on the listener list,
	 * this method has no effect.
	 * @param list The listener to add. Must not be null.
	 */
	 void addListener(InputListener list);
	/**
	 * Removes an event listener to this device.
	 * If the listener is not on the listener list,
	 * this method has no effect.
	 * @param list The listener to remove. Must not be null.
	 */
	 void removeListener(InputListener list);
	
	/**
	 * Releases all resources held by this EventDevice. No more events will be generated.
	 * It is impossible to restart an EventDevice once this method is called. 
	 */
	 void close();
}

