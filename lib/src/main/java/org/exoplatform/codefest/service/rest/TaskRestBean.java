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
package org.exoplatform.codefest.service.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.exoplatform.codefest.service.bean.TaskBean;
import org.exoplatform.codefest.util.DateUtil;

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
public class TaskRestBean {
  public static final String TASK_PRIORITY_LOW="LOW";
  public static final String TASK_PRIORITY_MEDIUM="MEDIUM";
  public static final String TASK_PRIORITY_HIGH="HIGH";
  
  private String createdDate;
  private String modifiedDate;
  private String startedDate;
  private String resolvedDate;
  private String creatorId;
  
  private String id;
  private String name ;
  private String description;
  private String assigneeId;
  private List<String> coWorkers;
  private String estimateTime;
  private String loggedTime;
  private String remainingTime;
  private String dueDate;
  private String status;
  private String priority;
  private String percentCompleted;
  
  
  public TaskRestBean(TaskBean task){
    this.createdDate = DateUtil.dateToString(task.getCreatedDate(), "MM/dd/yyyy");
    this.modifiedDate = DateUtil.dateToString(task.getModifiedDate(), "MM/dd/yyyy");
    this.startedDate = DateUtil.dateToString(task.getStartedDate(), "MM/dd/yyyy");
    this.resolvedDate = DateUtil.dateToString(task.getResolvedDate(), "MM/dd/yyyy");
    
    this.id=task.getId();
    this.name=task.getName();
    this.description = task.getDescription();
    this.assigneeId = task.getAssigneeId();
    if(null!=task.getCoWorkers() && task.getCoWorkers().size()>0){
      this.coWorkers = new ArrayList<String>();
      this.coWorkers.addAll(task.getCoWorkers());
    }
    
    this.estimateTime = task.getEstimateTime();
    this.loggedTime = task.getLoggedTime();
    this.remainingTime= task.getRemainingTime();
    this.status = task.getStatus();
    this.priority = task.getPriority();
    
    Long estimateTimeInMilisecond = 0L;
    Long loggedTimeInMilisecond = 0L;
    Long remainingTimeTimeInMilisecond = 0L;
    try {
      estimateTimeInMilisecond = DateUtil.parseMeasureTimeToMinisecond(this.estimateTime);
    } catch (Exception e) {
    }
    try {
      loggedTimeInMilisecond = DateUtil.parseMeasureTimeToMinisecond(this.loggedTime);
    } catch (Exception e) {
    }
    try {
      remainingTimeTimeInMilisecond = DateUtil.parseMeasureTimeToMinisecond(this.remainingTime);
    } catch (Exception e) {
    }
    
    if((remainingTimeTimeInMilisecond + loggedTimeInMilisecond) == 0)
    {
      percentCompleted="0";
    }else{
      Long percentCompletedValue = (loggedTimeInMilisecond*100/(loggedTimeInMilisecond+remainingTimeTimeInMilisecond));
      percentCompleted = percentCompletedValue + "";
    }
    
    this.dueDate = DateUtil.dateToString(task.getDueDate(), "MM/dd/yyyy");
  }
  
  public String getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
  public String getModifiedDate() {
    return modifiedDate;
  }
  public void setModifiedDate(String modifiedDate) {
    this.modifiedDate = modifiedDate;
  }
  public String getStartedDate() {
    return startedDate;
  }
  public void setStartedDate(String startedDate) {
    this.startedDate = startedDate;
  }
  public String getResolvedDate() {
    return resolvedDate;
  }
  public void setResolvedDate(String resolvedDate) {
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
  public String getDueDate() {
    return dueDate;
  }
  public void setDueDate(String dueDate) {
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

  public String getPercentCompleted() {
    return percentCompleted;
  }

  public void setPercentCompleted(String percentCompleted) {
    this.percentCompleted = percentCompleted;
  }

  
  
  
}
