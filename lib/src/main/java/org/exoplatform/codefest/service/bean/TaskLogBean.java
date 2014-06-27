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
package org.exoplatform.codefest.service.bean;

import java.util.Date;

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
public class TaskLogBean {

  public static final String LOG_TYPE_LOG_WORK = "LOG_WORK";
  public static final String LOG_TYPE_COMMENT = "COMMENT";
  public static final String LOG_TYPE_EIDT = "EDIT";
  
  private Date createdDate;
  private Date modifiedDate;
  private String logType;
  private String creatorId;
  private String logContent;
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public Date getModifiedDate() {
    return modifiedDate;
  }
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }
  public String getLogType() {
    return logType;
  }
  public void setLogType(String logType) {
    this.logType = logType;
  }
  public String getCreatorId() {
    return creatorId;
  }
  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }
  public String getLogContent() {
    return logContent;
  }
  public void setLogContent(String logContent) {
    this.logContent = logContent;
  }
  
  
  
}
