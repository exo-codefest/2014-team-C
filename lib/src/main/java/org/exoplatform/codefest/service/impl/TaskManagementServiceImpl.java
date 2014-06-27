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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

import org.exoplatform.codefest.service.TaskManagementService;
import org.exoplatform.codefest.service.bean.ProjectBean;
import org.exoplatform.codefest.service.bean.TaskBean;
import org.exoplatform.codefest.service.bean.TaskLogBean;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

/**
 * Created by The eXo Platform SAS
 * 27 Jun 2014  
 */
public class TaskManagementServiceImpl implements TaskManagementService {
  
  private RepositoryService repositoryService;
  
  private static final Log  LOG = ExoLogger.getLogger(TaskManagementServiceImpl.class);

  public TaskManagementServiceImpl(RepositoryService repositoryService){
    this.repositoryService = repositoryService;
    try {
      createHomeNode();
    } catch (RepositoryException e) {
      LOG.error(e);
    }
  }
  
  protected void createHomeNode() throws RepositoryException {
    SessionProvider sessionProvider = null;

    try {
      sessionProvider = SessionProvider.createSystemProvider();
      ManageableRepository currentRepo = this.repositoryService.getCurrentRepository();
      Session session = sessionProvider.getSession(currentRepo.getConfiguration().getDefaultWorkspaceName(), currentRepo);
      Node rootNode = session.getRootNode();
      if (!rootNode.hasNode(HOME)) {
        Node homeNode = rootNode.addNode(HOME, "exo:taskManagement");
        rootNode.save();
        //Hide home node
       /* if (homeNode.canAddMixin("exo:hiddenable")) {
          homeNode.addMixin("exo:hiddenable");
        }*/
        LOG.info("taskManagementHome is initialized.");
      }
    } finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
  }
  
  private Node getTaskManagementHomeNode(SessionProvider sessionProvider) throws Exception {
    ManageableRepository currentRepo = this.repositoryService.getCurrentRepository();
    Session session = sessionProvider.getSession(currentRepo.getConfiguration().getDefaultWorkspaceName(), currentRepo);
    if(session.getRootNode().hasNode(HOME) ==false){
      createHomeNode();
    }
    return session.getRootNode().getNode(HOME);
  }
  
  @Override
  public ProjectBean createProject(ProjectBean project) throws Exception {
    SessionProvider sessionProvider = null;
    try {
      sessionProvider = SessionProvider.createSystemProvider();
      //Create home node if it is not existed
      ManageableRepository currentRepo = this.repositoryService.getCurrentRepository();
      Session session = sessionProvider.getSession(currentRepo.getConfiguration().getDefaultWorkspaceName(), currentRepo);
      Node rootNode = session.getRootNode();
      Node homeNode = getTaskManagementHomeNode(sessionProvider);
      
      Node projectNode = homeNode.addNode(project.getId(), PROJECT_NOTE_TYPE);
      
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date() );
      projectNode.setProperty("exo:createdDate", cal);
      
      projectNode.setProperty("exo:ownerType", project.getOwnerType());
      projectNode.setProperty("exo:ownerID", project.getOwnerID());
      projectNode.setProperty("exo:id", project.getId());
      projectNode.setProperty("exo:name", project.getName());
      projectNode.setProperty("exo:description", project.getDescription());
      
      if(project.getMembersId()!=null){
        int lengh = project.getMembersId().size();
        Value[] value = new Value[lengh];
        for (int i=0; i<lengh; i++) {
          Value value2add = session.getValueFactory().createValue(project.getMembersId().get(i));
          value[i]= value2add;
        }
        projectNode.setProperty("exo:membersId", value);
      }else{
        projectNode.setProperty("exo:membersId", (Value[])null);
      }
      
      if(project.getManagerId()!=null){
        int lengh = project.getManagerId().size();
        Value[] value = new Value[lengh];
        for (int i=0; i<lengh; i++) {
          Value value2add = session.getValueFactory().createValue(project.getManagerId().get(i));
          value[i]= value2add;
        }
        projectNode.setProperty("exo:managerId", value);
      }else{
        projectNode.setProperty("exo:managerId", (Value[])null);
      }
      
      if(project.getListTaskStatus()!=null){
        int lengh = project.getManagerId().size();
        Value[] value = new Value[lengh];
        for (int i=0; i<lengh; i++) {
          Value value2add = session.getValueFactory().createValue(project.getListTaskStatus().get(i));
          value[i]= value2add;
        }
        projectNode.setProperty("exo:listTaskStatus", value);
      }else{
        Value[] value = new Value[3];
        value[0] = session.getValueFactory().createValue(ProjectBean.LIST_TASK_STATUS_TODO);
        value[1] = session.getValueFactory().createValue(ProjectBean.LIST_TASK_STATUS_IN_PROGRESS);
        value[2] = session.getValueFactory().createValue(ProjectBean.LIST_TASK_STATUS_DONE);
        projectNode.setProperty("exo:listTaskStatus", (Value[])null);
      }
      
      projectNode.setProperty("exo:isDeleted", true);
      
      homeNode.save();
      
     }catch (Exception e){
      LOG.error(e);
      return null;
    }finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
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
