/*
 * Copyright (C) 2003-2014 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package  org.exoplatform.content.webui.component;

import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.core.lifecycle.Lifecycle;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Jun 26, 2014  
 */
@ComponentConfig(
                 lifecycle = Lifecycle.class,
                 template = "app:/templates/TaskManagerPortlet/UITaskManagerLayout.gtmpl"
                 )
public class UITaskManagerLayout extends UIContainer {

  public UITaskManagerLayout() throws Exception{
    addChild(UIProjectMenuContainer.class,null,null);
    addChild(UITaskFilterContainer.class,null,null);
    addChild(UITaskListContainer.class,null,null);
  }
  public void processRender(WebuiRequestContext context) throws Exception {
    super.processRender(context);
    context.getJavascriptManager()
           .getRequireJS()
           .require("SHARED/teamctaskmanager", "teamctaskmanager")
           .addScripts("teamctaskmanager.init();");

  }
}
