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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;

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
      ManageableRepository currentRepo = this.repositoryService.getCurrentRepository();
      Session session = sessionProvider.getSession(currentRepo.getConfiguration().getDefaultWorkspaceName(), currentRepo);
      
      Node homeNode = getTaskManagementHomeNode(sessionProvider);
      
      Node projectNode = homeNode.addNode(project.getId(), PROJECT_NOTE_TYPE);
      
      project.setCreatedDate(new Date());
      Calendar cal = Calendar.getInstance();
      cal.setTime(project.getCreatedDate() );
      projectNode.setProperty("exo:createdDate", cal);
      
      projectNode.setProperty("exo:ownerType", project.getOwnerType());
      projectNode.setProperty("exo:ownerID", project.getOwnerID());
      projectNode.setProperty("exo:id", project.getId());
      projectNode.setProperty("exo:name", project.getName());
      projectNode.setProperty("exo:description", project.getDescription());
      projectNode.setProperty("exo:totalTask", new Long(0L));
      
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
      
      projectNode.setProperty("exo:isDeleted", false);
      
      homeNode.save();
      
     }catch (Exception e){
      LOG.error(e);
      return null;
    }finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
    return project;
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
  public TaskBean createTask(String projectId, TaskBean taskBean) throws Exception {
    SessionProvider sessionProvider = null;
    try {
      sessionProvider = SessionProvider.createSystemProvider();
      ManageableRepository currentRepo = this.repositoryService.getCurrentRepository();
      Session session = sessionProvider.getSession(currentRepo.getConfiguration().getDefaultWorkspaceName(), currentRepo);
      
      Node homeNode = getTaskManagementHomeNode(sessionProvider);
      if(homeNode.hasNode(projectId) ==false){
        return null;
      }
      
      taskBean.setCreatedDate(new Date());
      Node projectNode = homeNode.getNode(projectId);
      
      //increase total task of project
      Long totalTask = 0L;
      if(projectNode.hasProperty("exo:totalTask")){
        totalTask = (projectNode.getProperty("exo:totalTask").getLong());
        totalTask += 1;
        projectNode.setProperty("exo:totalTask", totalTask);
      }
      
      //create task node
      String taskId = projectId + "-" + totalTask;
      Node taskNode = projectNode.addNode(taskId, TASK_NOTE_TYPE);
      
      Calendar cal = Calendar.getInstance();
      cal.clear();
      cal.setTime(taskBean.getCreatedDate() );
      taskNode.setProperty("exo:createdDate", cal);
      
      if(null!=taskBean.getResolvedDate()){
        cal.clear();
        cal.setTime(taskBean.getResolvedDate() );
        taskNode.setProperty("exo:resolvedDate", cal);
      }
      
      if(null!=taskBean.getStartedDate() ){
        cal.clear();
        cal.setTime(taskBean.getStartedDate() );
        taskNode.setProperty("exo:startedDate", cal);
      }
      
      taskNode.setProperty("exo:creatorId",taskBean.getCreatorId());
      taskNode.setProperty("exo:id",taskId);
      taskNode.setProperty("exo:name",taskBean.getName());
      taskNode.setProperty("exo:description",taskBean.getDescription());
      taskNode.setProperty("exo:assigneeId",taskBean.getAssigneeId());
      
      if(null!=taskBean.getCoWorkers() && taskBean.getCoWorkers().size()>0){
        int lengh = taskBean.getCoWorkers().size();
        Value[] value = new Value[lengh];
        for (int i=0; i<lengh; i++) {
          Value value2add = session.getValueFactory().createValue(taskBean.getCoWorkers().get(i));
          value[i]= value2add;
        }
        taskNode.setProperty("exo:coWorkers",value);
      }else{
        taskNode.setProperty("exo:coWorkers",(Value[])null);
      }

      taskNode.setProperty("exo:estimateTime",taskBean.getEstimateTime());
      taskNode.setProperty("exo:loggedTime",taskBean.getLoggedTime());
      taskNode.setProperty("exo:remainingTime",taskBean.getRemainingTime());
      

      if(null != taskBean.getDueDate()){
        cal.clear();
        cal.setTime(taskBean.getDueDate());
        taskNode.setProperty("exo:dueDate",cal);
      }
      
      taskNode.setProperty("exo:status",taskBean.getStatus());
      taskNode.setProperty("exo:priority",taskBean.getPriority());
      taskNode.setProperty("exo:isDeleted",false);
      
      homeNode.save();
      
    }catch (Exception e){
      LOG.error(e);
      return null;
    }finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
    return taskBean;
  }

  @Override
  public TaskBean updateTask(String projectId, TaskBean taskBean) throws Exception {
    SessionProvider sessionProvider = null;
    try {
      sessionProvider = SessionProvider.createSystemProvider();
      ManageableRepository currentRepo = this.repositoryService.getCurrentRepository();
      Session session = sessionProvider.getSession(currentRepo.getConfiguration().getDefaultWorkspaceName(), currentRepo);
      
      Node homeNode = getTaskManagementHomeNode(sessionProvider);
      if(homeNode.hasNode(projectId) ==false){
        return null;
      }
      Node projectNode = homeNode.getNode(projectId);
      if(projectNode.hasNode(taskBean.getId())==false){
        return null;
      }
      
      Node taskNode = projectNode.getNode(taskBean.getId());
      taskBean.setModifiedDate(new Date());
      
      Calendar cal = Calendar.getInstance();
      cal.clear();
      cal.setTime(taskBean.getModifiedDate() );
      taskNode.setProperty("exo:modifiedDate", cal);
      
      if(null!=taskBean.getResolvedDate()){
        cal.clear();
        cal.setTime(taskBean.getResolvedDate() );
        taskNode.setProperty("exo:resolvedDate", cal);
      }
      
      if(null!=taskBean.getStartedDate() ){
        cal.clear();
        cal.setTime(taskBean.getStartedDate() );
        taskNode.setProperty("exo:startedDate", cal);
      }
      
      taskNode.setProperty("exo:name",taskBean.getName());
      taskNode.setProperty("exo:description",taskBean.getDescription());
      taskNode.setProperty("exo:assigneeId",taskBean.getAssigneeId());
      
      if(null!=taskBean.getCoWorkers() && taskBean.getCoWorkers().size()>0){
        int lengh = taskBean.getCoWorkers().size();
        Value[] value = new Value[lengh];
        for (int i=0; i<lengh; i++) {
          Value value2add = session.getValueFactory().createValue(taskBean.getCoWorkers().get(i));
          value[i]= value2add;
        }
        taskNode.setProperty("exo:coWorkers",value);
      }else{
        taskNode.setProperty("exo:coWorkers",(Value[])null);
      }

      taskNode.setProperty("exo:estimateTime",taskBean.getEstimateTime());
      taskNode.setProperty("exo:loggedTime",taskBean.getLoggedTime());
      taskNode.setProperty("exo:remainingTime",taskBean.getRemainingTime());
      

      if(null != taskBean.getDueDate()){
        cal.clear();
        cal.setTime(taskBean.getDueDate());
        taskNode.setProperty("exo:dueDate",cal);
      }
      
      taskNode.setProperty("exo:status",taskBean.getStatus());
      taskNode.setProperty("exo:priority",taskBean.getPriority());
      
      homeNode.save();
      
    }catch (Exception e){
      LOG.error(e);
      return null;
    }finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
    return taskBean;
  }
  

  @Override
  public boolean changeTaskStatus(String projectId, String taskId, String newStatus) throws Exception {
    SessionProvider sessionProvider = null;
    try {
      sessionProvider = SessionProvider.createSystemProvider();
      Node homeNode = getTaskManagementHomeNode(sessionProvider);
      if(homeNode.hasNode(projectId) ==false){
        return false;
      }
      Node projectNode = homeNode.getNode(projectId);
      if(projectNode.hasNode(taskId)==false){
        return false;
      }
      
      Node taskNode = projectNode.getNode(taskId);
      
      Calendar cal = Calendar.getInstance();
      cal.clear();
      cal.setTime(new Date());
      taskNode.setProperty("exo:modifiedDate", cal);
      
      taskNode.setProperty("exo:status", newStatus);
      
      homeNode.save();
      
      return true;
      
    }catch (Exception e){
      LOG.error(e);
      return false;
    }finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
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
    SessionProvider sessionProvider = null;
    List<ProjectBean> listPoject = new ArrayList<ProjectBean>();
    try {
      sessionProvider = SessionProvider.createSystemProvider();
      Node homeNode = getTaskManagementHomeNode(sessionProvider);
      
      NodeIterator iterator = homeNode.getNodes();

      while(iterator.hasNext()){
        Node projectNode = iterator.nextNode();
        ProjectBean projectBean = new ProjectBean();
        
        if(projectNode.hasProperty("exo:createdDate")){
          projectBean.setCreatedDate(projectNode.getProperty("exo:createdDate").getDate().getTime());
        }
        
        if(projectNode.hasProperty("exo:modifiedDate")){
          projectBean.setModifiedDate(projectNode.getProperty("exo:modifiedDate").getDate().getTime());
        }
        if(projectNode.hasProperty("exo:ownerType")){
          projectBean.setOwnerType(projectNode.getProperty("exo:ownerType").getString());
        }
        if(projectNode.hasProperty("exo:ownerID")){
          projectBean.setOwnerID(projectNode.getProperty("exo:ownerID").getString());
        }
        if(projectNode.hasProperty("exo:id")){
          projectBean.setId(projectNode.getProperty("exo:id").getString());
        }
        if(projectNode.hasProperty("exo:name")){
          projectBean.setName(projectNode.getProperty("exo:name").getString());
        }
        if(projectNode.hasProperty("exo:description")){
          projectBean.setDescription(projectNode.getProperty("exo:description").getString());
        }
        if(projectNode.hasProperty("exo:membersId")){
          Value[] values = projectNode.getProperty("exo:membersId").getValues();
          List<String> listMemberId = new ArrayList<String>();
          for (Value value : values) {
            String memberId = value.getString();
            listMemberId.add(memberId);
          }
          projectBean.setMembersId(listMemberId);
        }
        
        
        if(projectNode.hasProperty("exo:managerId")){
          Value[] values = projectNode.getProperty("exo:managerId").getValues();
          List<String> listManagerId = new ArrayList<String>();
          for (Value value : values) {
            String managerId = value.getString();
            listManagerId.add(managerId);
          }
          projectBean.setManagerId(listManagerId);
        }
        
        if(projectNode.hasProperty("exo:listTaskStatus")){
          Value[] values = projectNode.getProperty("exo:listTaskStatus").getValues();
          List<String> listTaskStatus = new ArrayList<String>();
          for (Value value : values) {
            String status = value.getString();
            listTaskStatus.add(status);
          }
          projectBean.setListTaskStatus(listTaskStatus);
        }
        
        
        if(projectNode.hasProperty("exo:isDeleted")){
          projectBean.setDeleted(projectNode.getProperty("exo:isDeleted").getBoolean());
        }
        
        
        if(projectNode.hasProperty("exo:totalTask")){
          projectBean.setTotalTask(projectNode.getProperty("exo:totalTask").getLong());
        }
        
        listPoject.add(projectBean);
     }
      
    }catch (Exception e){
      LOG.error(e);
      return null;
    }finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
    return listPoject;
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
    SessionProvider sessionProvider = null;
    List<TaskBean> listTask = new ArrayList<TaskBean>();
    try {
      sessionProvider = SessionProvider.createSystemProvider();
      Node homeNode = getTaskManagementHomeNode(sessionProvider);
      
      if(homeNode.hasNode(projectId)==false)
        return null;
      
      Node projectNode = homeNode.getNode(projectId);
      
      NodeIterator iterator = projectNode.getNodes();
      
      while(iterator.hasNext()){
        Node node = iterator.nextNode();
        TaskBean task = fillNodeToTaskBean(node);
        listTask.add(task);
      }
      
    }catch (Exception e){
      LOG.error(e);
      return null;
    }finally {
      if (sessionProvider != null) {
        sessionProvider.close();
      }
    }
    return listTask;
  }
  private TaskBean fillNodeToTaskBean(Node node){
    TaskBean task = new TaskBean();
    
    try {
      if(node.hasProperty("exo:createdDate")){
        task.setCreatedDate(node.getProperty("exo:createdDate").getDate().getTime());
      }
      
      if(node.hasProperty("exo:modifiedDate")){
        task.setModifiedDate(node.getProperty("exo:modifiedDate").getDate().getTime());
      }
      
      if(node.hasProperty("exo:resolvedDate")){
        task.setModifiedDate(node.getProperty("exo:resolvedDate").getDate().getTime());
      }
      
      if(node.hasProperty("exo:startedDate")){
        task.setModifiedDate(node.getProperty("exo:startedDate").getDate().getTime());
      }
      
      if(node.hasProperty("exo:creatorId")){
        task.setCreatorId(node.getProperty("exo:creatorId").getString());
      }
      
      if(node.hasProperty("exo:id")){
        task.setId(node.getProperty("exo:id").getString());
      }
      
      if(node.hasProperty("exo:name")){
        task.setName(node.getProperty("exo:name").getString());
      }
      
      if(node.hasProperty("exo:description")){
        task.setDescription(node.getProperty("exo:description").getString());
      }
      
      if(node.hasProperty("exo:assigneeId")){
        task.setAssigneeId(node.getProperty("exo:assigneeId").getString());
      }
      
      if(node.hasProperty("exo:coWorkers")){
        Value[] values = node.getProperty("exo:coWorkers").getValues();
        List<String> list = new ArrayList<String>();
        for (Value value : values) {
          String string = value.getString();
          list.add(string);
        }
        task.setCoWorkers(list);
      }
      
      if(node.hasProperty("exo:estimateTime")){
        task.setEstimateTime(node.getProperty("exo:estimateTime").getString());
      }
      
      if(node.hasProperty("exo:loggedTime")){
        task.setLoggedTime(node.getProperty("exo:loggedTime").getString());
      }
      
      if(node.hasProperty("exo:remainingTime")){
        task.setRemainingTime(node.getProperty("exo:remainingTime").getString());
      }
      
      if(node.hasProperty("exo:dueDate")){
        task.setDueDate(node.getProperty("exo:dueDate").getDate().getTime());
      }
      
      if(node.hasProperty("exo:status")){
        task.setStatus(node.getProperty("exo:status").getString());
      }
      
      if(node.hasProperty("exo:priority")){
        task.setPriority(node.getProperty("exo:priority").getString());
      }
      
      if(node.hasProperty("exo:isDeleted")){
        task.setDeleted(node.getProperty("exo:isDeleted").getBoolean());
      }
      
    } catch (Exception ex){
      LOG.error(ex);
      task = null;
    }
    
    return task;
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
