//
// MessagePack for Java
//
// Copyright (C) 2009-2011 FURUHASHI Sadayuki
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package org.msgpack.template;

import java.io.IOException;
import java.util.HashMap;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;


public class OrdinalEnumTemplate<T> extends AbstractTemplate<T> {
    protected T[] entries;
    protected HashMap<T, Integer> reverse;

    public OrdinalEnumTemplate(Class<T> targetClass) {
	entries = targetClass.getEnumConstants();
        reverse = new HashMap<T, Integer>();
        for(int i = 0; i < entries.length; i++) {
                reverse.put(entries[i], i);
        }
    }

    @Override
    public void write(Packer pk, T target, boolean required) throws IOException {
	Integer ordinal = reverse.get(target);
        if(ordinal == null) {
            throw new MessageTypeException(new IllegalArgumentException("ordinal: " + ordinal));
        }
        pk.writeInt((int) ordinal);
    }

    @Override
    public T read(Unpacker pac, T to, boolean required) throws IOException, MessageTypeException {
        int ordinal = pac.readInt();
        if(entries.length <= ordinal) {
                throw new MessageTypeException(new IllegalArgumentException("ordinal: " + ordinal));
        }
        return entries[ordinal];
    }
}
