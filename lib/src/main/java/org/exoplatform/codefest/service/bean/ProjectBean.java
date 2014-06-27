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

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
package org.exoplatform.codefest.service.bean;

import java.util.Date;
import java.util.List;

public class ProjectBean {
  
  public static final String LIST_TASK_STATUS_TODO = "TO DO";
  public static final String LIST_TASK_STATUS_IN_PROGRESS = "IN PROGRESS";
  public static final String LIST_TASK_STATUS_DONE = "DONE";
  
  public static final String OWNER_TYPE_SPACE = "SPACE";
  public static final String OWNER_TYPE_GROUP = "GROUP";
  public static final String OWNER_TYPE_USER = "USER";
  
  private Date createdDate;
  private Date modifiedDate;
  private String ownerType;
  private String ownerID;
  private String id;
  private String name;
  private String description;
  
  private List<String> membersId;
  private List<String> managerId;
  private List<String> listTaskStatus;
  private boolean isDeleted;
  
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
  public String getOwnerType() {
    return ownerType;
  }
  public void setOwnerType(String ownerType) {
    this.ownerType = ownerType;
  }
  public String getOwnerID() {
    return ownerID;
  }
  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public List<String> getMembersId() {
    return membersId;
  }
  public void setMembersId(List<String> membersId) {
    this.membersId = membersId;
  }
  public List<String> getManagerId() {
    return managerId;
  }
  public void setManagerId(List<String> managerId) {
    this.managerId = managerId;
  }
  public List<String> getListTaskStatus() {
    return listTaskStatus;
  }
  public void setListTaskStatus(List<String> listTaskStatus) {
    this.listTaskStatus = listTaskStatus;
  }
  public boolean isDeleted() {
    return isDeleted;
  }
  public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
  
}
