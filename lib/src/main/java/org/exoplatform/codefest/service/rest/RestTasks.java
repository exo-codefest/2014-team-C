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

import java.net.URI;
import java.util.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.RuntimeDelegate;

import org.exoplatform.codefest.service.TaskManagementService;
import org.exoplatform.codefest.service.bean.ProjectBean;
import org.exoplatform.codefest.service.bean.TaskBean;
import org.exoplatform.codefest.util.DateUtil;
import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.organization.User;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.resource.ResourceContainer;

@Path("taskmanagement/")
@RolesAllowed("users")
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
  private OrganizationService orgService_;
  
  public RestTasks(TaskManagementService  managementService, OrganizationService orgService){
    _managementService = managementService;
    orgService_ = orgService;
  }
  
  @GET
  @Path("/getTaskOfProject/{projectId}")
  public Response getTaskOfProject(@PathParam("projectId") String projectId) throws Exception{

	  List<TaskBean> tasks = _managementService.getTaskOfProject(projectId);
	  return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/getTask/{projectId}/{taskId}")
  @RolesAllowed("users")
  public Response getTask(@PathParam("projectId") String projectId, @PathParam("taskId") String taskId) throws Exception{
	  TaskBean task = _managementService.getTask(projectId, taskId);
	  return Response.ok(task, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/getAllProject/")
  @RolesAllowed("users")
  public Response getAllProject(@Context SecurityContext sc,
                                @Context UriInfo uriInfo) throws Exception{
	  List<ProjectBean> projects = _managementService.getAllProject();
	  return Response.ok(projects, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/getTaskOfProject/")
  @RolesAllowed("users")
  public Response getTaskOfProject(@Context SecurityContext sc,
                                   @Context UriInfo uriInfo,
                                   @QueryParam("projectId") String projectId) throws Exception{
    if(null==projectId || projectId.trim().length()==0)
      return Response.status(Status.BAD_REQUEST).build();
    
    List<TaskBean> tasks = _managementService.getTaskOfProject(projectId);
    return Response.ok(tasks, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/getTaskOfProjectToXML/")
  @Produces(MediaType.APPLICATION_XML)
  @RolesAllowed("users")
  public Response getTaskOfProjectToXML(@Context SecurityContext sc,
                                   @Context UriInfo uriInfo,
                                   @QueryParam("projectId") String projectId) throws Exception{
    if(null==projectId || projectId.trim().length()==0)
      return Response.status(Status.BAD_REQUEST).build();
    
    //List<TaskBean> tasks = _managementService.getTaskOfProject(projectId);
    String taskXML = "<user pin='123456'> <password>password</password><username>mkyong</username></user>";
    
    return Response.ok(taskXML, MediaType.APPLICATION_XML).cacheControl(cacheControl).build();
  }
  
  @GET
  @Path("/createTask/")
  @RolesAllowed("users")
  public Response createTask(@Context SecurityContext sc,
                         @Context UriInfo uriInfo,
                         @QueryParam("projectId") String projectId,
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
                         @QueryParam("startedDate") String startedDate,
                         @QueryParam("resolvedDate") String resolvedDate
		  ) throws Exception{
    
    //TODO
    //check permission of logined user with project first
    //only members and managers of project can create task
    
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
	  task.setCreatorId(getUserId(sc, uriInfo));
	  task.setStartedDate(DateUtil.stringToDate(startedDate, "dd-MM-yyyy"));
	  task.setResolvedDate(DateUtil.stringToDate(resolvedDate, "dd-MM-yyyy"));
	  
	  List<String> listCoWorkers = new ArrayList<String>() ;
    String[] arrayCoWorkers = coWorkers.split(",");
    for (String string : arrayCoWorkers) {
      listCoWorkers.add(string);
    }
    task.setCoWorkers(listCoWorkers);
	    
	  
    TaskBean taskBean= _managementService.createTask(projectId,task);
    
	  if(null!=taskBean){
      return Response.ok("true", MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
    }else{
      return Response.ok("false", MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
    }
	  
  }
  
  @GET
  @Path("/changeTaskStatus/")
  @RolesAllowed("users")
  public Response changeTaskStatus(@Context SecurityContext sc,
                             @Context UriInfo uriInfo,
                             @QueryParam("projectId") String projectId,
                             @QueryParam("id") String id,
                             @QueryParam("status") String status
                            ) throws Exception{
    
    boolean result = _managementService.changeTaskStatus(projectId, id, status);
    
    return Response.ok(result+"", MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  
  }
  @GET
  @Path("/updateTask/")
  @RolesAllowed("users")
  public Response updateTask(@Context SecurityContext sc,
                             @Context UriInfo uriInfo,
                         @QueryParam("projectId") String projectId,
                         @QueryParam("id") String id,
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
                         @QueryParam("startedDate") String startedDate,
                         @QueryParam("resolvedDate") String resolvedDate
		  ) throws Exception{
    
    //TODO
    //check permission of logined user with project first
    //only members and managers of project can update task
    
    TaskBean task = new TaskBean();
    task.setName(name);
    task.setId(id);
    task.setDescription(description);
    task.setAssigneeId(assigneeId);
    task.setEstimateTime(estimateTime);
    task.setLoggedTime(loggedTime);
    task.setRemainingTime(remainingTime);
    task.setDueDate(DateUtil.stringToDate(dueDate, "dd-MM-yyyy"));
    task.setStatus(status);
    task.setPriority(priority);
    task.setStartedDate(DateUtil.stringToDate(startedDate, "dd-MM-yyyy"));
    task.setResolvedDate(DateUtil.stringToDate(resolvedDate, "dd-MM-yyyy"));
    
    List<String> listCoWorkers = new ArrayList<String>() ;
    String[] arrayCoWorkers = coWorkers.split(",");
    for (String string : arrayCoWorkers) {
      listCoWorkers.add(string);
    }
    task.setCoWorkers(listCoWorkers);
    
    TaskBean taskBean= _managementService.updateTask(projectId,task);
    
    if(null!=taskBean){
      return Response.ok("true", MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
    }else{
      return Response.ok("false", MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
    }
  }
  
  @GET
  @Path("/createProject/")
  @RolesAllowed("users")
  public Response createProject(@Context SecurityContext sc,
                                @Context UriInfo uriInfo,
                                @QueryParam("id") String id,
      		  				            @QueryParam("name") String name,
                                @QueryParam("description") String description,
                                @QueryParam("membersId") String membersId,
                                @QueryParam("managerId") String managerId,
                                @QueryParam("ownerType") String ownerType,
                                @QueryParam("ownerID") String ownerID) throws Exception{
	  ProjectBean project = new ProjectBean();
	  project.setId(id);
	  project.setName(name);
	  project.setDescription(description);
	  
	  //project.setOwnerType(ownerType);
	  if(null==ownerType || ownerType.trim().length()==0 && 
	      (ownerType.trim().equals(ProjectBean.OWNER_TYPE_USER)==false &&
	      ownerType.trim().equals(ProjectBean.OWNER_TYPE_SPACE)==false &&
	      ownerType.trim().equals(ProjectBean.OWNER_TYPE_GROUP)==false)){
	    
	      project.setOwnerType(ProjectBean.OWNER_TYPE_USER);
	  }else{
	      project.setOwnerType(ownerType.trim());
	  }
	  
	  if(null==ownerID || ownerID.trim().length()==0){
	    project.setOwnerType(ProjectBean.OWNER_TYPE_USER);
	    project.setOwnerID(getUserId(sc, uriInfo));
	  }else{
	    project.setOwnerID(ownerID);
	  }
	  
	  List<String> ListManagerId = new ArrayList<String>() ;
	  String[] arrayManagerId = managerId.split(",");
    for (String string : arrayManagerId) {
      ListManagerId.add(string);
    }
 	  project.setManagerId(ListManagerId);
 	  
    List<String> memberIds = new ArrayList<String>() ;
 	  String[] mIds = membersId.split(",");
    for (String string : mIds) {
      memberIds.add(string);
    }
 	  
 	  project.setMembersId(memberIds);
 	  
	  ProjectBean projectResult = _managementService.createProject(project);
	  if(null!=projectResult){
	    return Response.ok("true", MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
	  }else{
	    return Response.ok("false", MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
	  }
  }
  
  @GET
  @Path("/getAllUser/")
  @RolesAllowed("users")
  public Response createProject(@Context SecurityContext sc,
                                @Context UriInfo uriInfo)throws Exception{
    
    ListAccess<User> userList = orgService_.getUserHandler().findAllUsers();
    List<User> list = new ArrayList<User>();
    for(int i = 0; i < userList.getSize(); i++){
      User user = null;
      try{
        user = userList.load(i, 1)[0];
        list.add(user);
      }
      catch (Exception e) {
        LOG.error(e);
      }
    }
    return Response.ok(list, MediaType.APPLICATION_JSON).cacheControl(cacheControl).build();
  }
  /**
   * Get Logined userid
   * @param sc
   * @param uriInfo
   * @return
   */
  private String getUserId(SecurityContext sc, UriInfo uriInfo) {

    try {
      return sc.getUserPrincipal().getName();
    } catch (NullPointerException e) {
      return getViewerId(uriInfo);
    } catch (Exception e) {
      return null;
    }
  }
  
  private String getViewerId(UriInfo uriInfo) {
    
    URI uri = uriInfo.getRequestUri();
    String requestString = uri.getQuery();
    if (requestString == null) {
      return null;
    }
    String[] queryParts = requestString.split("&");
    
    for (String queryPart : queryParts) {
      if (queryPart.startsWith("opensocial_viewer_id")) {
        return queryPart.substring(queryPart.indexOf("=") + 1, queryPart.length());
      }
    }
    
    return null;
  }
  
}

