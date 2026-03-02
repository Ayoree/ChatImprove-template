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

package org.ayoree.chatimprove.template.messages;

import java.util.function.Function;
import java.util.function.Predicate;

import org.ayoree.chatimprove.template.AddonInformerImpl;
import org.ayoree.chatimprover.api.ChatMessage;
import org.ayoree.chatimprover.api.ChatMessageWithSender;

import com.google.auto.service.AutoService;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

// extends `ChatMessageWithSender`
public class ExampleMessageWithSender extends ChatMessageWithSender {
    public ExampleMessageWithSender(Text message) {
        super(message);
        // Setting senderNick
        setSenderNick(getMessageStr().substring(0, getMessageStr().indexOf(' '))); // 
    }

    // This annotation is necessary
    @AutoService(Provider.class)
    public static class ProviderImpl extends AddonInformerImpl implements Provider {
        @Override
        public Predicate<Text> validator() {
            return message -> {
                return (
                    message.getString().startsWith("CoolNick -> ...")
                );
            };
        }
        @Override
        public Function<Text, ChatMessage> creator() {
            return ExampleMessage::new;
        }
    }

    // Changing message as you want
    @Override
    public Text getChangedMessage() {
        MutableText newMsg = m_message.copy().setStyle(Style.EMPTY);

        // Should call addExtraStuff(newMsg) at return
        return addExtraStuff(newMsg);
    }
}
