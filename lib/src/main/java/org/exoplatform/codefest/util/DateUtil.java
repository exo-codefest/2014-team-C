/*
 * Copyright (C) 2003-2014 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU Affero General Public License
* as published by the Free Software Foundation; either version 3
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.codefest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
public class DateUtil {
  
  public static String dateToString(Date date, String format){
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      String s = formatter.format(date);
      return s;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public static Date stringToDate(String date,String formatStr){
    try {      
      SimpleDateFormat format = new SimpleDateFormat(formatStr);      
      Date ret = format.parse(date);        
      return ret;
    } catch (Exception e) {
      return null;
    } 
  }
  
  public static long parseMeasureTimeToMinisecond(String input) {
    long result = 0;
    String number = "";
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (Character.isDigit(c)) { 
        number += c; 
      } else if (Character.isLetter(c) && !number.isEmpty()) {
        result += convert(Integer.parseInt(number), c);
        number = "";
      }
    }
    return result;
 }

 private static long convert(int value, char unit) {
   switch(unit) {
     case 'M' : return value * 1000*60*60*8*5*4;
     case 'w' : return value * 1000*60*60*8*5;
     case 'd' : return value * 1000*60*60*8;
     case 'h' : return value * 1000*60*60;         
     case 'm' : return value * 1000*60;
     case 's' : return value * 1000;
   }
   return 0;
 }
}
