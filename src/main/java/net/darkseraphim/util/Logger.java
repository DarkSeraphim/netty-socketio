/**
 * Copyright 2012 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.darkseraphim.util;

import java.util.Objects;
import java.util.logging.Level;

/**
 * @author DarkSeraphim.
 */
public class Logger extends java.util.logging.Logger
{

    private boolean debug = false;

    private boolean trace = false;

    protected Logger(String name)
    {
        super(name, null);
    }

    public static Logger getLogger(Class<?> clazz)
    {
        return new Logger(clazz.getSimpleName());
    }

    public boolean isDebugEnabled()
    {
        return this.debug;
    }

    public boolean isTraceEnabled()
    {
        return this.trace;
    }

    private String format(String message, Object...params)
    {
        String[] sparams = new String[params.length];
        for(int i = 0; i < params.length; i++)
            sparams[i] = Objects.toString(params[i], "null");
        return String.format(message.replace("{}", "%s"), sparams);
    }

    public void info(String message, Object...params)
    {
        this.log(Level.INFO, format(message, params));
    }

    public void debug(String message, Object...params)
    {
        if(!this.debug)
            return;
        this.log(Level.INFO, format(message, params));
    }

    public void trace(String message, Object...params)
    {
        if(!this.trace)
            return;
        this.log(Level.INFO, format(message, params));
    }

    public void warn(String message, Object...params)
    {
        this.log(Level.WARNING, format(message, params));
    }

    public void error(String message, Object...params)
    {
        this.log(Level.SEVERE, format(message, params));
    }
}
