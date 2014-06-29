(function(gj) {
	function TaskManager() {
		this.projectMembers = new Array();
		this.projectManagers = new Array();
		this.taskAssignees = new Array();
		this.taskCoWorkers = new Array();
		this.lightboxContainerDOM = gj(".LightBoxContainer"); 
		this.lightboxContentDOM = gj(".LightBoxContent");
		this.projectComboDOM = gj("#projectComBoId");
/*
		this.memberProjectComboDOM = gj(".LightBoxContent #displayMember");
		this.ManagerProjectComboDOM = gj(".LightBoxContent #displayManager");
		this.assigneTaskComboDOM = gj(".LightBoxContent #taskAssigne");
		this.coworkerTaskComboDOM = gj(".LightBoxContent #taskCoWorker");
		*/
		this.taskStatusToDisplay="TODO";
		this.displayPopup = false;
		this.isPopupProject = false;
		this.isLoadingData4Popup = false;
	};
	TaskManager.prototype.init = function(){
		this.initProjectLayout();
	};
	TaskManager.prototype.getRestURLByAction = function(action){
		var restURL = '/rest/taskmanagement/';
		if(action == 'createProject'){
			restURL +='createProject';	
		}else if(action == 'getAllProject'){
			restURL +='getAllProject';
		}else if(action == 'createTask'){
			restURL +='createTask';
		}else if(action == 'getTaskOfProject'){
			restURL +='getTaskOfProjectInRestBean';
		}else if(action == 'getTask'){
			restURL +='getTask';
		}else if(action == 'getTaskInRestBean'){
			restURL +='getTaskInRestBean';	
		}else if(action == 'getAllUser'){
			restURL +='getAllUser';
		}else if(action == 'changeTaskStatus'){
			restURL +='changeTaskStatus';
		}
		return restURL;
	}
	TaskManager.prototype.ajaxCommonRequest = function(action,data,callBackFct){

		var url = this.getRestURLByAction(action);
		var _this = this;
		gj.ajax({
  			dataType: "json",
  			url: url,
  			data: data,
  			success: function(data){
  				if(!_this.isLoadingData4Popup){
	  				// to do later
	  				if(data){
		  				_this.lightboxContentDOM.html('ok');	  					
	  				}else{
		  				_this.lightboxContentDOM.html('nok');	  						  					  					
	  				}
	  				_this.closePopupContainer();
  				}	
  				callBackFct(data,_this);
  			}
		});

	};
	TaskManager.prototype.showPopupContainer = function(contentid){
		var childDOM = gj("#"+contentid);
		var htmlContent = childDOM.html();

		this.lightboxContentDOM.html(htmlContent);
		var _this = this;
		if(gj("#exo-mask").length == 0) {
			var mask = gj("<div />", {
				id : "exo-mask",
				style : "opacity:0.8;",
				click : function(){
					_this.lightboxContentDOM.html('');
					_this.lightboxContainerDOM.hide();
					gj(this).remove();
					gj("body").css("overflow", "auto");
				} 	
			});
		}
		this.lightboxContainerDOM.before(mask);

		gj("a.BtnClose").click(function(){
			_this.lightboxContentDOM.html('');
			_this.lightboxContainerDOM.hide();
			gj("#exo-mask").remove();	
			gj("body").css("overflow", "visible");
		});
		
		var top = (gj(window).height() - this.lightboxContainerDOM.outerHeight()) ;
		if(contentid == 'uiPopupProjectCreationForm')
			top = top/2;
		else
			top = top/8;

		top += gj(window).scrollTop() || 0;
		
		var left = (gj(window).width() - gj(this.lightboxContainerDOM).width())/4;
		this.lightboxContainerDOM.css("top", top);
		this.lightboxContainerDOM.css("left", left);

		gj("body").css("overflow", "hidden");

		this.lightboxContainerDOM.show();
	};
	TaskManager.prototype.closePopupContainer = function(){
		this.displayPopup = false;
		this.lightboxContentDOM.html('');
		gj(this.lightboxContainerDOM).hide();
		gj("#exo-mask").remove();	
		gj("body").css("overflow", "visible");
	}
	TaskManager.prototype.initProjectLayout = function(){
			this.getProjects();
	};
	TaskManager.prototype.showProjectCreationForm = function(){
		this.isPopupProject = true;
		this.displayPopup = true;
		this.getUsers();
	};
	TaskManager.prototype.setInputVal = function(id,val){
		if(gj('.LightBoxContent #'+id).length != 0 )
			gj('.LightBoxContent #'+id).val(val);
		else
			gj('.LightBoxContent .'+id).val(val);
	}
	TaskManager.prototype.getInputVal = function(id){

		return gj('.LightBoxContent #'+id).val();

	}
	TaskManager.prototype.doCreateProject = function(){
		var data = {
			'action':'createProject',
			'id':this.getInputVal('displayName'),
			'name':this.getInputVal('displayName'),
			'description':this.getInputVal('description'),
			'membersId':this.getInputVal('displayMember'),	
			'managerId':this.getInputVal('displayManager')		
			};

		this.ajaxCommonRequest('createProject',data,this.createProjectCallBack);
	};
	TaskManager.prototype.createProjectCallBack = function(data,parent){
		console.log('callBackFct createProject '+data);
		if(data){
			parent.getProjects();
		}
		else{
			alert('something is wrong');
		}
	}
	TaskManager.prototype.validProjectCreationForm = function (){
		console.log('validate form');
	};
	TaskManager.prototype.getProjects = function(){
		var data = {'action':'getAllProject'};
		this.ajaxCommonRequest('getAllProject',data,this.getProjectsCallBack);
	};
	TaskManager.prototype.getProjectsCallBack = function(data,parent){
		parent.showProjects(data,parent);
	}
	TaskManager.prototype.showProjects = function (projects,parent){
		var _this = parent;
		if(_this == null || _this === undefined ){
			_this = this;
		}
		_this.projectComboDOM.html('');
		if(projects.length > 0){
			gj.each(projects,function(key,val){
				_this.projectComboDOM.append('<option id="' + val.id + '">' + val.name + '</option>');
			});
			parent.getTasksByProject(projects[0].id);

		}
	};
	TaskManager.prototype.initTaskFilterLayout = function(){
		this.getTasksByProject();

	};
	TaskManager.prototype.getProjectSelected = function(){
		var projectName = gj("#projectComBoId option:selected").text();
		var projectId = gj("#projectComBoId option:selected").val();		
		if(projectName == null || projectName === undefined)
			return false;
		return {
			'name':projectName,
			'id':projectId
		}
	}
	TaskManager.prototype.showTaskCreationForm = function(status){
		this.displayPopup = true;
		this.isPopupProject = false;
		this.taskStatusToDisplay = status;
		this.getUsers();
	};
	TaskManager.prototype.doCreateTask = function(){

		this.ajaxCommonRequest('createTask',this.getTaskFormData(),this.createTaskCallBack);

	};
	TaskManager.prototype.createTaskCallBack = function(data,parent){
		if(data){
			var currentProject = parent.getProjectSelected();
			if(currentProject !== false)
					parent.getTasksByProject(currentProject.id);			
		}
		else
			alert('something is wrong');

	};
	TaskManager.prototype.showTaskEditForm = function(){

	}
	TaskManager.prototype.doEditTask = function(){

	};
	TaskManager.prototype.getTasksByProject = function(projectId){
		if(projectId == null || projectId === undefined){
			projectId =  this.getProjectSelected().id;

		}
		var data = {
			'action':'getTaskOfProject',
			'projectId':projectId
		};				
		this.ajaxCommonRequest('getTaskOfProject',data,this.getTasksByProjectCallBack);
	};
	TaskManager.prototype.getTasksByProjectCallBack = function(tasks,parent){
		parent.showTasks(tasks,parent);
	};
	TaskManager.prototype.getTaskFormData = function(){
		return {
			'projectId':this.getInputVal('displayProjectIdTask'),
			'name':this.getInputVal('displayTaskName'),
			'description':this.getInputVal('taskDescription'),
			'assigneeId':this.getInputVal('taskAssigne'),	
			'coWorkers':this.getInputVal('taskCoWorker'),		
			'estimateTime':this.getInputVal('taskEstimateTime'),		
			'remainingTime':this.getInputVal('taskRemainTime'),		
			'priority':this.getInputVal('taskPriorityComBoId'),		
			'dueDate':this.getInputVal('taskDueDate'),		
			'status':this.getInputVal('taskStatusComBoId'),
			'startedDate':this.getInputVal('taskStartdDate'),														
			'resolvedDate':this.getInputVal('taskResolvedDate'),
			'loggedTime':this.getInputVal('taskLogComBoId')													
			}
	};
	TaskManager.prototype.getTasksByType = function(type){

	};
	TaskManager.prototype.showTasks = function(tasks,parent,view){
		tasksArray4Chart = new Array();
  		var currentProject = this.getProjectSelected();
  		var hasTask = false;	
		var taskContainerDOM = gj("#taskListContainerId");
		var tasksHTML = '<tr><td colspan="7">no task found</td></tr>';
		var tasksTodo = '';
		var tasksIP = '';
		var tasksDone = '';
		if(tasks.length > 0){
			tasksHTML = '';	
			gj.each(tasks,function(key,val){
				try{
					hasTask = true;
					var row = '<tr>';
					row +='<td><a href="#" onclick="TaskManager.showTaskDetail(\''+val.id+'\')">'+val.name+'</a></td>';
					row +='<td class="center">'+val.priority+'</i></td>';
					row +='<td>'+val.status+'</td>';
					row +='<td>'+val.assigneeId+'</td>';
					row +='<td>'+val.dueDate+'</td>';
					row +='<td>'+val.creatorId+'</td>';
					row +='<td class="center">';
					row +='<a onclick="TaskManager.showTaskEditForm(\''+val.id+'\');" data-original-title="Edit" data-placement="bottom" rel="tooltip" class="actionIcon">';
					row +='<i class="uiIconEdit"></i></a>';
					row +='<a onclick="TaskManager.removeTask(\''+val.id+'\');" data-original-title="Delete" data-placement="bottom" rel="tooltip"  class="actionIcon">';
					row +='<i class="uiIconDelete"></i></a>'
					row +='</td>';	
					row +='</tr>';	
					tasksHTML +=row;  

					var rowView1 ='<div class="task-list draggable priority-'+val.priority.toLowerCase()+'">';
						rowView1 +='<div class="task-name" data-placement="bottom" rel="tooltip" title="" data-original-title="task1">';
						rowView1 +=val.name+'</div>';
						rowView1 +='<input type="hidden" name="projectId" class="projectId" value="'+currentProject.id+'"/>';
						rowView1 +='<input type="hidden" name="taskId" class="taskId" value="'+val.id+'"/>';
						rowView1 +='<a class="edit-task">';
          				rowView1 +='<i class="uiIconEdit uiIconLightGray"></i>';
        				rowView1 +='</a>';
      					rowView1 +='</div>';
					if(val.status == 'TODO'){
						tasksTodo +=rowView1;
					}else if(val.status == 'IN PROGRESS'){
						tasksIP +=rowView1;
					}else{
						tasksDone +=rowView1;
					}
					pushDataForChart(val);
				}catch(e){}
			});
		}
		gj("#taskTodoContainerId").html(tasksTodo);
		gj("#taskIPContainerId").html(tasksIP);
		gj("#taskDoneContainerId").html(tasksDone);

		taskContainerDOM.html(tasksHTML);
		showChart();
	};
	TaskManager.prototype.showTaskDetail = function(taskId){
		var currentProject = this.getProjectSelected();
		if(currentProject !== false){
			this.isLoadingData4Popup = true;
			var data = {'action':'getTask','projectId':currentProject.id,'taskId':taskId};			
			this.ajaxCommonRequest('getTask',data,this.showTaskDetailCallBack);
		}

	};
	TaskManager.prototype.showTaskDetailCallBack = function(data,parent){
		var _this = parent;
		if(_this == null || _this === undefined ){
			_this = this;
		}
//		_this.isLoadingData4Popup = false;		
		_this.showPopupContainer('uiPopupTaskDetail');
	};
	TaskManager.prototype.fill
	TaskManager.prototype.removeTask = function(tid){
		var currentProject = this.getProjectSelected();
		if(currentProject != null){
			var data = {
				'action':'changeTaskStatus',
				'projectId':currentProject.id,
				'id':tid,
				'status':'REMOVED'
			};		
			this.ajaxCommonRequest('changeTaskStatus',data,this.removeTaskCallBack);	
		}

	};
	TaskManager.prototype.removeTaskCallBack = function(data,parent){
		if(data){
			parent.getTasksByProject(null);
		}
	};
	TaskManager.prototype.getUsers = function(){			
		var data = {
			'action':'getAllUser'
		};
		this.isLoadingData4Popup = true;
		this.ajaxCommonRequest('getAllUser',data,this.getUsersCallBack);
	};
	TaskManager.prototype.getUsersCallBack = function(users,parent){

		if(users.length > 0){
			var _this = parent;
			if(_this == null || _this === undefined ){
				_this = this;
			}
		_this.isLoadingData4Popup = false;
		var memberProjectComboDOM = gj("#displayMember");
		var ManagerProjectComboDOM = gj("#displayManager");
		var assigneTaskComboDOM = gj("#taskAssigne");
		var coworkerTaskComboDOM = gj("#taskCoWorker");
			if(_this.isPopupProject){
				memberProjectComboDOM.html('');
				ManagerProjectComboDOM.html('');		
			}else{
				assigneTaskComboDOM.html('');
				coworkerTaskComboDOM.html('');				
			}

			gj.each(users,function(key,user){
				if(_this.isPopupProject){
					memberProjectComboDOM.append('<option id="' +user.userName+ '">' + user.firstName+' '+ user.lastName + '</option>');
					ManagerProjectComboDOM.append('<option id="' +user.userName+ '">' + user.firstName+' '+ user.lastName + '</option>');
				}else{
					assigneTaskComboDOM.append('<option id="' +user.userName+ '">' + user.firstName+' '+ user.lastName + '</option>');
					coworkerTaskComboDOM.append('<option id="' +user.userName+ '">' + user.firstName+' '+ user.lastName + '</option>');				
				}
			});
		}
		if(_this.isPopupProject)
			_this.showPopupContainer('uiPopupProjectCreationForm');
		else{
			_this.showPopupContainer('uiPopupTaskCreationForm');
			var currentProject = _this.getProjectSelected();
			if(currentProject !== false){
				_this.setInputVal('displayProjectNameTask',currentProject.name);
				_this.setInputVal('displayProjectIdTask',currentProject.id);	
				if(_this.taskStatusToDisplay != null && _this.taskStatusToDisplay !== undefined){
					_this.setInputVal('taskStatusComBoId',_this.taskStatusToDisplay);
				}	
			}
			else
				alert('no project');
			}	

	};
	TaskManager.prototype.addParticipant = function(type,id){
		
	};
	TaskManager.prototype.showProjectMembers = function(){

	};
	TaskManager.prototype.showProjectManagers = function(){

	};
	TaskManager.prototype.showTaskAssignees = function(){

	};
	TaskManager.prototype.showTaskCoWorkers = function(){

	};
	TaskManager.prototype.addProjectMembers = function(uid){

	};
	TaskManager.prototype.addProjectManagers = function(uid){

	};
	TaskManager.prototype.addTaskAssignees = function(uid){

	};
	TaskManager.prototype.addTaskCoWorkers = function(uid){

	};
	TaskManager.prototype.removeProjectMembers = function(uid){

	};
	TaskManager.prototype.removeProjectManagers = function(uid){

	};
	TaskManager.prototype.removeTaskAssignees = function(uid){

	};
	TaskManager.prototype.removeTaskCoWorkers = function(uid){

	};

	window.TaskManager = new TaskManager();
	return window.TaskManager;
})(gj);