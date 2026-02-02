/*
 * ChatImprove-template, a Minecraft mod-addon for <https://github.com/Ayoree/ChatImprover>
 * Copyright (C) Ayoree <https://github.com/Ayoree>
 * Copyright (C) ChatImprove-template contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayoree.chatimprove.template;

import net.fabricmc.api.ModInitializer;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatImprove_template implements ModInitializer {
	public static final String MOD_ID = "chatimprove_template";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Set<String> IPs = Set.of("mc.template.org", "ru.template.org", "184.213.55.70"); // Server(s) IPs

	@Override
	public void onInitialize() {
		LOGGER.info("%s initialized".formatted(MOD_ID));
	}
}