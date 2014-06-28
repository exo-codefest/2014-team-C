(function(gj) {
	function TaskManager() {

		this.lightboxContainerDOM = gj(".LightBoxContainer"); 
		this.lightboxContentDOM = gj(".LightBoxContent");
		this.projectComboDOM = gj("#projectComBoId");
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
			restURL +='getTaskOfProject';
		}
		return restURL;
	}
	TaskManager.prototype.ajaxCommonRequest = function(data,callBackFct){

		var url = this.getRestURLByAction(data.action);
		var _this = this;
		gj.ajax({
  			dataType: "json",
  			url: url,
  			data: data,
  			success: function(data){
  				// to do later
  				if(data){
	  				_this.lightboxContentDOM.html('ok');	  					
  				}else{
	  				_this.lightboxContentDOM.html('nok');	  						  					  					
  				}
  				callBackFct(data,_this);	
  				_this.closePopupContainer();
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
		this.lightboxContentDOM.html('');
		gj(this.lightboxContainerDOM).hide();
		gj("#exo-mask").remove();	
		gj("body").css("overflow", "visible");
	}
	TaskManager.prototype.initProjectLayout = function(){
			this.getProjects();
	};
	TaskManager.prototype.showProjectCreationForm = function(){
		this.showPopupContainer('uiPopupProjectCreationForm');
	};
	TaskManager.prototype.setInputVal = function(id,val){

			gj('.LightBoxContent #'+id).val(val);
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

		this.ajaxCommonRequest(data,this.createProjectCallBack);
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
		this.ajaxCommonRequest(data,this.getProjectsCallBack);
	};
	TaskManager.prototype.getProjectsCallBack = function(data,parent){
		parent.showProjects(data,parent);
	}
	TaskManager.prototype.showProjects = function (projects,parent){
		var _this = parent;
		if(_this != null || _this === undefined ){
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
	TaskManager.prototype.showTaskCreationForm = function(){

		var currentProject = this.getProjectSelected();
		if(currentProject !== false){
			this.showPopupContainer('uiPopupTaskCreationForm');
			this.setInputVal('displayProjectNameTask',currentProject.name);
			this.setInputVal('displayProjectIdTask',currentProject.id);			
		}
		else
			alert('cannot get project');

	};
	TaskManager.prototype.doCreateTask = function(){
		var data = {
			'action':'createTask',
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
			'resolvedDate':this.getInputVal('taskResolvedDate')													
			};

		this.ajaxCommonRequest(data,this.createTaskCallBack);

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
	TaskManager.prototype.cloneTemplateTask = function(tasks){
  

	};
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
		this.ajaxCommonRequest(data,this.getTasksByProjectCallBack);
	};
	TaskManager.prototype.getTasksByProjectCallBack = function(tasks,parent){
		parent.showTasks(tasks,parent);
	};
	TaskManager.prototype.getTaskCreationFormData = function(){

	};
	TaskManager.prototype.getTasksByType = function(type){

	};
	TaskManager.prototype.showTasks = function(tasks,parent){
  			
		var taskContainerDOM = gj("#taskListContainerId");
		var tasksHTML = '<tr><td colspan="7">no task found</td></tr>';
		if(tasks.length > 0){
			tasksHTML = '';	

			gj.each(tasks,function(key,val){
				try{
					var row = '<tr>';
					var date = new Date(parseInt(val.dueDate.time));
					row +='<td><a href="#" onclick="TaskManager.showTaskDetail(\''+val.id+'\')">'+val.name+'</a></td>';
					row +='<td class="center">'+val.priority+'</i></td>';
					row +='<td>'+val.status+'</td>';
					row +='<td>'+val.assigneeId+'</td>';
					row +='<td>'+date.getDay()+'-'+date.getMonth()+'-'+date.getYear()+'</td>';
					row +='<td>'+val.creatorId+'</td>';
					row +='<td class="center">';
					row +='<a onclick="alert(\''+val.id+'\');" data-original-title="Edit" data-placement="bottom" rel="tooltip" class="actionIcon">';
					row +='<i class="uiIconEdit"></i></a>';
					row +='<a data-original-title="Delete" data-placement="bottom" rel="tooltip"  class="actionIcon">';
					row +='<i class="uiIconDelete"></i></a>'
					row +='</td>';	
					row +='</tr>';	
					tasksHTML +=row;
				}catch(e){}
			});
		}
		taskContainerDOM.html(tasksHTML);
	
	};
	TaskManager.prototype.showTaskDetail = function(taskId){
		var currentProject = this.getProjectSelected();
		if(currentProject !== false){
			var data = {'action':'getTask','pid':currentProject.id,'tid':taskId};			
			this.ajaxCommonRequest(data,this.showTaskDetailCallBack);
		}

	};
	TaskManager.prototype.showTaskDetailCallBack = function(data,parent){
		
	}
	window.TaskManager = new TaskManager();
	return window.TaskManager;
})(gj);