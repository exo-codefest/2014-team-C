(function(gj) {
	function TaskManager() {

		this.lightboxContainerDOM = gj(".LightBoxContainer"); 
		this.lightboxContentDOM = gj(".LightBoxContent");
		
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

		var top = (gj(window).height() - this.lightboxContainerDOM.outerHeight())/2 ;
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
	TaskManager.prototype.getInputVal = function(inputName){

			return gj('.LightBoxContent input[name='+inputName+']').val();
	}
	TaskManager.prototype.getTextVal = function(inputName){

			return gj('.LightBoxContent #'+inputName).val();
	}
	TaskManager.prototype.doCreateProject = function(){
		var data = {
			'action':'createProject',
			'id':this.getInputVal('displayName'),
			'name':this.getInputVal('displayName'),
			'description':this.getTextVal('description'),
			'membersId':this.getInputVal('displayMember'),	
			'managerId':this.getInputVal('displayManager')		
			};

		this.ajaxCommonRequest(data,this.createProjectCallBack);
	};
	TaskManager.prototype.createProjectCallBack = function(data,parent){
		console.log('callBackFct createProject '+data);
	//	if(data){
			parent.getProjects();
	//	}
	}
	TaskManager.prototype.validProjectCreationForm = function (){
		console.log('validate form');
	};
	TaskManager.prototype.getProjects = function(){
		console.info('get project json');
		var data = {'action':'getAllProject'};
		this.ajaxCommonRequest(data,this.showProjects);
	};
	TaskManager.prototype.showProjects = function (projects){
		var projectComboDOM = gj("#projectComBoId");
		projectComboDOM.html('');;
		console.info('show projects in select');
	};
	TaskManager.prototype.initTaskFilterLayout = function(){

	};
	TaskManager.prototype.showTaskCreationForm = function(){

	};
	TaskManager.prototype.doCreateTask = function(){

	};
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