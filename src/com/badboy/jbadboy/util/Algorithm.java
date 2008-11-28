/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id$
 *
 * This file is part of JBadboy.
 * 
 * JBadboy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JBadboy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JBadboy.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.badboy.jbadboy.util;
import java.util.*;

/**
 * Some simple functional programming constructs
 */
public class Algorithm {
    
    public interface Filter<T> {
        boolean $(T t);
    }   

    public static <T extends Number> Number sum(Iterable<T> items) {
        double total = 0.0;
        for(T n : items) {
            total += n.doubleValue();
        }
        return total;
    }
    
    public static <T> List<T> filter(Iterable<T> items, Filter<T> p) {
        final List<T> filtered = new ArrayList<T>();
        for(T t : items) {
            if(p.$(t))
                filtered.add(t);
        }
        return filtered;
    }
    
    public static <T,U> List<U> map(Iterable<T> list, Function<U,T> c) {
        ArrayList<U> u = new ArrayList<U>();
        for(T t : list) 
            u.add(c.$(t));
        return u;
    }
    
    public static <T,U> Map<T,U> index(Iterable<U> list, Function<T,U> c) {
        Map<T, U> result = new HashMap<T,U>();
        for(U u: list) {
            result.put(c.$(u), u);
        }
        return result;
    }
}