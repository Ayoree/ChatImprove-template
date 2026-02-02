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

import java.util.function.Predicate;

import org.ayoree.chatimprover.api.Filter;

import com.google.auto.service.AutoService;

import net.minecraft.text.Text;

public class FilterImpl extends Filter {
    
    // Block all empty messages
    @AutoService(Provider.class)
    public static class EmptyFilterProviderImpl extends AddonInformerImpl implements Provider {
        @Override
        public Predicate<Text> validator() {
            return text -> text.getString().isBlank();
        }
    }

    // Block all messages, that contains 'КУПИ ДАНАТ!'
    @AutoService(Provider.class)
    public static class AdsFilterProviderImpl extends AddonInformerImpl implements Provider {
        @Override
        public Predicate<Text> validator() {
            return text -> {
                return (
                    text.getString().contains("КУПИ ДАНАТ!")
                );
            };
        }
    }
}
