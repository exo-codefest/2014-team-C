/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.codefest.service.rest;

import java.util.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;

import org.exoplatform.codefest.service.TaskManagementService;
import org.exoplatform.codefest.service.bean.ProjectBean;
import org.exoplatform.codefest.service.bean.TaskBean;
import org.exoplatform.codefest.util.DateUtil;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.resource.ResourceContainer;

@Path("taskmanagement/")
@Produces(MediaType.APPLICATION_JSON)
public class RestTasks implements ResourceContainer {
  
  private static final Log LOG = ExoLogger.getLogger(RestTasks.class);
  
  private static final CacheControl cacheControl;
  static {
    RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
    cacheControl = new CacheControl();
    cacheControl.setNoCache(true);
    cacheControl.setNoStore(true);
  }
  
  private TaskManagementService _managementService; 
  
  
  public RestTasks(TaskManagementService  managementService){
    _managementService = managementService;
  }
  
  @GET
  @Path("/getTaskOfProject/{projectId}")
  public Response getTaskOfProject(@PathParam("projectId") String projectId) throws Exception{

	  List<TaskBean> tasks = _managementService.getTaskOfProject(projectId);
	  return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/getTask/{projectId}/{taskId}")
  public Response getTask(@PathParam("projectId") String projectId, @PathParam("taskId") String taskId) throws Exception{
	  TaskBean task = _managementService.getTask(projectId, taskId);
	  return Response.ok(task, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/getAllProject/")
  public Response getAllProject() throws Exception{
	  List<ProjectBean> projects = _managementService.getAllProject();
	  return Response.ok(projects, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/createTask/")
  public void createTask(@QueryParam("name") String name,
                         @QueryParam("description") String description,
                         @QueryParam("assigneeId") String assigneeId,
                         @QueryParam("coWorkers") String coWorkers,
                         @QueryParam("estimateTime") String estimateTime,
                         @QueryParam("loggedTime") String loggedTime,
                         @QueryParam("remainingTime") String remainingTime,
                         @QueryParam("priority") String priority,
                         @QueryParam("dueDate") String dueDate,
                         @QueryParam("status") String status,
                         @QueryParam("isDeleted") String isDeleted,
                         @QueryParam("startedDate") String startedDate,
                         @QueryParam("resolvedDate") String resolvedDate,
                         @QueryParam("creatorId") String creatorId,
                         @QueryParam("modifiedDate") String modifiedDate
		  ) throws Exception{
	  TaskBean task = new TaskBean();
	  task.setName(name);
	  task.setDescription(description);
	  task.setAssigneeId(assigneeId);
	  task.setEstimateTime(estimateTime);
	  task.setLoggedTime(loggedTime);
	  task.setRemainingTime(remainingTime);
	  task.setDueDate(DateUtil.stringToDate(dueDate, "dd-MM-yyyy"));
	  task.setStatus(status);
	  task.setPriority(priority);
	  task.setCreatorId(creatorId);
	  task.setStartedDate(DateUtil.stringToDate(startedDate, "dd-MM-yyyy"));
	  task.setResolvedDate(DateUtil.stringToDate(resolvedDate, "dd-MM-yyyy"));
	  
	  _managementService.createTask(task);
  }
  
  @GET
  @Path("/updateTask/")
  public void updateTask(@QueryParam("id") String id,
		  				 @QueryParam("name") String name,
                         @QueryParam("description") String description,
                         @QueryParam("assigneeId") String assigneeId,
                         @QueryParam("coWorkers") String coWorkers,
                         @QueryParam("estimateTime") String estimateTime,
                         @QueryParam("loggedTime") String loggedTime,
                         @QueryParam("remainingTime") String remainingTime,
                         @QueryParam("priority") String priority,
                         @QueryParam("dueDate") String dueDate,
                         @QueryParam("status") String status,
                         @QueryParam("isDeleted") String isDeleted,
                         @QueryParam("startedDate") String startedDate,
                         @QueryParam("resolvedDate") String resolvedDate,
                         @QueryParam("creatorId") String creatorId,
                         @QueryParam("modifiedDate") String modifiedDate
		  ) throws Exception{
	  TaskBean task = new TaskBean();
	  task.setId(id);
	  task.setName(name);
	  task.setDescription(description);
	  task.setAssigneeId(assigneeId);
	  task.setEstimateTime(estimateTime);
	  task.setLoggedTime(loggedTime);
	  task.setRemainingTime(remainingTime);
	  task.setDueDate(DateUtil.stringToDate(dueDate, "dd-MM-yyyy"));
	  task.setStatus(status);
	  task.setPriority(priority);
	  task.setCreatorId(creatorId);
	  task.setStartedDate(DateUtil.stringToDate(startedDate, "dd-MM-yyyy"));
	  task.setResolvedDate(DateUtil.stringToDate(resolvedDate, "dd-MM-yyyy"));
	  
	  _managementService.updateTask(task);
  }
  
  @GET
  @Path("/createProject/")
  public void createProject(@QueryParam("id") String id,
		  				 @QueryParam("name") String name,
                         @QueryParam("description") String description,
                         @QueryParam("membersId") String membersId,
                         @QueryParam("managerId") String managerId,
                         @QueryParam("listTaskStatus") String listTaskStatus,
                         @QueryParam("isDeleted") String isDeleted,
                         @QueryParam("createdDate") String createdDate,
                         @QueryParam("modifiedDate") String modifiedDate,
                         @QueryParam("ownerType") String ownerType,
                         @QueryParam("ownerID") String ownerID
		  ) throws Exception{
	  ProjectBean project = new ProjectBean();
	  project.setId(id);
	  project.setName(name);
	  project.setOwnerType(ownerType);
	  String[] taskS = listTaskStatus.split(",");
	  List<String> taskStatus = new ArrayList<String>() ;
	  
	  for (int i = 0; i < taskS.length; i++) {
		  taskStatus.add(taskS[i]);
	  }
	  
 	  project.setListTaskStatus(taskStatus);
	  
 	  String[] mIds = membersId.split(",");
 	  List<String> memberIds = new ArrayList<String>() ;
	  
	  for (int i = 0; i < mIds.length; i++) {
		  memberIds.add(mIds[i]);
	  }
 	  
 	  project.setMembersId(memberIds);
 	  
	  _managementService.createProject(project);
  }
  
}

