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
package org.exoplatform.codefest.service;

import java.util.List;

import org.exoplatform.codefest.service.bean.ProjectBean;
import org.exoplatform.codefest.service.bean.TaskBean;
import org.exoplatform.codefest.service.bean.TaskLogBean;

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
public interface TaskManagementService {
  
  public static final String HOME = "exo:taskManagementHome";
  public static final String TASK_LOG_HOME = "exo:logTaskHome";
  
  public static final String TASK_MANAGEMENT_HOME_NOTE_TYPE = "exo:taskManagement";
  public static final String PROJECT_NOTE_TYPE = "exo:project";
  public static final String TASK_NOTE_TYPE = "exo:task";
  public static final String TASK_LOG_HOME_NOTE_TYPE = "exo:logTaskHome";
  public static final String TASK_LOG_NOTE_TYPE = "exo:logTask";
  
  public abstract ProjectBean createProject(ProjectBean project) throws Exception;
  public abstract ProjectBean updateProject(ProjectBean project) throws Exception;
  public abstract boolean deleteProject(String projectId) throws Exception;
  
  public abstract TaskBean createTask(String projectId, TaskBean taskBean) throws Exception;
  public abstract TaskBean updateTask(String projectId, TaskBean taskBean) throws Exception;
  public abstract boolean changeTaskStatus(String projectId, String taskId, String newStatus) throws Exception;
  public abstract boolean deleteTask(String projectId, String taskId) throws Exception;
  
  public abstract TaskLogBean createTaskLogBean(String projectId, String taskId, TaskLogBean takTaskLogBean) throws Exception;
  public abstract TaskLogBean updateTaskLogBean(String projectId, String taskId, TaskLogBean takTaskLogBean) throws Exception;
  public abstract TaskLogBean deleteTaskLogBean(String projectId, String taskId, TaskLogBean takTaskLogBean) throws Exception;
  
  public abstract ProjectBean getProjectByID(String projectID) throws Exception;
  public abstract ProjectBean getProjectByName(String projectName) throws Exception;
  public abstract List<ProjectBean> getAllProject() throws Exception;
  public abstract List<ProjectBean> getProjectByOwner(String ownerType, String ownerId) throws Exception;
  public abstract List<ProjectBean> getProjectByPermission(String userId) throws Exception;
  
  public abstract List<TaskBean> getTaskOfProject(String projectId) throws Exception;
  public abstract TaskBean getTask(String projectId, String taskId) throws Exception;
  
  public abstract List<TaskLogBean> getLogOfTask(String projectId, String taskId) throws Exception;
  
}
