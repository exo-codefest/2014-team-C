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

package org.exoplatform.community.service.ws;

import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.resource.ResourceContainer;

import javax.jcr.Node;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.RuntimeDelegate;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@Path("tasks/")
@Produces(MediaType.APPLICATION_JSON)
public class RestTasks implements ResourceContainer {
  private static final Log LOG = ExoLogger.getLogger(RestUsefulLink.class);

  @GET
	@Path("/getTaskOfProject/")
	public void getTaskOfProject(@PathParam("projectId") String projectId) throws Exception{
		ExoContainer container = ExoContainerContext.getCurrentContainer();  
		TaskManagementService taskMS = (TaskManagementService) container.getComponentInstanceOfType(TaskManagementServiceImpl.class);

  	}
}

