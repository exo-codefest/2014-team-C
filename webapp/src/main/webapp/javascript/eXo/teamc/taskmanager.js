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
		_this.projectComboDOM.html('');;
		console.info('show projects in select');

		if(projects.length > 0){
			gj.each(projects,function(key,val){
				_this.projectComboDOM.append('<option id="' + val.id + '">' + val.name + '</option>');
			});

		}
	};

	TaskManager.prototype.initTaskFilterLayout = function(){

	};
	TaskManager.prototype.showTaskCreationForm = function(){
		var projectName = gj("#projectComBoId option:selected").text();
		var projectId = gj("#projectComBoId option:selected").val();
		this.showPopupContainer('uiPopupTaskCreationForm');
		this.setInputVal('displayProjectNameTask',projectName);
		this.setInputVal('displayProjectIdTask',projectId);
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
			'status':this.getInputVal('taskStatusComBoId')														
			};

		this.ajaxCommonRequest(data,this.createTaskCallBack);

	};
	TaskManager.prototype.createTaskCallBack = function(data,parent){
		alert(data);
	}
	TaskManager.prototype.doEditTask = function(){

	};
	TaskManager.prototype.getTaskCreationFormData = function(){

	};
	TaskManager.prototype.getTasksByType = function(type){

	};
	TaskManager.prototype.showTasks = function(){

	};
	TaskManager.prototype.showTaskDetail = function(){

	};
	TaskManager.prototype.putTask2Tmp = function(task){

	};






	window.TaskManager = new TaskManager();
	return window.TaskManager;
})(gj);