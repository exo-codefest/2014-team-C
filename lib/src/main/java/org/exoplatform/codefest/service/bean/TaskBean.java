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
import java.util.List;

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
public class TaskBean {
  public static final String TASK_PRIORITY_LOW="LOW";
  public static final String TASK_PRIORITY_MEDIUM="MEDIUM";
  public static final String TASK_PRIORITY_HIGH="HIGH";
  
  private Date createdDate;
  private Date modifiedDate;
  private Date resolvedDate;
  private String creatorId;
  
  private String id;
  private String name ;
  private String description;
  private String assigneeId;
  private List<String> coWorkers;
  private String estimateTime;
  private String loggedTime;
  private String remainingTime;
  private Date dueDate;
  private String status;
  private String priority;
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
  public Date getResolvedDate() {
    return resolvedDate;
  }
  public void setResolvedDate(Date resolvedDate) {
    this.resolvedDate = resolvedDate;
  }
  public String getCreatorId() {
    return creatorId;
  }
  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
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
  public String getAssigneeId() {
    return assigneeId;
  }
  public void setAssigneeId(String assigneeId) {
    this.assigneeId = assigneeId;
  }
  public List<String> getCoWorkers() {
    return coWorkers;
  }
  public void setCoWorkers(List<String> coWorkers) {
    this.coWorkers = coWorkers;
  }
  public String getEstimateTime() {
    return estimateTime;
  }
  public void setEstimateTime(String estimateTime) {
    this.estimateTime = estimateTime;
  }
  public String getLoggedTime() {
    return loggedTime;
  }
  public void setLoggedTime(String loggedTime) {
    this.loggedTime = loggedTime;
  }
  public String getRemainingTime() {
    return remainingTime;
  }
  public void setRemainingTime(String remainingTime) {
    this.remainingTime = remainingTime;
  }
  public Date getDueDate() {
    return dueDate;
  }
  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getPriority() {
    return priority;
  }
  public void setPriority(String priority) {
    this.priority = priority;
  }
  public boolean isDeleted() {
    return isDeleted;
  }
  public void setDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
  
  
  
}
