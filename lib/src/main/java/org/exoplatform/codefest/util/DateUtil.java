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
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    String s = formatter.format(date);
    return s;
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
}
