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
package org.exoplatform.codefest.service.impl;

import java.util.List;

import org.exoplatform.codefest.service.TaskManagementService;
import org.exoplatform.codefest.service.bean.ProjectBean;
import org.exoplatform.codefest.service.bean.TaskBean;
import org.exoplatform.codefest.service.bean.TaskLogBean;

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
public class TaskManagementServiceImpl implements TaskManagementService {

  @Override
  public ProjectBean createProject(ProjectBean project) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProjectBean updateProject(ProjectBean project) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean deleteProject(String projectId) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public TaskBean createTask(TaskBean taskBean) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TaskBean updateTask(TaskBean taskBean) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean deleteTask(String projectId, String taskId) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public TaskLogBean createTaskLogBean(String projectId, String taskId, TaskLogBean takTaskLogBean) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TaskLogBean updateTaskLogBean(String projectId, String taskId, TaskLogBean takTaskLogBean) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TaskLogBean deleteTaskLogBean(String projectId, String taskId, TaskLogBean takTaskLogBean) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProjectBean getProjectByID(String projectID) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProjectBean getProjectByName(String projectName) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ProjectBean> getAllProject() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ProjectBean> getProjectByOwner(String ownerType, String ownerId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ProjectBean> getProjectByPermission(String userId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TaskBean> getTaskOfProject(String projectId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TaskBean getTask(String projectId, String taskId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TaskLogBean> getLogOfTask(String projectId, String taskId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
